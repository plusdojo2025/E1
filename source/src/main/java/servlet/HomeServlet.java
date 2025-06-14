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
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dtf1 =
				DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String formatNowDate1 = dtf1.format(nowDate);
					String formatyesterdayDate = dtf1.format(nowDate.minusDays(1));
		DateTimeFormatter dtf2 =
				DateTimeFormatter.ofPattern("yyyy-MM");
					String formatNowDate2 = dtf2.format(nowDate);
		 Calendar cal = Calendar.getInstance();
		 int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		 String family_id = "1001";
		 //String family_id = loginuser.getFamily_id();
		
		 today_houseworkDAO td_hwDAO = new today_houseworkDAO();
		 today_memoDAO memoDAO = new today_memoDAO();
		 //td_hwDAO.delete(family_id,dayOfWeek);
		 if (td_hwDAO.selectdate(formatyesterdayDate) == 0) {
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
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
			dispatcher.forward(request, response); 
		 
		 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Calendar cal = Calendar.getInstance();
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dtf2 =
				DateTimeFormatter.ofPattern("yyyyMM");
					String formatNowDate2 = dtf2.format(nowDate);
		 int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		 String user_id = "1";
		 String family_id = "1001";
		 

		String id = request.getParameter("housework_id");
		String text = request.getParameter("memo");
		today_memo memo = new today_memo(0,family_id,text);
		today_houseworkDAO td_hwDAO = new today_houseworkDAO();
		today_memoDAO memoDAO = new today_memoDAO();
		

		if (request.getParameter("submit").equals("完了")) {
			int housework_id = Integer.parseInt(id);
			if(td_hwDAO.submit(user_id, housework_id ,formatNowDate2)){
			List<housework> houseworkList = td_hwDAO.select(family_id); 
			request.setAttribute("houseworkList",houseworkList);
			request.setAttribute("houseworkList",houseworkList);
			
			List<housework> irregular_houseworkList = td_hwDAO.select_ir(family_id);
			request.setAttribute("irregular_houseworkList",irregular_houseworkList);
			
			List<today_memo> memoList = memoDAO.select(family_id);
			request.setAttribute("memoList", memoList);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
			dispatcher.forward(request, response); 	
			}
		}else if(request.getParameter("submit").equals("家事追加")) {
			int housework_id = Integer.parseInt(id);
			if(td_hwDAO.insert(housework_id)) {
				List<housework> houseworkList = td_hwDAO.select(family_id); 
				request.setAttribute("houseworkList",houseworkList);				
				
				List<housework> irregular_houseworkList = td_hwDAO.select_ir(family_id);
				request.setAttribute("irregular_houseworkList",irregular_houseworkList);				
				
				List<today_memo> memoList = memoDAO.select(family_id);
				request.setAttribute("memoList", memoList);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
				dispatcher.forward(request, response); 
			}
		}else {	
			if(memoDAO.insert(memo)) {
				List<housework> houseworkList = td_hwDAO.select(family_id); 
				request.setAttribute("houseworkList",houseworkList);				
				
				List<housework> irregular_houseworkList = td_hwDAO.select_ir(family_id);
				request.setAttribute("irregular_houseworkList",irregular_houseworkList);				
				
				List<today_memo> memoList = memoDAO.select(family_id);
				request.setAttribute("memoList", memoList);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
				dispatcher.forward(request, response); 
			}
		}
	}
}
