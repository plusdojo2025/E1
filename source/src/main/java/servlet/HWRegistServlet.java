package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.houseworkDAO;
import dto.housework;



/**
 * Servlet implementation class HWRegistServlet
 */
@WebServlet("/HWRegistServlet")
public class HWRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		/*HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/E1/LoginServlet");
			return;
		}*/
		// 登録ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/housework_regist.jsp");
		dispatcher.forward(request, response);
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		/*HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/E1/LoginServlet");
			return;
		}*/
		
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		String housework_name = request.getParameter("housework_name");
		String family_id = request.getParameter("family_id");
		int category_id = Integer.parseInt(request.getParameter("category_id"));
		int housework_level = Integer.parseInt(request.getParameter("housework_level"));
		int noti_flag = Integer.parseInt(request.getParameter("noti_flag"));
		String noti_time = request.getParameter("noti_time");
		int frequency = Integer.parseInt(request.getParameter("frequency"));
		String manual = request.getParameter("manual");
		String fixed_role = request.getParameter("fixed_role");
		String variable_role = request.getParameter("variable_role");
		int log = Integer.parseInt(request.getParameter("log"));
		
		// 登録処理を行う
		houseworkDAO hDao = new houseworkDAO();
		if (hDao.insert(new housework(0,housework_name,family_id,category_id,housework_level,noti_flag,
				noti_time,frequency,manual,fixed_role,variable_role,log))) { // 登録成功
			response.sendRedirect("/E1/HomeServlet");
		} else { // 登録失敗
			response.sendRedirect("/E1/HomeServlet");
		}
		// 一覧ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/housework_list.jsp");
		dispatcher.forward(request, response);
	}

}
