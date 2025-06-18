package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			
			// 必須項目のバリデーション
	        if (card.getHousework_name() == null || card.getHousework_name().isEmpty() ||
	            card.getFamily_id() == null || card.getFamily_id().isEmpty() ||
	            card.getCategory_id() == 0 ||
	            card.getFrequency() == null) {
	            return false;
	        }
			
			// SQL文を準備する
	        /*
			String sql = "INSERT INTO housework (housework_id, housework_name, family_id, category_id, housework_level, "
					+ "noti_flag, noti_time, frequency, manual, fixed_role, variable_role, log)VALUES (0,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			*/
	        String sql = "INSERT INTO housework VALUES(0,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
				// SQL文を完成させる
				if (card.getHousework_name() != null) {
					
					pStmt.setString(1, card.getHousework_name());
				} else {
					pStmt.setString(1, "");
				}
				if (card.getFamily_id() != null) {
					pStmt.setString(2, card.getFamily_id());
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
				if (card.getFrequency() != null) {
					pStmt.setString(7, card.getFrequency());
				} else {
					pStmt.setString(7, "");
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

// 検索ここから
	// 引数card指定された項目で検索して、取得されたデータのリストを返す
	public List<housework> select(housework card) {
		Connection conn = null;
		List<housework> cardList = new ArrayList<housework>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する 
			String sql = "SELECT housework_id, housework_name, family_id, category_id, housework_level, noti_flag, noti_time, frequency, manual, fixed_role, variable_role, log" 
            + "FROM housework WHERE housework_id LIKE ? AND housework_name LIKE ?  AND family_id LIKE ? AND category_id LIKE ? AND housework_level LIKE ?  AND noti_flag LIKE ? AND noti_time LIKE ? AND frequency LIKE ? AND manual LIKE ? AND fixed_role LIKE ? AND variable_role LIKE ? AND log LIKE ? ORDER BY housework_id ASC";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (card.getHousework_id() != 0) {
				pStmt.setString(1, "%" + card.getHousework_id() + "%");
			} else {
				pStmt.setString(1, "%");
			}
			if (card.getHousework_name() != null) {
				pStmt.setString(2, "%" + card.getHousework_name() + "%");
			} else {
				pStmt.setString(2, "% OR housework_name IS NULL");
			}
			if (card.getFamily_id() != null) {
				pStmt.setString(3, "%" + card.getFamily_id() + "%'");
			} else {
				pStmt.setString(3, "% OR family_id IS NULL");
			}
			if (card.getCategory_id() != 0) {
				pStmt.setString(4, "%" + card.getCategory_id() + "%");
			} else {
				pStmt.setString(4, "%");
			}
            if (card.getHousework_level() != 0) {
				pStmt.setString(5, "%" + card.getHousework_level() + "%");
			} else {
				pStmt.setString(5, "%");
			}
            if (card.getNoti_flag() != 0) {
				pStmt.setString(6, "%" + card.getNoti_flag() + "%");
			} else {
				pStmt.setString(6, "%");
			}
            if (card.getNoti_time() != null) {
				pStmt.setString(7, "%" + card.getNoti_time() + "%");
			} else {
				pStmt.setString(7, "% OR noti_time IS NULL");
			}
            if (card.getFrequency() != null) {
				pStmt.setString(8, "%" + card.getFrequency() + "%");
			} else {
				pStmt.setString(8, "%");
			}
            if (card.getManual() != null) {
				pStmt.setString(9, "%" + card.getManual() + "%");
			} else {
				pStmt.setString(9, "% OR manual IS NULL");
			}
            if (card.getFixed_role() != null) {
				pStmt.setString(10, "%"+ card.getFixed_role() + "%");
			} else {
				pStmt.setString(10, "% OR fixed_role IS NULL");
			}
            if (card.getVariable_role() != null) {
				pStmt.setString(11, "%" + card.getVariable_role() + "%");
			} else {
				pStmt.setString(11, "% OR variable_role IS NULL");
			}
            if (card.getLog() != 0) {
				pStmt.setString(12, "%" + card.getLog() + "%");
			} else {
				pStmt.setString(12, "%");
			}

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			while (rs.next()) {
				housework hw = new housework(rs.getInt("housework_id"), 
							  rs.getString("housework_name"), 
							  rs.getString("family_id"), 
							  rs.getInt("category_id"), 
							  rs.getInt("housework_level"), 
							  rs.getInt("noti_flag"), 
							  rs.getString("noti_time"), 
							  rs.getString("frequency"), 
							  rs.getString("manual"), 
							  rs.getString("fixed_role"), 
							  rs.getString("variable_role"), 
							  rs.getInt("log"));
				cardList.add(hw);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cardList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			cardList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					cardList = null;
				}
			}
		}
		// 結果を返す
		return cardList;
	}
