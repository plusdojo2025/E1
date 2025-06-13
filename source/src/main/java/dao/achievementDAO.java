//
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class achievementDAO {
	
	public List<Map<String, Object>> getAchievementDistribution() {
		Connection conn = null;
		List<Map<String, Object>> achievementList = new ArrayList<>();
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT user_id, date, achievement_history FROM achievement GROUP BY user_id";
			PreparedStatement pStmt = conn.prepareStatement(sql);
		
			// SQL文を完成させる
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			achievementList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			achievementList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					achievementList = null;
				}
			}
		}

		// 結果を返す
		return achievementList;
	}
