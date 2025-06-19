package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.user; // DTOのuserクラスをインポート

public class userDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/e1_db?"
            + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    // データベース接続を取得する共通メソッド
    private Connection getConnection() throws SQLException {
        // JDBCドライバの読み込み（初回のみ必要だが、念のためtry-catchで囲む）
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバが見つかりません: " + e.getMessage());
            throw new SQLException("JDBC Driver not found", e);
        }
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * ユーザーIDとハッシュ化されたパスワードでログイン認証を行います。
     *
     * @param user ユーザーIDとハッシュ化されたパスワードを持つUserオブジェクト
     * @return ログインが成功した場合はtrue、失敗した場合はfalse
     */
    public boolean isLoginOK(user user) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        boolean loginResult = false;

        try {
            conn = getConnection(); // 共通メソッドで接続を取得

            // SELECT文を準備する
            String sql = "SELECT count(*) FROM user WHERE user_id=? AND password=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, user.getUser_id());
            pStmt.setString(2, user.getPassword()); // ここにはハッシュ化されたパスワードが渡される想定

            // SELECT文を実行し、結果表を取得する
            rs = pStmt.executeQuery();

            // ユーザーIDとパスワードが一致するユーザーがいれば結果をtrueにする
            rs.next(); // 結果セットの最初の行へ移動
            if (rs.getInt(1) == 1) { // カラム名ではなく、インデックス(1)で取得
                loginResult = true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // エラーログを出力
            // 本番環境では、詳細なエラー情報をユーザーに返さないようにする
            // throw new DAOException("ログイン処理中にデータベースエラーが発生しました。", e); のように、
            // より上位の例外にラップしてスローすることも検討
        }
        return loginResult;
    }

    /**
     * 新しいユーザーをデータベースに挿入します。
     *
     * @param newUser 挿入するユーザー情報を持つUserオブジェクト
     * @return 挿入が成功した場合はtrue、失敗した場合はfalse
     */
    public boolean insert(user newUser) {
        String sql = "INSERT INTO user (user_id, user_name, family_id, password, share_goal) VALUES (?, ?, ?, ?, ?)";
        String cQuery = "SELECT COUNT(*) FROM family WHERE family_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection(); // 共通メソッドで接続を取得
            pstmt = conn.prepareStatement(sql);
         // familyid重複チェック
            try (PreparedStatement checkStmt = conn.prepareStatement(cQuery)) {
                checkStmt.setString(1, newUser.getUser_id());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return false; // 重複データあり
                    }
                }
            }
            // パラメータを設定
            pstmt.setString(1, newUser.getUser_id());
            pstmt.setString(2, newUser.getUser_name());
            pstmt.setString(3, newUser.getFamily_id());
            pstmt.setString(4, newUser.getPassword()); // ここにはハッシュ化されたパスワードが渡される想定
            pstmt.setFloat(5, newUser.getShare_goal());

            int rowsAffected = pstmt.executeUpdate(); // SQLを実行

            return rowsAffected > 0; // 1行以上影響があれば成功
        } catch (SQLException e) {
            e.printStackTrace(); // エラーログを出力
            // 例: ユーザーIDが重複している場合など、固有のエラーハンドリングが必要ならここで分岐
            // if (e.getSQLState().equals("23000")) { // データベースの重複エントリのエラーコード
            //    System.err.println("ユーザーIDが既に存在します。");
            // }
            return false; // 挿入失敗
        }
    }
    
    
    
    public List<user> getUsersByFamilyid(String familyid) {
    	
    	
    	//登録画面用
	    List<user> users = new ArrayList<>();
	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
    			+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
    			"root", "password")) {
	        String sql = "SELECT user_id FROM user WHERE family_id = ?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, familyid);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            users.add(new user(rs.getString("user_id")));
	        }
	        rs.close(); 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return users;
	}
    
    
    public boolean deleteUser(user newUser) {
        String sql = "DELETE FROM user WHERE user_id = ? AND password = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection(); // 共通メソッドで接続を取得
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newUser.getUser_id());
            pstmt.setString(2, newUser.getPassword());
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0; // 1行以上削除されれば成功
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String family_id(String user_id) {
		Connection conn = null;
		String family_id = "";
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
				+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			String sql = "SELECT family_id FROM user WHERE user_id = '" + user_id + "'";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				family_id = rs.getString("family_id");
			}
			
						
		}catch (SQLException e) {
			e.printStackTrace();
			family_id = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			family_id = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					family_id = null;
				}
			}
		}
		return family_id;
	}
    
}