// 検索ここまで


// 更新ここから
    // 引数cardで指定されたレコードを更新し、成功したらtrueを返す
	public boolean update(housework card) {
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
            // 変えたい項目だけに絞る
			String sql = "UPDATE housework SET housework_id=?, housework_name=?, family_id=?, category_id=?, housework_level=?, noti_flag=?, noti_time=?, frequency=?, manual=?, fixed_role=?, variable_role=? log=? WHERE housework_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (card.getHousework_id() != 0) {
				pStmt.setInt(1, card.getHousework_id());
			} else {
				pStmt.setString(1, "");
			}
			if (card.getHousework_name() != null) {
				pStmt.setString(2, card.getHousework_name());
			} else {
				pStmt.setString(2, "");
			}
			if (card.getFamily_id() != null) {
				pStmt.setString(3, card.getFamily_id());
			} else {
				pStmt.setString(3, "");
			}
			if (card.getCategory_id() != 0) {
				pStmt.setInt(4, card.getCategory_id());
			} else {
				pStmt.setString(4, "");
			}
			if (card.getHousework_level() != 0) {
				pStmt.setInt(5, card.getHousework_level());
			} else {
				pStmt.setString(5, "");
			}
			if (card.getNoti_flag() != 0) {
				pStmt.setInt(6, card.getNoti_flag());
			} else {
				pStmt.setString(6, "");
			}
			if (card.getNoti_time() != null) {
				pStmt.setString(7, card.getNoti_time());
			} else {
				pStmt.setString(7, "");
			}
			if (card.getFrequency() != null) {
				pStmt.setString(8, card.getFrequency());
			} else {
				pStmt.setString(8, "");
			}
			if (card.getManual() != null) {
				pStmt.setString(9, card.getManual());
			} else {
				pStmt.setString(9, "");
			}
			if (card.getFixed_role() != null) {
				pStmt.setString(10, card.getFixed_role());
			} else {
				pStmt.setString(10, "");
			}
			if (card.getVariable_role() != null) {
				pStmt.setString(11, card.getVariable_role());
			} else {
				pStmt.setString(11, "");
			}
			if (card.getLog() != 0) {
				pStmt.setInt(12, card.getLog());
			} else {
				pStmt.setString(12, "");
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
		// 結果を返す
		return result;
	}
// 更新ここまで

// 削除ここから
	// 引数cardで指定された家事IDのレコードを削除し、成功したらtrueを返す
	public boolean delete(housework card) {
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
			String sql = "DELETE FROM housework WHERE housework_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, card.getHousework_id());

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

		// 結果を返す
		return result;
// 削除ここまで
	}
	
