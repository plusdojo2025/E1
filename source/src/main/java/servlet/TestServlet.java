package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dao.today_houseworkDAO;


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
				HttpSession session = request.getSession();
				LocalDateTime nowDate = LocalDateTime.now();
				DateTimeFormatter dtf1 =
						DateTimeFormatter.ofPattern("yyyy-MM-dd");
							String formatNowDate1 = dtf1.format(nowDate);
				 
				String user_id = (String) session.getAttribute("user_id");
				String family_id = (String) session.getAttribute("family_id"); 
				int housework_id = Integer.parseInt(request.getParameter("housework_id"));
				today_houseworkDAO td_hwDAO = new today_houseworkDAO();
				
				if(td_hwDAO.selectachive2(formatNowDate1, housework_id) == -1) {
					td_hwDAO.submit(user_id, family_id ,housework_id);
					td_hwDAO.insert_notification(user_id,housework_id);
					int idList = td_hwDAO.selectachive2(formatNowDate1, housework_id);
					json.put("data", idList);
					
					
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
}
