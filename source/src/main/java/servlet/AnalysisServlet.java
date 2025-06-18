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
		
			request.setCharacterEncoding("UTF-8");
			// セッションを取得
			HttpSession session = request.getSession();
			
			// セッションにログイン情報がなければログイン画面へリダイレクト
			/*if (session.getAttribute("id") == null) {
			    response.sendRedirect(request.getContextPath() + "/LoginServlet");
			    return;
			}*/
			
			//現在日時を取得し、yyyymmのフォーマットに変換
			/*LocalDateTime nowDate = LocalDateTime.now();
				DateTimeFormatter dtf =
					DateTimeFormatter.ofPattern("yyyyMM");
						String formatNowDate = dtf.format(nowDate);*/
			
			// セッションからfamily_idを取得
			String familyId = (String) session.getAttribute("family_id");
			
			if (familyId == null) {
				familyId = "sato0611"; // テスト用の固定値
			}
			
			try {
				achievementDAO adao = new achievementDAO();
				
				// 13か月以前のデータの削除処理を実行
				adao.deleteOldAchievements();
				
				// DAOを使って同じfamily_idを持つユーザーのリストを取得
				List<user> userList = adao.selectUserId(familyId);
				
				// JSPに渡すためにリクエストスコープに保存
				request.setAttribute("userList", userList);
				
				// DAOを使って同じfamily_idの前日の実績データリストを取得
				List<achievement> yesterdayList = adao.selectYesterdayAchievement(familyId);
				
				// JSPに渡すためにリクエストスコープに保存
				request.setAttribute("yesterdayList", yesterdayList);
				
				// DAOを使って同じfamily_idの先月から過去12か月分の実績データリストを取得
				List<achievement> yearList = adao.selectYearAchievement(familyId);
				
				// JSPに渡すためにリクエストスコープに保存
				request.setAttribute("yearList", yearList);
				
			} catch (Exception e) {
				
				// 例外が発生した場合はエラーログを出力し、500エラーを返す
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データの取得に失敗しました。");
				return;
			}
		
		// userList の中身をログ出力して確認
					/*System.out.println("userList size: " + userList.size());
					for (user u : userList) {
						System.out.println("user_id: " + u.getUser_id() + ", share_goal: " + u.getShare_goal());
					}*/
		
		// yesterdayList の中身をログ出力して確認
					/*System.out.println("yesterdayList size: " + yesterdayList.size());
					for (achievement a : yesterdayList) {
						System.out.println("user_id: " + a.getUser_id() + ", daily_score: " + a.getAchieve_history());
					}*/
		
		// yearList の中身をログ出力して確認
					/*System.out.println("yearList size: " + yearList.size());
					for (achievement a : yearList) {
						System.out.println("user_id: " + a.getUser_id() + ", month: " + a.getDate() + ", monthly_score: " + a.getAchieve_history());
					}*/
		
		// 分析画面（analysis.jsp）にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/analysis.jsp");
		dispatcher.forward(request, response);
		
	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        request.setCharacterEncoding("UTF-8");
	        HttpSession session = request.getSession();
	        
			// セッションにログイン情報がなければログイン画面へリダイレクト
			/*if (session.getAttribute("id") == null) {
			    response.sendRedirect(request.getContextPath() + "/LoginServlet");
			    return;
			}*/

	        String user_id = request.getParameter("user_id");
	        String shareGoalStr = request.getParameter("share_goal");
	        float share_goal = 0.5f;
	        boolean hasError = false;

	        if (shareGoalStr != null && !shareGoalStr.isEmpty()) {
	            try {
	                share_goal = Float.parseFloat(shareGoalStr);
	            } catch (NumberFormatException e) {
	                request.setAttribute("errorMessage", "分担目標は数値で入力してください。");
	                hasError = true;
	            }
	        } else {
	            request.setAttribute("errorMessage", "分担目標を入力してください。");
	            hasError = true;
	        }

	        if (hasError) {
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/analysis.jsp");
	            dispatcher.forward(request, response);
	            return;
	        }

	        try {
	            // 分担目標更新処理
	            user u = new user();
	            u.setUser_id(user_id);
	            u.setShare_goal(share_goal);

	            achievementDAO adao = new achievementDAO();
	            if (adao.updateShareGoal(u)) {
	                response.sendRedirect(request.getContextPath() + "/HomeServlet");
	                return;
	            } else {
	                request.setAttribute("errorMessage", "分担目標の設定に失敗しました。");
	                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/analysis.jsp");
	                dispatcher.forward(request, response);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("errorMessage", "予期せぬエラーが発生しました。");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/analysis.jsp");
	            dispatcher.forward(request, response);
	        }
	    }
}
