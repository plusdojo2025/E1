package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		String noti_housework = "";
		int id = Integer.parseInt(request.getParameter("housework_id"));
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
			+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
			"root", "password");
			
			String sql = "SELECT housework_name FROM housework WHERE housework_id = " + id + "";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			 while (rs.next()) {
				 noti_housework = rs.getString("housework_name");		
			}
			 json.put("data", noti_housework);
			 
			// データベースを切断
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {
				e.printStackTrace();
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
			e.printStackTrace();
			}
		
		//httpヘッダー送信の登録
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");
	    //送信データの登録
		PrintWriter out = response.getWriter();
		//送信データをネットストリームへ書き込む
		out.print(json);
	}
}
