package servlet;

import java.io.IOException;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.houseworkDAO;
import dto.housework;



/**
 * Servlet implementation class HWUpdateDeleteServlet
 */
@WebServlet("/HWUpdateDeleteServlet")
public class HWUpdateDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/webapp/LoginServlet");
			return;
		}

		// 検索ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/search.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/webapp/LoginServlet");
			return;
		}

		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		int housework_id = Integer.parseInt(request.getParameter("housework_id"));
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
		
		// 更新または削除を行う
//		更新削除結果の通達方法要検討
		houseworkDAO hwDao = new houseworkDAO();
		if (request.getParameter("submit").equals("更新")) {
			if (hwDao.update(new housework(housework_id, housework_name, family_id, category_id, housework_level, noti_flag, 
					noti_time, frequency, manual, fixed_role, variable_role, log))) { // 更新成功
				request.setAttribute("result", new Result("家事情報更新", "家事情報をを更新しました。", "/webapp/HWSearchServlet"));
			} else { // 更新失敗
				request.setAttribute("result", new Result("更新失敗", "レコードを更新できませんでした。", "/webapp/HWSearchServlet"));
			}
		} else {
			if (hwDao.delete(new housework(housework_id, housework_name, family_id, category_id, housework_level, noti_flag, 
					noti_time, frequency, manual, fixed_role, variable_role, log))) { // 削除成功
				request.setAttribute("result", new Result("削除成功", "家事情報を削除しました。", "/webapp/HWSearchServlet"));
			} else { // 削除失敗
				request.setAttribute("result", new Result("削除失敗", "家事情報を削除できませんでした。", "/webapp/HWSearchServlet"));
			}
		}

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/housework_list.jsp");
		dispatcher.forward(request, response);
	}
}