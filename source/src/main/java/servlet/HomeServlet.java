package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.today_houseworkDAO;
import dao.today_memoDAO;
import dto.housework;
import dto.today_memo;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// セッションを取得
		HttpSession session = request.getSession();
		// セッションにログイン情報がなければログイン画面へリダイレクト
		if (session.getAttribute("user_id") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}
		
		request.setCharacterEncoding("UTF-8");
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dtf1 =
				DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String formatNowDate1 = dtf1.format(nowDate);
					String formatyesterdayDate = dtf1.format(nowDate.minusDays(1));
		 Calendar cal = Calendar.getInstance();
		 int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		String family_id = (String) session.getAttribute("family_id");
		
		 today_houseworkDAO td_hwDAO = new today_houseworkDAO();
		 today_memoDAO memoDAO = new today_memoDAO();
		 if (td_hwDAO.selectdate(formatyesterdayDate) != 0) {
			 td_hwDAO.reset();
		 }
		 int a = td_hwDAO.selectdate(formatNowDate1, family_id);
		 if (a == 0) {
		 	td_hwDAO.first_insert(dayOfWeek,family_id);
		 }
			List<housework> houseworkList = td_hwDAO.select(family_id); 
			request.setAttribute("houseworkList",houseworkList);
			
			List<housework> irregular_houseworkList = td_hwDAO.select_ir(family_id);
			request.setAttribute("irregular_houseworkList",irregular_houseworkList);
			
			List<today_memo> memoList = memoDAO.select(family_id);
			request.setAttribute("memoList", memoList);
			

			List<Integer> idList = td_hwDAO.selectachive(formatNowDate1, family_id);
			request.setAttribute("idList", idList);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/home.jsp");
			dispatcher.forward(request, response); 
		 
		 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dtf1 =
				DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String formatNowDate1 = dtf1.format(nowDate);
		 
		String user_id = (String) session.getAttribute("user_id");
		String family_id = (String) session.getAttribute("family_id"); 

		String id = request.getParameter("housework_id");
		String text = request.getParameter("memo");
		today_memo memo = new today_memo(0,family_id,text);
		today_houseworkDAO td_hwDAO = new today_houseworkDAO();
		today_memoDAO memoDAO = new today_memoDAO();


		if (request.getParameter("submit").equals("完了")) {
			int housework_id = Integer.parseInt(id);
			if(td_hwDAO.selectachive2(formatNowDate1, housework_id) == -1) {
				if(td_hwDAO.submit(user_id, family_id ,housework_id)) { 
						if (td_hwDAO.insert_notification(user_id,housework_id)){
							response.sendRedirect(request.getContextPath() + "/HomeServlet");
						}
				}
			}else {
				response.sendRedirect(request.getContextPath() + "/HomeServlet");
			}
			
			
		}else if(request.getParameter("submit").equals("家事追加")) {
				if (id == null) {
					id = "0";

				int housework_id = Integer.parseInt(id);
				if(td_hwDAO.insert(housework_id)) {
					response.sendRedirect(request.getContextPath() + "/HomeServlet");
				}
			}
		}else if(request.getParameter("submit").equals("メモ追加")) {	
			if(memoDAO.insert(memo)) {
				response.sendRedirect(request.getContextPath() + "/HomeServlet");
			}
		}
	}
}
