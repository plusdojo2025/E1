package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        } finally {
            // リソースを確実にクローズ
            closeResources(conn, pStmt, rs);
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
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection(); // 共通メソッドで接続を取得
            pstmt = conn.prepareStatement(sql);

            // パラメータを設定
            pstmt.setString(1, newUser.getUser_id());
            pstmt.setString(2, newUser.getUser_name());
            pstmt.setString(3, newUser.getFamily_id());
            pstmt.setString(4, newUser.getPassword()); // ここにはハッシュ化されたパスワードが渡される想定
            pstmt.setDouble(5, newUser.getShare_goal());

            int rowsAffected = pstmt.executeUpdate(); // SQLを実行

            return rowsAffected > 0; // 1行以上影響があれば成功
        } catch (SQLException e) {
            e.printStackTrace(); // エラーログを出力
            // 例: ユーザーIDが重複している場合など、固有のエラーハンドリングが必要ならここで分岐
            // if (e.getSQLState().equals("23000")) { // データベースの重複エントリのエラーコード
            //    System.err.println("ユーザーIDが既に存在します。");
            // }
            return false; // 挿入失敗
        } finally {
            // リソースを確実にクローズ
            closeResources(conn, pstmt, null); // ResultSetはここでは不要
        }
    }

    /**
     * JDBCリソースを安全にクローズするためのヘルパーメソッド
     * @param conn Connectionオブジェクト
     * @param pstmt PreparedStatementオブジェクト
     * @param rs ResultSetオブジェクト
     */
    private void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // クローズ時のエラーもログに出力
        }
    }
}