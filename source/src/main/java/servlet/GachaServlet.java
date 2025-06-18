package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
		gachaDAO gcDAO = new gachaDAO();
		
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21); // 午後9時
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long initialDelay = calendar.getTimeInMillis() - System.currentTimeMillis();
        if (initialDelay < 0) {
            initialDelay += TimeUnit.DAYS.toMillis(1); // 翌日の午後9時に設定
        }
        scheduler.scheduleAtFixedRate(() -> {
            // 実行する処理
            gcDAO.reset_gacha();
            
        }, initialDelay, TimeUnit.DAYS.toMillis(1), TimeUnit.MILLISECONDS);
		
		if (gcDAO.select_log(family_id) == 0) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/gacha.jsp");
			dispatcher.forward(request, response);
		}else {
			
			
			List<housework> roleList = gcDAO.select_role(family_id);
			List<String> role = new ArrayList<String>();
			for (housework hw:roleList) {
				if (role.indexOf(hw.getRole()) == -1) {
					role.add(hw.getRole());
				}
			}
			
			request.setAttribute("role", role);
			request.setAttribute("roleList",roleList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/gacha_result.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		//String familyId = (String) session.getAttribute("family_id");
		String family_id = "1001";
		
		String event = request.getParameter("click");
		if (event.equals("on")) {
			gachaDAO gcDAO = new gachaDAO();
			gcDAO.click_gacha(family_id);
			
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
