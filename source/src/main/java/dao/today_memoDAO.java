package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.today_memo;

public class today_memoDAO {
	
	public List<today_memo> select(String family_id) {
		Connection conn = null;
		List<today_memo> memoList = new ArrayList<today_memo>();
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1?"
				+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			String sql = "SELECT memo_id, family_id, memo FROM today_memo WHERE family_id = '" + family_id + "'";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				today_memo memo = new today_memo(rs.getInt("memo_id"),rs.getString("family_id"),rs.getString("memo"));
				memoList.add(memo);
			}
			
						
		}catch (SQLException e) {
			e.printStackTrace();
			memoList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			memoList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					memoList = null;
				}
			}
		}
		return memoList;
	}
	
	public boolean insert(today_memo memo) {
		Connection conn = null;
		boolean result = false;
		
		try {
			
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "INSERT INTO today_memo VALUES (0, ?, ?, NOW())";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			if (memo.getFamily_id() != null) {
				pStmt.setString(1, memo.getFamily_id());
			}
			if (memo.getMemo() != null) {
				pStmt.setString(2, memo.getMemo());
			}
			
			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
			
		} catch (SQLException e) {
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
}
