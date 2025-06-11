package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Bc;
import dto.housework;
import dto.today_housework;

public class today_houseworkDAO {

//	today_houseテーブルにhouseテーブルのデータを挿入
	public boolean insert(housework hw, int dayOfWeek) {
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
		String sql = "INSERT INTO today_housework (today_id, housework_id, date) "
				+ "SELECT 0, housework_id, NOW() "
				+ "FROM housework "
				+ "WHERE frequency = 0 OR frequency = " + dayOfWeek;
		
		PreparedStatement pStmt = conn.prepareStatement(sql);
		
		// SQL文を実行する
		if (pStmt.executeUpdate() == 1) {
			result = true;
			}
			
		}  catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public List<housework> select(String family_id) {
		Connection conn = null;
		List<housework> houseworkList = new ArrayList<housework>();
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
				+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			String sql = "SELECT * FROM housework WHERE housework_id IN "
					+ "(SELECT housework_id FROM today_housework) AND family_id = " + family_id;
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				housework hw = new housework(rs.getInt("housework_id"), rs.getString
						rs.getInt("housework_id"),
						rs.getString("date"));
			}
						
		}catch (SQLException e) {
			e.printStackTrace();
			houseworkList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			houseworkList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					houseworkList = null;
				}
			}
		}
	}
}
