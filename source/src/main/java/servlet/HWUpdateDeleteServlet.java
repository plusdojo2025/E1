package servlet;

import java.io.IOException;

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
		//開発中なので一旦非公開
		/*if (session.getAttribute("id") == null) {
			response.sendRedirect("/E1/LoginServlet");
			return;
		}*/
		
		// 検索ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/housework_list.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    // セッションの取得
	    HttpSession session = request.getSession();

	    // リクエストパラメータを取得する
	    request.setCharacterEncoding("UTF-8");

	    // housework_idは必須と仮定し、nullチェックを行う
	    String houseworkIdStr = request.getParameter("housework_id");
	    if (houseworkIdStr == null || houseworkIdStr.isEmpty()) {
	        // エラーハンドリング: 必須パラメータが不足している
	        request.setAttribute("update_error", "家事IDが指定されていません。");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/housework_list.jsp");
	        dispatcher.forward(request, response);
	        return;
	    }
	    int housework_id = Integer.parseInt(houseworkIdStr);

	    String housework_name = request.getParameter("housework_name");
	    String family_id = request.getParameter("family_id");
	    
	    // category_idのnullチェック
	    String categoryIdStr = request.getParameter("category_id");
	    int category_id = (categoryIdStr == null || categoryIdStr.isEmpty()) ? 0 : Integer.parseInt(categoryIdStr);

	    // housework_levelのnullチェック
	    String houseworkLevelStr = request.getParameter("housework_level");
	    int housework_level = (houseworkLevelStr == null || houseworkLevelStr.isEmpty()) ? 0 : Integer.parseInt(houseworkLevelStr);

	    // noti_flagのnullチェック
	    String notiFlagStr = request.getParameter("noti_flag");
	    int noti_flag = (notiFlagStr == null || notiFlagStr.isEmpty()) ? 0 : Integer.parseInt(notiFlagStr);

	    String noti_time = request.getParameter("noti_time");
	    String frequency = request.getParameter("frequency");
	    String manual = request.getParameter("manual");
	    String fixed_role = request.getParameter("fixed_role");
	    String variable_role = request.getParameter("variable_role");
	    
	    // logのnullチェック
	    String logStr = request.getParameter("log");
	    int log = (logStr == null || logStr.isEmpty()) ? 0 : Integer.parseInt(logStr);

	    // 更新または削除を行う
	    String submit = request.getParameter("submit");
	    String actionType = request.getParameter("action_type");

	    houseworkDAO hwDao = new houseworkDAO();

	    if ("更新".equals(submit)) {
	        // 更新処理
	        if (hwDao.update(new housework(housework_id, housework_name, family_id, category_id, housework_level, noti_flag,
	                noti_time, frequency, manual, fixed_role, variable_role, log))) {
	            request.setAttribute("update_message", "家事情報を更新しました。");
	        } else {
	            request.setAttribute("update_error", "レコードを更新できませんでした。");
	        }
	    } else if ("削除".equals(actionType)) {
	        // 削除処理
	        if (hwDao.delete(new housework(housework_id, housework_name, family_id, category_id, housework_level, noti_flag,
	                noti_time, frequency, manual, fixed_role, variable_role, log))) {
	            request.setAttribute("delete_message", "家事情報を削除しました。");
	        } else {
	            request.setAttribute("delete_error", "家事情報を削除できませんでした。");
	        }
	    } else {
	        request.setAttribute("update_error", "不正な操作です。");
	    }

	    // 結果ページにフォワードする
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/housework_list.jsp");
	    dispatcher.forward(request, response);
	}

}