//	個別SQL

	// 全件のデータだけを表示したい
	public List<housework> all() {
	    Connection conn = null;
	    List<housework> cardList = new ArrayList<>();

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1_db?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password"
	        );

	        String sql = "SELECT housework_id, housework_name, family_id, category_id, housework_level, noti_flag, noti_time, frequency, manual, fixed_role, variable_role, log FROM housework ORDER BY housework_id ASC";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            housework hw = new housework(
	                rs.getInt("housework_id"),
	                rs.getString("housework_name"),
	                rs.getString("family_id"),
	                rs.getInt("category_id"),
	                rs.getInt("housework_level"),
	                rs.getInt("noti_flag"),
	                rs.getString("noti_time"),
	                rs.getString("frequency"),
	                rs.getString("manual"),
	                rs.getString("fixed_role"),
	                rs.getString("variable_role"),
	                rs.getInt("log")
	            );
	            cardList.add(hw);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        cardList = null;
	    } finally {
	        try {
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            cardList = null;
	        }
	    }
	    return cardList;  
	}

	public List<housework> searchclean(int category_id) {
		// TODO 自動生成されたメソッド・スタブ
		// カテゴリが『掃除』のデータだけを表示したい
	    Connection conn = null;
	    List<housework> cardList = new ArrayList<>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1_db?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password"
	        );

	        String sql = "SELECT * FROM housework WHERE category_id = 1;";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            housework hw = new housework(
	                rs.getInt("housework_id"),
	                rs.getString("housework_name"),
	                rs.getString("family_id"),
	                rs.getInt("category_id"),
	                rs.getInt("housework_level"),
	                rs.getInt("noti_flag"),
	                rs.getString("noti_time"),
	                rs.getString("frequency"),
	                rs.getString("manual"),
	                rs.getString("fixed_role"),
	                rs.getString("variable_role"),
	                rs.getInt("log")
	            );
	            cardList.add(hw);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        cardList = null;
	    } finally {
	        try {
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            cardList = null;
	        }
	    }	    
	    return cardList;  
	}
	public List<housework> searchwash(int category_id) {
		// TODO 自動生成されたメソッド・スタブ
		// カテゴリが『洗濯』のデータだけを表示したい
	    Connection conn = null;
	    List<housework> cardList = new ArrayList<>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1_db?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password"
	        );

	        String sql = "SELECT * FROM housework WHERE category_id = 2";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            housework hw = new housework(
	                rs.getInt("housework_id"),
	                rs.getString("housework_name"),
	                rs.getString("family_id"),
	                rs.getInt("category_id"),
	                rs.getInt("housework_level"),
	                rs.getInt("noti_flag"),
	                rs.getString("noti_time"),
	                rs.getString("frequency"),
	                rs.getString("manual"),
	                rs.getString("fixed_role"),
	                rs.getString("variable_role"),
	                rs.getInt("log")
	            );
	            cardList.add(hw);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        cardList = null;
	    } finally {
	        try {
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            cardList = null;
	        }
	    }
	    return cardList;  
	}

	public List<housework> searchcook(int category_id) {
		// TODO 自動生成されたメソッド・スタブ
		// カテゴリが『料理』のデータだけを表示したい
	    Connection conn = null;
	    List<housework> cardList = new ArrayList<>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1_db?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password"
	        );

	        String sql = "SELECT * FROM housework WHERE category_id = 3";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            housework hw = new housework(
	                rs.getInt("housework_id"),
	                rs.getString("housework_name"),
	                rs.getString("family_id"),
	                rs.getInt("category_id"),
	                rs.getInt("housework_level"),
	                rs.getInt("noti_flag"),
	                rs.getString("noti_time"),
	                rs.getString("frequency"),
	                rs.getString("manual"),
	                rs.getString("fixed_role"),
	                rs.getString("variable_role"),
	                rs.getInt("log")
	            );
	            cardList.add(hw);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        cardList = null;
	    } finally {
	        try {
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            cardList = null;
	        }
	    }
	    return cardList;  
	}

	public List<housework> searchother(int category_id) {
		// TODO 自動生成されたメソッド・スタブ
		// カテゴリが『その他』のデータだけを表示したい
	    Connection conn = null;
	    List<housework> cardList = new ArrayList<>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1_db?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password"
	        );

	        String sql = "SELECT * FROM housework WHERE category_id = 4";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            housework hw = new housework(
	                rs.getInt("housework_id"),
	                rs.getString("housework_name"),
	                rs.getString("family_id"),
	                rs.getInt("category_id"),
	                rs.getInt("housework_level"),
	                rs.getInt("noti_flag"),
	                rs.getString("noti_time"),
	                rs.getString("frequency"),
	                rs.getString("manual"),
	                rs.getString("fixed_role"),
	                rs.getString("variable_role"),
	                rs.getInt("log")
	            );
	            cardList.add(hw);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        cardList = null;
	    } finally {
	        try {
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            cardList = null;
	        }
	    }
	    return cardList;  
	}
}
