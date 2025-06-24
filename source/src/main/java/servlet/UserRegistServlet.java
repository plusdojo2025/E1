package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.familyDAO;
import dao.userDAO;
import dto.family;
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_regist.jsp");
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
        String confirmPassword = request.getParameter("confirmPassword");
		float share_goal = Float.parseFloat(request.getParameter("share_goal"));
		
		String pattern = "^[a-zA-Z0-9!@#$%^&*()_+=\\-]+$";
		String errorMessage = "";

		// ファミリーIDのチェック
		if (family_id == null || !family_id.matches(pattern)) {
		    errorMessage = "ファミリーIDに使用できない文字が含まれています";
		}
		// あいことばのチェック
		else if (fami_pass == null || !fami_pass.matches(pattern)) {
		    errorMessage = "あいことばに使用できない文字が含まれています";
		}
		// ユーザーIDのチェック
		else if (user_id == null || !user_id.matches(pattern)) {
		    errorMessage = "ユーザーIDに使用できない文字が含まれています";
		}
		// パスワードのチェック
		else if (password == null || !password.matches(pattern)) {
		    errorMessage = "パスワードに使用できない文字が含まれています";
		}
		
		if (!errorMessage.isEmpty()) {
		    request.setAttribute("UserErrorMessage", errorMessage);
		    request.getRequestDispatcher("WEB-INF/jsp/user_regist.jsp").forward(request, response);
		    return;
		}
		
		// useridとPWが入力されているか確認
		if (user_id == null || user_id.isEmpty() || password == null || password.isEmpty()) {
	            request.setAttribute("UserErrorMessage", "ユーザーIDとパスワードは必須入力です。");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_regist.jsp");
	            dispatcher.forward(request, response);
	            return; // これ以上処理を進めない
	    }
		// パスワードと確認用パスワードが一致しているか確認
        if (!password.equals(confirmPassword)) {
            request.setAttribute("UserErrorMessage", "パスワードと確認用パスワードが一致しません。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_regist.jsp");
            dispatcher.forward(request, response);
            return;
        }
		 //パスワードが文字数の条件を満たしているか確認
        if (password.length() < 8 || password.length() > 20) {
            request.setAttribute("UserErrorMessage", "パスワードは8文字以上20文字以下で入力してください。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_regist.jsp");
            dispatcher.forward(request, response);
            return;
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
		// --- ここからあいことばのSHA-256暗号化処理 ---
        String hashedFami_Pass = Sha256Util.hashString(fami_pass);
        if (hashedFami_Pass == null) {
            // ハッシュ化に失敗した場合の処理（エラーページへフォワードするなど）
            request.setAttribute("errorMessage", "システムエラーが発生しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
            dispatcher.forward(request, response);
            return; // 処理を中断
        }
        // --- ここまであいことばのSHA-256暗号化処理 ---
        familyDAO familyDAO = new familyDAO();
        family family = new family(family_id, hashedFami_Pass);
		// 登録処理を行う
        if (familyDAO.isFamilyOK(family)) {
			userDAO uDao = new userDAO();
			if (uDao.insert(new user(user_id, family_id, hashedPassword, share_goal))) { // 登録成功
	//			request.setAttribute("result", "登録処理が完了しました");
			} else { // 登録失敗
				request.setAttribute("result", "登録でエラーが発生しました");
			} 
        } else {
			request.setAttribute("result", "家族認証でエラーが発生しました");
		}
		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}
}
