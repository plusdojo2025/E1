package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import dto.housework;
import dto.notification;

public class notificationDAO {
	//アプリ内通知の表示
	public List<notification> select(String user_id){
		Connection conn = null;
		List<notification> notiList = new ArrayList<notification>();
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			// SQL文を準備する
			String sql="select noti_id,notification.user_id,noti_datetime,noti_content from notification join user on notification.user_id=user.user_id where family_id=(select family_id from user where user_id=?) and notification.user_id<>? and convert(noti_datetime,date)=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を完成させる
			pStmt.setString(1, user_id);
			pStmt.setString(2,user_id);
			LocalDate today=LocalDate.now();
			long millisToday=today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
			Date date=new Date(millisToday);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String d=df.format(date);
			pStmt.setString(3,d);
			
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
	
	public boolean delete() {
		Connection conn=null;
		boolean result=false;
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
						
			// SQL文を準備する
			String sql="delete from notification where convert(noti_datetime,date)<>?;";
			PreparedStatement pStmt=conn.prepareStatement(sql);
			
			// SQL文を完成させる
			LocalDate today=LocalDate.now();
			long millisToday=today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
			Date date=new Date(millisToday);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String d=df.format(date);
			pStmt.setString(1,d);
			
			// SQL文を実行する
			pStmt.executeUpdate();
			
			result=true;
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
		return result;
	}
	
	public List<housework> select_rimnoti(String user_id) {
		Connection conn=null;
		List<housework> notiList = new ArrayList<housework>();
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
									
			// SQL文を準備する
			String sql="select housework_name,housework.family_id,noti_time,frequency from housework join user on housework.family_id=user.family_id where housework.family_id=(select user.family_id from user where user_id=?) and noti_flag=1;";
			PreparedStatement pStmt=conn.prepareStatement(sql);
			
			// SQL文を完成させる
			pStmt.setString(1, user_id);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs=pStmt.executeQuery();
			
			// 結果表をコレクションにコピーする
			while (rs.next()) {
				housework hw = new housework(0,rs.getString("housework_name"), rs.getString("family_id"),0,0,1, rs.getString("noti_time"),rs.getString("frequency"),"","","",0);
				notiList.add(hw);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			notiList=null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			notiList=null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					notiList=null;
				}
			}
		}
		return notiList;
	}
}
