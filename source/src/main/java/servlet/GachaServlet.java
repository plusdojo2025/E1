package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String family_id = "1001";
		int sum_level = 0;
		gachaDAO gcDAO = new gachaDAO();
		List<user> familyList = gcDAO.select(family_id);
		List<housework> houseworkList = gcDAO.selecthw(family_id);
<<<<<<< Updated upstream
		List<housework> fixed_levelList = gcDAO.selecthwlevel(family_id);
=======
>>>>>>> Stashed changes
		
		for (housework hw:houseworkList) {
			sum_level += hw.getHousework_level();
		}
<<<<<<< Updated upstream
		for (housework hw:fixed_levelList) {
			
		}
=======
>>>>>>> Stashed changes
	}

}
