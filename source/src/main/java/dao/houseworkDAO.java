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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1?"
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

	// 一覧を負担度で昇順降順
	public List<housework> selectAllSorted(String sortOrder, String family_Id) {
	    Connection conn = null;
	    List<housework> cardList = new ArrayList<>();

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password"
	        );

	        // SQL：ソート順は "asc" または "desc"
	        String sql = "SELECT * FROM housework WHERE family_id = ? ORDER BY housework_level " + 
	                     ("desc".equalsIgnoreCase(sortOrder) ? "DESC" : "ASC");

	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, family_Id);
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
	        }
	    }

	    return cardList;
	}
	
	// タブで絞り込んだ結果を負担度で昇順降順
	public List<housework> selectByCategorySorted(int categoryId, String sortOrder, String family_Id) {
	    List<housework> cardList = new ArrayList<>();
	    Connection conn = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password"
	        );

	        // ソート順が "desc" なら DESC それ以外は ASC
	        String order = "asc".equalsIgnoreCase(sortOrder) ? "ASC" : "DESC";

	        String sql = "SELECT * FROM housework WHERE category_id = ? AND family_id = ? ORDER BY housework_level " + order;

	        PreparedStatement pStmt = conn.prepareStatement(sql);
	        pStmt.setInt(1, categoryId);
	        pStmt.setString(2, family_Id); 

	        ResultSet rs = pStmt.executeQuery();

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

	        rs.close();
	        pStmt.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	        cardList = null;
	    } finally {
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    return cardList;
	}

/*
// 検索ここから
	// 引数card指定された項目で検索して、取得されたデータのリストを返す
	public List<housework> select(housework card) {
		Connection conn = null;
		List<housework> cardList = new ArrayList<housework>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する 
			String sql = "SELECT housework_id, housework_name, family_id, category_id, housework_level, noti_flag, noti_time, frequency, manual, fixed_role, variable_role, log " 
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
	} */
// 検索ここまで
//	検索試作はじまり
	public List<housework> searchHousework(String family_Id, int category_id, String housework_name, int frequency, int noti_flag) {
	    List<housework> cardList2 = new ArrayList<>();

	    String sql = "SELECT * FROM housework WHERE category_id = ? AND housework_name LIKE ? AND frequency LIKE ? AND noti_flag = ? AND family_id = ?";
		Connection conn = null;
		
		try {
			// JDBCドライバを読み込む
//			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1?"
			+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
			"root", "password");
			PreparedStatement pstmt = conn.prepareStatement(sql);

	        pstmt.setInt(1, category_id);
			if (housework_name == null) {
			    pstmt.setString(2, "%"); // 全ての housework を取得
			} else {
			    pstmt.setString(2, "%" + housework_name + "%"); // 部分一致検索
			}
	        pstmt.setString(3, "%" + frequency + "%");
	        pstmt.setInt(4, noti_flag);
	        pstmt.setString(5, family_Id);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                cardList2.add(new housework(
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
		                ));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return cardList2;
	}
