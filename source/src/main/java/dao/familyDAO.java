package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.family; // DTOのfamilyクラスをインポート

public class familyDAO {

    // --- データベース接続定数 ---
    // !!! 重要: ご自身のデータベース接続情報に合わせて変更してください !!!
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/e1_db?"
            + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    /**
     * データベースへの接続を確立し、Connectionオブジェクトを返します。
     *
     * @return データベース接続を表すConnectionオブジェクト
     * @throws SQLException データベースアクセスエラーまたはJDBCドライバーが見つからない場合
     */
    private Connection getConnection() throws SQLException {
        try {
            // JDBCドライバーの読み込み（通常、モダンなJDBCドライバーでは不要ですが、念のため）
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバーが見つかりません: " + e.getMessage());
            // ドライバーが見つからない場合は、SQLExceptionとして上位に伝播
            throw new SQLException("JDBC Driver not found", e);
        }
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * 家族IDとあいことばを使って家族認証を行います。
     *
     * @param family 家族IDとあいことば（ハッシュ化済み）を含むFamily DTO
     * @return 認証が成功した場合はtrue、それ以外はfalse
     */
    public boolean isFamilyOK(family family) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        boolean familyResult = false; // デフォルトはfalse

        try {
            conn = getConnection(); // データベース接続を取得

            // SELECT文を準備
            String sql = "SELECT count(*) FROM family WHERE family_id=? AND fami_pass=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, family.getFamily_id());
            pStmt.setString(2, family.getFami_pass()); // ここにはハッシュ化されたあいことばが渡される想定

            // クエリを実行し、結果セットを取得
            rs = pStmt.executeQuery();

            // 一致する家族レコードが存在するかチェック
            if (rs.next()) { // 最初の（唯一の）行に移動
                if (rs.getInt(1) == 1) { // カラムのインデックス1からカウント値を取得
                    familyResult = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // エラーをログに出力
            // 本番環境では、より具体的なエラーハンドリングを検討（例: DAOExceptionをスロー）
        } finally {
            // リソースを確実に解放
            closeResources(conn, pStmt, rs);
        }
        return familyResult;
    }

    /**
     * 新しい家族レコードをデータベースに挿入します。
     *
     * @param newfamily 挿入する家族情報（IDとあいことば）を含むFamily DTO
     * @return 挿入が成功した場合はtrue、それ以外はfalse
     */
    public boolean insertFami(family newfamily) {
        String sql = "INSERT INTO family (family_id, fami_pass) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection(); // データベース接続を取得
            pstmt = conn.prepareStatement(sql);

            // パラメータを設定
            pstmt.setString(1, newfamily.getFamily_id());
            pstmt.setString(2, newfamily.getFami_pass()); // ここにはハッシュ化されたあいことばが渡される想定

            int rowsAffected = pstmt.executeUpdate(); // SQLを実行

            return rowsAffected > 0; // 1行以上影響があれば成功
        } catch (SQLException e) {
            e.printStackTrace(); // エラーをログに出力
            // 例: family_idが重複している場合など、特定のSQL例外を処理することも可能
            return false; // 挿入失敗
        } finally {
            // リソースを確実に解放
            closeResources(conn, pstmt, null); // ResultSetはここでは不要
        }
    }

    /**
     * JDBCリソース（Connection, PreparedStatement, ResultSet）を安全にクローズするためのヘルパーメソッドです。
     *
     * @param conn クローズするConnectionオブジェクト
     * @param pstmt クローズするPreparedStatementオブジェクト
     * @param rs クローズするResultSetオブジェクト
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