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
import dto.achievement;
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

		if (familyId == null) {
			familyId = "sato0611"; // テスト用の固定値
		}
		
		// DAOを使って同じfamily_idを持つユーザーのリストを取得
		try {
			achievementDAO dao = new achievementDAO();
			List<user> userList = dao.selectUserId(familyId);
			
			// userList の中身をログ出力して確認
			/*System.out.println("userList size: " + userList.size());
			for (user u : userList) {
				System.out.println("user_id: " + u.getUser_id() + ", share_goal: " + u.getShare_goal());
			}*/
			
			// JSPに渡すためにリクエストスコープに保存
			request.setAttribute("userList", userList);
			
		} catch (Exception e) {
			
			// 例外が発生した場合はエラーログを出力し、500エラーを返す
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データの取得に失敗しました。");
			return;
		}

		// DAOを使って同じfamily_idの前日の実績データリストを取得
		try {
			achievementDAO dao = new achievementDAO();
			List<achievement> yesterdayList = dao.selectYesterdayAchievement(familyId);
			
			// userList の中身をログ出力して確認
			System.out.println("yesterdayList size: " + yesterdayList.size());
			for (achievement a : yesterdayList) {
				System.out.println("user_id: " + a.getUser_id() + ", daily_score: " + a.getAchieve_history());
			}
			
			// JSPに渡すためにリクエストスコープに保存
			request.setAttribute("yesterdayList", yesterdayList);
			
		} catch (Exception e) {
			
			// 例外が発生した場合はエラーログを出力し、500エラーを返す
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データの取得に失敗しました。");
			return;
		}
		
		// 分析画面（analysis.jsp）にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/analysis.jsp");
		dispatcher.forward(request, response);
		
	}
	
}
