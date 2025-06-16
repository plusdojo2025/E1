package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.achievementDAO;
import dto.user;

/**
 * Servlet implementation class AnalysisServlet
 */
@WebServlet("/AnalysisServlet")
public class AnalysisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// セッションを取得
        HttpSession session = request.getSession();
        
        // セッションにログイン情報がなければログイン画面へリダイレクト
        /*if (session.getAttribute("id") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }*/
		
        // セッションからfamily_idを取得
		String familyId = (String) session.getAttribute("family_id");
		
		// DAOを使って同じfamily_idを持つユーザーのリストを取得
		try {
			achievementDAO dao = new achievementDAO();
			List<user> userList = dao.selectUserId(familyId);
			
			// JSPに渡すためにリクエストスコープに保存
			request.setAttribute("userList", userList);
			
		} catch (Exception e) {
			
			// JSPに渡すためにリクエストスコープに保存
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データの取得に失敗しました。");
			return;
		}

		// 分析画面（analysis.jsp）にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/analysis.jsp");
		dispatcher.forward(request, response);
		
	}
	
}
