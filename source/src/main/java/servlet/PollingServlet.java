package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dao.today_houseworkDAO;

/**
 * Servlet implementation class PollingServlet
 */
@WebServlet("/PollingServlet")
public class PollingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        String family_id = (String) session.getAttribute("family_id");

        LocalDateTime nowDate = LocalDateTime.now();
        String today = nowDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        today_houseworkDAO td_hwDAO = new today_houseworkDAO();

        List<Integer> idList = td_hwDAO.selectachive(today, family_id);
        
        JSONObject json = new JSONObject();
        json.put("completedTasks", idList);
        
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
