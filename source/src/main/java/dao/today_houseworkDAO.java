package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.housework;

public class today_houseworkDAO {

//	today_houseテーブルにhouseテーブルのデータを挿入
	public boolean first_insert(int dayOfWeek, String family_id) {
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
		String sql = "INSERT INTO today_housework (today_housework_id, housework_id, date) "
				+ "SELECT 0, housework_id, NOW() "
				+ "FROM housework "
				+ "WHERE family_id = '" + family_id + "' AND frequency = '0' OR frequency LIKE '%" + dayOfWeek + "%'";
		
		PreparedStatement pStmt = conn.prepareStatement(sql);
		
		// SQL文を実行する
		if (pStmt.executeUpdate() >= 1) {
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
			
			String sql = "SELECT housework_id,housework_name,family_id,housework_level "
					+ "FROM housework WHERE housework_id IN "
					+ "(SELECT housework_id FROM today_housework) AND family_id = '" + family_id + "'";
			
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
	
	
	public List<housework> select_ir(String family_id) {
		Connection conn = null;
		List<housework> irregular_houseworkList = new ArrayList<housework>();
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
				+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			String sql = "SELECT housework_id,housework_name,family_id,housework_level "
					+ "FROM housework WHERE frequency = '8' AND family_id = '" + family_id + "'";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				housework hw = new housework(rs.getInt("housework_id"),rs.getString("housework_name"),
						rs.getString("family_id"),rs.getInt("housework_level"));
				irregular_houseworkList.add(hw);
			}
			
						
		}catch (SQLException e) {
			e.printStackTrace();
			irregular_houseworkList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			irregular_houseworkList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					irregular_houseworkList = null;
				}
			}
		}
		return irregular_houseworkList;
	}
	
	
	public boolean insert(int housework_id) {
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
		String sql = "INSERT INTO today_housework VALUES(0, " + housework_id +", NOW())";
		
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
	public int selectdate(String date, String family_id) {
		Connection conn = null;
		int count = 0;
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
				+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			String sql = "SELECT count(*) FROM today_housework "
					+ "WHERE housework_id IN (SELECT housework_id FROM housework WHERE family_id = '" + family_id
					+ "') AND date LIKE ('%" + date + "%')";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(*)");
			}
			
						
		}catch (SQLException e) {
			e.printStackTrace();
			count = 0;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			count = 0;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					count = 0;
				}
			}
		}
		return count;
	}
	public int selectdate(String date) {
		Connection conn = null;
		int count = 0;
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
				+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			String sql = "SELECT count(*) FROM today_housework "
					+ "WHERE date LIKE ('%" + date + "%')";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(*)");
			}
			
						
		}catch (SQLException e) {
			e.printStackTrace();
			count = 0;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			count = 0;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					count = 0;
				}
			}
		}
		return count;
	}
	
	public boolean delete(String family_id, int dayOfWeek) {
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
			String sql = "DELETE FROM today_housework WHERE housework_id IN (SELECT housework_id FROM housework "
					+ "WHERE family_id = + '" + family_id + "' AND (frequency = '0' OR frequency = '" + dayOfWeek +"'))"; 
			PreparedStatement pStmt = conn.prepareStatement(sql);


			// SQL文を実行する
			if (pStmt.executeUpdate() >= 0) {
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

		// 結果を返す
		return result;
	}
	
	public boolean submit(String user_id, String family_id, int housework_id) {
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
		String sql = "INSERT INTO achievement "
				+ "VALUES(0,'" + user_id + "', '" + family_id + "', '" + housework_id +  "', NOW(),"
						+ "(SELECT housework_level FROM housework WHERE housework_id = " + housework_id + "))";
		
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
	public boolean reset() {
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
			String sql1 = "TRUNCATE today_housework"; 
			String sql2 = "TRUNCATE today_memo";
			PreparedStatement pStmt1 = conn.prepareStatement(sql1);
			PreparedStatement pStmt2 = conn.prepareStatement(sql2);


			// SQL文を実行する
			if (pStmt1.executeUpdate() >= 0) {
				if (pStmt2.executeUpdate() >= 0)
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

		// 結果を返す
		return result;
	}
	
	public boolean insert_notification(String user_id, int housework_id) {
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
		String sql = "INSERT INTO notification VALUES(0,'" + user_id + "',NOW(),"
				+ "CONCAT((SELECT user_id FROM user WHERE user_id = '" + user_id + "'), 'が',"
						+ "(SELECT housework_name FROM housework WHERE housework_id = " + housework_id + " ),'を完了しました。'))";
		
		PreparedStatement pStmt = conn.prepareStatement(sql);
		
		// SQL文を実行する
		if (pStmt.executeUpdate() >= 1) {
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
	
	public List<Integer> selectachive(String date, String family_id) {
		Connection conn = null;
		List<Integer> hwidList = new ArrayList<Integer>();
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
				+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			String sql = "SELECT housework_id FROM achievement "
					+ "WHERE family_id = '" + family_id + "' AND date Like '%" + date + "%'";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				hwidList.add(rs.getInt("housework_id"));
			}
			
						
		}catch (SQLException e) {
			e.printStackTrace();
			hwidList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			hwidList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					hwidList = null;
				}
			}
		}
		return hwidList;
	}
	
}
