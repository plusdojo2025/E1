package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.gachaDAO;
import dto.housework;
import dto.user;

/**
 * Servlet implementation class GachaServlet
 */
@WebServlet("/GachaServlet")
public class GachaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		//String familyId = (String) session.getAttribute("family_id");
		String family_id = "1001";
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dtf =
				DateTimeFormatter.ofPattern("yyyyMMddHH");
					int date = Integer.parseInt(dtf.format(nowDate));
		gachaDAO gcDAO = new gachaDAO();
		int log = 0;
		try {
			log = gcDAO.select_log(family_id);
		}catch (Exception e){
			
		}
		
		 if (date / 100 != log /100) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/gacha.jsp");
			dispatcher.forward(request, response);			
		}else if (log == 0) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/gacha.jsp");
			dispatcher.forward(request, response);
		}
		
		
			
			List<housework> roleList = gcDAO.select_role(family_id);
			if (roleList.size() == 0) {
				request.setAttribute("today_housework", "none");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/gacha.jsp");
				dispatcher.forward(request, response);
			}
			List<String> role = new ArrayList<String>();
			for (housework hw:roleList) {
				if (role.indexOf(hw.getRole()) == -1) {
					role.add(hw.getRole());
				}
			}
			
			request.setAttribute("role", role);
			request.setAttribute("roleList",roleList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/gacha_result.jsp");
			dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		//String familyId = (String) session.getAttribute("family_id");
		String family_id = "1001";
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dtf =
				DateTimeFormatter.ofPattern("yyyyMMddHH");
					int date = Integer.parseInt(dtf.format(nowDate));
		String event = request.getParameter("click");
		if (event.equals("on")) {
			gachaDAO gcDAO = new gachaDAO();
			gcDAO.click_gacha(family_id,date);
			
			int sum_level = 0;
			List<user> familyList = gcDAO.select(family_id);
			List<housework> houseworkList = gcDAO.selecthw(family_id);
			List<housework> fixed_levelList = gcDAO.selecthwlevel(family_id);
			List<housework> vari_houseworkList = gcDAO.selecthw_vari(family_id);		
			for (housework hw:houseworkList) {
				sum_level += hw.getHousework_level();
			}
			
			for (user fm:familyList) {
				fm.setUser_level(sum_level * fm.getShare_goal());
			}
			
			for (user fm:familyList) {
				for (housework hw:fixed_levelList) {
					if (hw.getFixed_role().equals(fm.getUser_id())) {
						fm.addUser_level(hw.getHousework_level());
					}
				}
			}
			
			int i = 0;
			Random rand = new Random();
			while (i < vari_houseworkList.size()) {
				int num = rand.nextInt(familyList.size());
				if (familyList.get(num).getUser_level() >= 0) {
					gcDAO.update(familyList.get(num),vari_houseworkList.get(i));
					familyList.get(num).addUser_level(vari_houseworkList.get(i).getHousework_level());
					i++;
				}
			}
		}
		response.sendRedirect(request.getContextPath() + "/GachaServlet");
	}

}
