package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.housework;

/**
 * Servlet implementation class Test2Servlet
 */
@WebServlet("/Test2Servlet")
public class Test2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String family_id = "sato0611";
		List<housework> notiList = new ArrayList<>();
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1?"
			+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
			"root", "password");
			
			String sql = "SELECT housework_id, noti_time FROM housework "
					+ "WHERE housework_id IN "
					+ "(SELECT today_housework_id FROM today_housework WHERE family_id = '" + family_id + "') "
									+ "AND noti_flag = 1";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			 while (rs.next()) {
				 housework hw = new housework(rs.getInt("housework_id"),rs.getString("noti_time"));
				 notiList.add(hw);
			}
			 
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
		
		request.setAttribute("notiList", notiList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/test.jsp");
		dispatcher.forward(request, response); 
	}

}
