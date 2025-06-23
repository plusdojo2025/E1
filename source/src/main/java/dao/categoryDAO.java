package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.category;

public class categoryDAO {
	
	// 
	public List<category> findAll() {
	    List<category> categoryList = new ArrayList<>();
	    Connection conn = null;
	    
	    try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
	
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
		
			// SELECT文を準備する
			 String sql = "SELECT * FROM category";
			 PreparedStatement pStmt = conn.prepareStatement(sql);
			 
			// SELECT文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			
			// 結果をリストに詰める
			while (rs.next()) {
			    category ct = new category();
			    ct.setCategory_id(rs.getInt("category_id"));
			    ct.setCategory_name(rs.getString("category_name"));
			    categoryList.add(ct);
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
	    return categoryList;
	    
	}
}
