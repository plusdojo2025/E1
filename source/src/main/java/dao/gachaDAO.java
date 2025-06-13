package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.housework;
import dto.user;

public class gachaDAO {
	public List<user> select(String family_id) {
		Connection conn = null;
		List<user> familyList = new ArrayList<user>();
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
				+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			String sql = "SELECT user_id user_name, share_goal FROM user WHERE family_id = " + family_id;
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				user user = new user(rs.getString("uesr_id"),rs.getString("user_name"),
						rs.getDouble("share_goal"));
				familyList.add(user);
			}
			
						
		}catch (SQLException e) {
			e.printStackTrace();
			familyList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			familyList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					familyList = null;
				}
			}
		}
		return familyList;
	}
	
	public List<housework> selecthw(String family_id) {
		Connection conn = null;
		List<housework> houseworkList = new ArrayList<housework>();
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
				+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			String sql = "SELECT housework_id,housework_name,family_id,housework_level "
					+ "FROM housework WHERE housework_id IN "
					+ "(SELECT housework_id FROM today_housework) "
					+ "AND family_id = " + family_id + " AND fixed_role = ''";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				housework hw = new housework(rs.getInt("housework_id"),rs.getString("housework_name"),
						rs.getString("family_id"),rs.getInt("housework_level"));
				houseworkList.add(hw);
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
		return houseworkList;
	}
	
	public List<housework> selecthwlevel(String family_id) {
		Connection conn = null;
		List<housework> fixed_levelList = new ArrayList<housework>();
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
				+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			String sql = "SELECT fixed_role, SUM(housework_level) AS housework_level FROM housework "
					+ "WHERE family_id = " + family_id + " AND fixed_role = '' "
					+ "AND housework_id IN (SELECT housework_id FROM today_housework)"
					+ "GROUP BY fixed_role";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				housework hw = new housework(rs.getString("fixed_role"),rs.getInt("housework_level"));
				fixed_levelList.add(hw);
			}
			
						
		}catch (SQLException e) {
			e.printStackTrace();
			fixed_levelList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fixed_levelList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					fixed_levelList = null;
				}
			}
		}
		return fixed_levelList;
	}
	
}
