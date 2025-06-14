package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.familyDAO;
import dto.family;
import process.Sha256Util;

/**
 * Servlet implementation class FamilyRegistServlet
 */
@WebServlet("/FamilyRegistServlet")
public class FamilyRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/family_regist.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		String family_id = request.getParameter("family_id");
		String fami_pass = request.getParameter("fami_pass");
		
		// familyidとPWが入力されているか確認
		if (family_id == null || family_id.isEmpty() || fami_pass == null || fami_pass.isEmpty()) {
	            request.setAttribute("errorMessage", "ユーザーIDとパスワードは必須入力です。");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
	            dispatcher.forward(request, response);
	            return; // これ以上処理を進めない
	    }
		
		// --- ここからパスワードのSHA-256暗号化処理 ---
        String hashedFami_Pass = Sha256Util.hashString(fami_pass);
        if (hashedFami_Pass == null) {
            // ハッシュ化に失敗した場合の処理（エラーページへフォワードするなど）
            request.setAttribute("errorMessage", "システムエラーが発生しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            dispatcher.forward(request, response);
            return; // 処理を中断
        }
        // --- ここまでパスワードのSHA-256暗号化処理 ---

		// 登録処理を行う
		familyDAO fDao = new familyDAO();
		if (fDao.insertFami(new family(family_id, hashedFami_Pass))) { // 登録成功
			request.setAttribute("result", "登録できました");
		} else { // 登録失敗
			request.setAttribute("result","登録できませんでした");
		}

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user_regist.jsp");
		dispatcher.forward(request, response);	
	}
}
