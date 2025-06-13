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
import dto.loginuser;
import dto.user;

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
		// ログイン処理を行う
		userDAO uDao = new userDAO();
		if (uDao.isLoginOK(new user(user_id, password))) { // ログイン成功
			// セッションスコープにIDを格納する
			HttpSession session = request.getSession();
			session.setAttribute("user_id", new loginuser(user_id));

			// メニューサーブレットにリダイレクトする
			response.sendRedirect("/webapp/HomeServlet");
			} else { // ログイン失敗
			request.setAttribute("errorMessage", "ユーザー名またはパスワードが正しくありません。"); // エラーメッセージをリクエストスコープに設定

			 RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp"); // 元のログインページ（login.jsp）を指定
	         dispatcher.forward(request, response); // login.jsp に「フォワード」する
		}
	}
}
