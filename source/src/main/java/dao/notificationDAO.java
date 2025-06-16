package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import dto.notification;

public class notificationDAO {
	//アプリ内通知の表示
	public List<notification> select(notification noti,String user_id){
		Connection conn = null;
		List<notification> notiList = new ArrayList<notification>();
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			// SQL文を準備する
			String sql="select noti_id,notification.user_id,noti_datetime,noti_content from notification join user on notification.user_id=user.user_id where family_id=(select family_id from user where user_id=?) and notification.user_id<>? and convert(date,noti_datetime)=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を完成させる
			pStmt.setString(0, user_id);
			pStmt.setString(1,user_id);
			LocalDate today=LocalDate.now();
			long millisToday=today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
			Date date=new Date(millisToday);
			pStmt.setDate(2,date);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs=pStmt.executeQuery();
			
			// 結果表をコレクションにコピーする
			while (rs.next()) {
			notification notif = new notification(rs.getInt("noti_id"), rs.getString("user_id"), rs.getString("noti_datetime"),rs.getString("noti_content"));
			notiList.add(notif);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			notiList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			notiList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					notiList = null;
				}
			}
		}

		// 結果を返す
		return notiList;
	}
	
	public void insert(notification noti) {
		Connection conn=null;
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			String sql="insert into notification values (0,?,?,?)";
		}catch (SQLException e) {
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
	}
}
