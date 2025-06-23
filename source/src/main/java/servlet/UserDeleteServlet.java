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
	@WebServlet("/UserDeleteServlet")
public class UserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_delete.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		String user_id = request.getParameter("user_id");
		String family_id = request.getParameter("family_id");
		String fami_pass = request.getParameter("fami_pass");
		String password = request.getParameter("password");

		
		// useridとPWが入力されているか確認
		if (user_id == null || user_id.isEmpty() || password == null || password.isEmpty()) {
	            request.setAttribute("UserErrorMessage", "ユーザーIDとパスワードは必須入力です。");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_delete.jsp");
	            dispatcher.forward(request, response);
	            return; // これ以上処理を進めない
		}
		// --- ここからパスワードのSHA-256暗号化処理 ---
        String hashedPassword = Sha256Util.hashString(password);
        if (hashedPassword == null) {
            // ハッシュ化に失敗した場合の処理（エラーページへフォワードするなど）
            request.setAttribute("errorMessage", "システムエラーが発生しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
            dispatcher.forward(request, response);
            return; // 処理を中断
        }
        // --- ここまでパスワードのSHA-256暗号化処理 ---

        userDAO userDAO = new userDAO();

        user user = new user(user_id, hashedPassword);
        boolean isDeleted = userDAO.deleteUser(user);
        if (isDeleted) {
        	request.setAttribute("result", "ユーザー削除に成功しました。");
        } else {
        	request.setAttribute("result", "ユーザー削除に失敗しました。");
        }
		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_delete.jsp");
		dispatcher.forward(request, response);
	}
}
