package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.userDAO;
import dto.user;
import process.Sha256Util;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログインページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		String user_id = request.getParameter("user_id");
		String password = request.getParameter("password");
		 
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
        
		// ログイン処理を行う
		userDAO uDao = new userDAO();
		String family_id = uDao.family_id(user_id);
		if (uDao.isLoginOK(new user(user_id, hashedPassword))) { // ログイン成功
			// セッションスコープにIDを格納する
			HttpSession session = request.getSession();
			session.setAttribute("user_id", user_id);
			session.setAttribute("family_id", family_id);
			
			// ホームサーブレットにリダイレクトする
			response.sendRedirect(request.getContextPath() + "/HomeServlet");
			} else { // ログイン失敗
			request.setAttribute("errorMessage", "ユーザー名またはパスワードが正しくありません。"); // エラーメッセージをリクエストスコープに設定

			 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp"); // 元のログインページ（login.jsp）を指定
	         dispatcher.forward(request, response); // login.jsp に「フォワード」する
		}
	}
}
