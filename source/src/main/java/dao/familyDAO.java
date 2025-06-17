package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.family; // DTOのfamilyクラスをインポート

public class familyDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/e1_db?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    

    // データベース接続を確立するメソッド
    private static Connection getConnection() throws SQLException {
    	try {
    	    Class.forName("com.mysql.cj.jdbc.Driver");
    	} catch (ClassNotFoundException e) {
    	    e.printStackTrace();
    	}
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    // family_id と fami_pass の認証メソッド
    public boolean isFamilyOK(family family) {
        String query = "SELECT COUNT(*) FROM family WHERE family_id = ? AND fami_pass = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, family.getFamily_id());
            pstmt.setString(2, family.getFami_pass());

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }

    // family_id を挿入するメソッド
    public boolean insertFami(family newFamily) {
        String checkQuery = "SELECT COUNT(*) FROM family WHERE family_id = ?";
        String insertQuery = "INSERT INTO family (family_id, fami_pass) VALUES (?, ?)";

        try (Connection conn = getConnection()) {
            // familyid重複チェック
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, newFamily.getFamily_id());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return false; // 重複データあり
                    }
                }
            }

            //familyデータ挿入処理
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, newFamily.getFamily_id());
                insertStmt.setString(2, newFamily.getFami_pass());

                int rowsInserted = insertStmt.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//   削除しようとしているfamilyに登録されているuserがいないかチェック
    public boolean hasUsers(family newFamily) {
        String query = "SELECT COUNT(*) FROM user WHERE family_id = ?";
        try {
        	Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, newFamily.getFamily_id());
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0; // ユーザーが存在するなら true
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // エラー時は削除不可とする
        }
    }
//    familyを削除する
    public boolean deleteFamily(family newFamily) {
    	 if (hasUsers(newFamily)) {
             return false; // ユーザーが存在する場合は削除不可
         }
        String sql = "DELETE FROM family WHERE family_id = ? AND fami_pass = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection(); // 共通メソッドで接続を取得
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newFamily.getFamily_id());
            pstmt.setString(2, newFamily.getFami_pass());
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0; // 1行以上削除されれば成功
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}