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
    public List<achievement> selectYesterdayAchievement(String family_id) {
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
            String sql = "SELECT user_id, SUM(achieve_history) AS daily_score "
            		+ "FROM achievement "
            		+ "WHERE date = CURDATE() - INTERVAL 1 DAY AND family_id = " + family_id 
            		+ "GROUP BY user_id";
            
            PreparedStatement pStmt = conn.prepareStatement(sql);
            
            // SQL文を実行し、結果表を取得する
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                String user_id = rs.getString("user_id");
                int daily_score = rs.getInt("daily_score");

                achievement a = new achievement(user_id, daily_score);
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
    
    // 過去12か月分の achievement データを全件取得
    public List<achievement> selectYearAchievement(String family_id) {
        Connection conn = null;
        List<achievement> yearList = new ArrayList<>();

        try {
        	// JDBCドライバを読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // データベースに接続する
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/e1_db?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
                "root", "password");

            // SQL文を準備する
            String sql = "SELECT user_id, DATE_FORMAT(date, '%Y-%m') AS month, SUM(achieve_history) AS monthly_score "
            		+ "FROM achievement "
            		+ "WHERE date >= CURDATE() - INTERVAL 12 MONTH AND family_id = " + family_id
            		+ "GROUP BY user_id, month "
            		+ "ORDER BY month ASC, user_id ASC";
            
            PreparedStatement pStmt = conn.prepareStatement(sql);
            
            // SQL文を実行し、結果表を取得する
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                String user_id = rs.getString("user_id");
                String month = rs.getString("month");
                int monthly_score = rs.getInt("monthly_score");

                achievement a = new achievement(user_id, month, monthly_score);
                yearList.add(a);
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
        return yearList;
    }

	// ユーザーリスト
	public List<String> selectUserId(String family_id) {
	    Connection conn = null;
	    List<String> userList = new ArrayList<>();
	
	    try {
	    	// JDBCドライバを読み込む
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        // データベースに接続する
	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1_db?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password");
	
	        // SQL文を準備する
	        String sql = "SELECT DISTINCT user_id FROM achievement "
	        		+ "WHERE family_id = " + family_id;
	        
	        PreparedStatement pStmt = conn.prepareStatement(sql);
	
	        // SQL文を実行し、結果表を取得する
	        ResultSet rs = pStmt.executeQuery();
	        while (rs.next()) {
	            userList.add(rs.getString("user_id"));
	        }
	
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	    // 結果を返す
	    return userList;
	}

}


