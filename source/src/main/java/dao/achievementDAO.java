/*
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class achievementDAO {
	
	public int select(String user_id, String date, int achieve_history) {
		Connection conn = null;
		int daily_count = 0;
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
				+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			// SQL文を準備する
			String sql = "SELECT user_id, achievement_history "
					+ "FROM achievement "
					+ "WHERE date = CURDATE() - INTERVAL 1 DAY";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String userId = rs.getString("user_id");
				int history = rs.getInt("achievement_history");
				result.put(userId, history);
				}

			
						
		}catch (SQLException e) {
			e.printStackTrace();
			daily_count = 0;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			daily_count = 0;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					daily_count = 0;
				}
			}
		}
		
		
		return daily_count;
	}
	
}*/
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.achievement;

public class achievementDAO {

    // 昨日の achievement データを全件取得
    public List<achievement> selectYesterdayAchievement() {
        Connection conn = null;
        List<achievement> yesterdayList = new ArrayList<>();

        try {
        	// JDBCドライバを読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // データベースに接続する
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/e1_db?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
                "root", "password");

            // SQL文を準備する
            String sql = "SELECT achieve_id, user_id, date, achievement_history "
            			+ "FROM achievement "
            			+ "WHERE date = CURDATE() - INTERVAL 1 DAY";
            
            PreparedStatement pStmt = conn.prepareStatement(sql);
            
            // SQL文を実行し、結果表を取得する
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("achieve_id");
                String user_id = rs.getString("user_id");
                String date = rs.getString("date");
                int history = rs.getInt("achievement_history");

                achievement a = new achievement(id, user_id, date, history);
                yesterdayList.add(a);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
            	// データベースを切断
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 結果を返す
        return yesterdayList;
    }
}