// 検索試作おわり
	public List<housework> searchHouseworkSorted(String category_id, String housework_name, String frequency, String noti_flag, String sortOrder, String family_Id) {
	    List<housework> cardList = new ArrayList<>();
	    Connection conn = null;

	    try {
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1?"
	            + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password");

	        String sql = "SELECT * FROM housework WHERE category_id = ? AND housework_name LIKE ? AND frequency LIKE ? AND noti_flag = ?"
	                   + " ORDER BY housework_level " + ("desc".equalsIgnoreCase(sortOrder) ? "DESC" : "ASC");

	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1,Integer.parseInt(category_id));
	        pstmt.setString(2, "%" + (housework_name == null ? "" : housework_name) + "%");
	        pstmt.setString(3, "%" + frequency + "%");
	        pstmt.setInt(4, Integer.parseInt(noti_flag));
	        pstmt.setString(5, family_Id);

	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            cardList.add(new housework(
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
	            ));
	        }
	        rs.close();
	        pstmt.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return cardList;
	}
	// 更新ここから
	    // 引数cardで指定されたレコードを更新し、成功したらtrueを返す
		public boolean update(housework card) {
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
	            // 変えたい項目だけに絞る
				String sql = "UPDATE housework SET housework_id=?, housework_name=?, family_id=?, category_id=?, housework_level=?, noti_flag=?, noti_time=?, frequency=?, manual=?, fixed_role=?, variable_role=?, log=? WHERE housework_id=?";
				PreparedStatement pStmt = conn.prepareStatement(sql);

				// SQL文を完成させる
				if (card.getHousework_id() != 0) {
					pStmt.setInt(1, card.getHousework_id());
				} else {
					// 変更不可 必須項目
				}
				if (card.getHousework_name() != null) {
					pStmt.setString(2, card.getHousework_name());
				} else {
					//pStmt.setString(2, "");
					// 必須項目 未入力の場合スクリプトでエラーメッセージを表示
				}
				if (card.getFamily_id() != null) {
					pStmt.setString(3, card.getFamily_id());
				} else {
					// pStmt.setString(3, "");
					// 変更不可 必須項目
				}
				if (card.getCategory_id() != 0) {
					pStmt.setInt(4, card.getCategory_id());
				} else {
					// 必須項目 １～４それ以外はエラーメッセージ
					pStmt.setInt(4, 1);
				}
				if (card.getHousework_level() != 0) {
					pStmt.setInt(5, card.getHousework_level());
				} else {
					// デフォルト値１
					pStmt.setInt(5, 1);
				}
				if (card.getNoti_flag() != 0) {
					pStmt.setInt(6, card.getNoti_flag());
				} else {
					// デフォルト値０
					pStmt.setInt(6, 0);
				}
				if (card.getNoti_time() != null) {
					pStmt.setString(7, card.getNoti_time());
				} else {
					// デフォルト値7:00
					pStmt.setString(7, "7:00");
				}
				if (card.getFrequency() != null) {
					pStmt.setString(8, card.getFrequency());
				} else {
					// 必須項目 未入力の場合エラーメッセージを表示
					pStmt.setString(8, "8");
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
					// デフォルト値0
					pStmt.setInt(12, 0);
				}          
				if (card.getHousework_id() != 0) {
					pStmt.setInt(13, card.getHousework_id());
				} else {
					// 変更不可 必須項目
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1?"
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
	public List<housework> all(String family_Id) {
	    Connection conn = null;
	    List<housework> cardList = new ArrayList<>();

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password"
	        );

	        // ログイン時の家族IDと等しいもののみ表示に変更する
	        //String sql = "SELECT * FROM housework ORDER BY housework_id ASC";
	        String sql = "SELECT * FROM housework WHERE family_id LIKE ? ORDER BY housework_id ASC";
	        
	        //String sql = "SELECT housework_id, housework_name, family_id, category_id, housework_level, noti_flag, noti_time, frequency, manual, fixed_role, variable_role, log FROM housework ORDER BY housework_id ASC";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, family_Id);
/*		if (card.getFamily_id() != null) {
				stmt.setString(1, card.getFamily_id());
				} */
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

	public List<housework> searchclean(int category_id, String family_Id) {
		// TODO 自動生成されたメソッド・スタブ
		// カテゴリが『掃除』のデータだけを表示したい
	    Connection conn = null;
	    List<housework> cardList = new ArrayList<>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password"
	        );

	        String sql = "SELECT * FROM housework WHERE family_id = ? AND category_id = 1;";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, family_Id);
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
	public List<housework> searchwash(int category_id, String family_Id) {
		// TODO 自動生成されたメソッド・スタブ
		// カテゴリが『洗濯』のデータだけを表示したい
	    Connection conn = null;
	    List<housework> cardList = new ArrayList<>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password"
	        );

	        String sql = "SELECT * FROM housework WHERE faimly_id = ? AND category_id = 2";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, family_Id);
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

	public List<housework> searchcook(int category_id, String family_Id) {
		// TODO 自動生成されたメソッド・スタブ
		// カテゴリが『料理』のデータだけを表示したい
	    Connection conn = null;
	    List<housework> cardList = new ArrayList<>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password"
	        );

	        String sql = "SELECT * FROM housework WHERE family_id = ? AND category_id = 3";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, family_Id);
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

	public List<housework> searchother(int category_id, String family_Id) {
		// TODO 自動生成されたメソッド・スタブ
		// カテゴリが『その他』のデータだけを表示したい
	    Connection conn = null;
	    List<housework> cardList = new ArrayList<>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/e1?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	            "root", "password"
	        );

	        String sql = "SELECT * FROM housework WHERE family_id = ? AND category_id = 4";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, family_Id);
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
