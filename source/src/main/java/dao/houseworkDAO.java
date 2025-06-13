package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dto.housework;

public class houseworkDAO {

	// 引数cardで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(housework card) {
		Connection conn = null;
		boolean result = false;
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
			+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
			"root", "password");
			
			// SQL文を準備する
			String sql = "INSERT INTO housework VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
				// SQL文を完成させる
				if (card.getHousework_name() != null) {
					pStmt.setString(1, card.getHousework_name());
				} else {
					pStmt.setString(1, "");
				}
				if (card.getFamily_id() != null) {
					pStmt.setString(2, card.getFamily_id());
				} else {
					pStmt.setString(2, "");
				}
				if (card.getCategory_id() != 0) {
					pStmt.setInt(3, card.getCategory_id());
				} else {
					pStmt.setInt(3, 0);
				}
				if (card.getHousework_level() != 0) {
					pStmt.setInt(4, card.getHousework_level());
				} else {
					pStmt.setInt(4, 0);
				}
				if (card.getNoti_flag() != 0) {
					pStmt.setInt(5, card.getNoti_flag());
				} else {
					pStmt.setInt(5, 0);
				}
				if (card.getNoti_time() != null) {
					pStmt.setString(6, card.getNoti_time());
				} else {
					pStmt.setString(6, "");
				}
				if (card.getFrequency() != 0) {
					pStmt.setInt(7, card.getFrequency());
				} else {
					pStmt.setInt(7, 0);
				}
				if (card.getManual() != null) {
					pStmt.setString(8, card.getManual());
				} else {
					pStmt.setString(8, "");
				}
				if (card.getFixed_role() != null) {
					pStmt.setString(9, card.getFixed_role());
				} else {
					pStmt.setString(9, "");
				}
				if (card.getVariable_role() != null) {
					pStmt.setString(10, card.getVariable_role());
				} else {
					pStmt.setString(10, "");
				}
				if (card.getLog() != 0) {
					pStmt.setInt(11, card.getLog());
				} else {
					pStmt.setInt(11, 0);
				}
			
				// SQL文を実行する
				if (pStmt.executeUpdate() == 1) {
					result = true;
				}
		} 
			catch (SQLException e) {
			e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
			e.printStackTrace();
			} 
			finally {
				// データベースを切断
				if (conn != null) {
					try {
					conn.close();
					} catch (SQLException e) {
					e.printStackTrace();
					}
				}
			}
			
		// 結果を返す
		return result;	
	}	
}
