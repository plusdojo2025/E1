package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.userDAO;
import dto.user;
import process.Sha256Util;

/**
 * Servlet implementation class UserRegistServlet
 */
@WebServlet("/UserRegistServlet")
public class UserRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user_regist.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		String user_id = request.getParameter("user_id");
		String user_name = request.getParameter("user_name");
		String family_id = request.getParameter("family_id");
		String password = request.getParameter("password");
		float share_goal = Float.parseFloat(request.getParameter("share_goal"));
		
		// useridとPWが入力されているか確認
		if (user_id == null || user_id.isEmpty() || password == null || password.isEmpty()) {
	            request.setAttribute("errorMessage", "ユーザーIDとパスワードは必須入力です。");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
	            dispatcher.forward(request, response);
	            return; // これ以上処理を進めない
	    }
		// --- ここからパスワードのSHA-256暗号化処理 ---
        String hashedPassword = Sha256Util.hashString(password);
        if (hashedPassword == null) {
            // ハッシュ化に失敗した場合の処理（エラーページへフォワードするなど）
            request.setAttribute("errorMessage", "システムエラーが発生しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            dispatcher.forward(request, response);
            return; // 処理を中断
        }
        // --- ここまでパスワードのSHA-256暗号化処理 ---
		
		// 登録処理を行う
		userDAO uDao = new userDAO();
		if (uDao.insert(new user(user_id, user_name, family_id, hashedPassword, share_goal))) { // 登録成功
			request.setAttribute("result", "/E1/HomeServlet");
		} else { // 登録失敗
			request.setAttribute("result", "/E1/LoginServlet");
		}

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}
}
