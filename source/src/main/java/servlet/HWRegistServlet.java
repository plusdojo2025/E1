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

import dao.houseworkDAO;
import dao.userDAO;
import dto.housework;
/**
 * Servlet implementation class HWRegistServlet
 */
@WebServlet("/HWRegistServlet")
public class HWRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private int parseIntOrDefault(String param, int defaultValue) {
	    try {
	        return Integer.parseInt(param);
	    } catch (NumberFormatException | NullPointerException e) {
	        return defaultValue;
	    }
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		if (session.getAttribute("user_id") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}
		
		// セッションからfamily_idを取得
		String familyId = (String) session.getAttribute("family_id");
		if (familyId == null) {
			familyId = "sato0611"; // テスト用の固定値
				}
		
		// userDAOを使用してuserListを取得し、リクエスト属性に設定する
	    List<dto.user> userList = new userDAO().getUsersByFamilyid(familyId);
	    request.setAttribute("userList", userList);
		
		// 登録ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/housework_regist.jsp");
		dispatcher.forward(request, response);
			
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		/*if (session.getAttribute("user_id") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}*/
		
		
		// セッションからfamily_idを取得
		String familyId = (String) session.getAttribute("family_id");
		if (familyId == null) {
			familyId = "sato0611"; // テスト用の固定値
		}
		
		// **バリデーションフラグ**
				boolean hasValidationError = false;
				// リクエストパラメータを取得
				String housework_name = request.getParameter("housework_name");
				// category_idは必須ではないため、ここではバリデーションの対象外
				int category_id = parseIntOrDefault(request.getParameter("category_id"), 0);
				
				String housework_level_str = request.getParameter("housework_level");
				int housework_level = parseIntOrDefault(housework_level_str, 0); // hiddenフィールドから取得
				
				// noti_flagとnoti_timeは必須ではないため、ここではバリデーションの対象外
				int noti_flag = parseIntOrDefault(request.getParameter("noti_flag"), 0);
				String noti_time = request.getParameter("noti_time");
				
				String frequency_param = request.getParameter("frequency"); // JSPの<select id="daySelection" name="frequency">からの値
				String manual = request.getParameter("manual");
				String fixed_role = request.getParameter("fixed_role");
				String variable_role = request.getParameter("variable_role");
				// logも必須ではないため、ここではバリデーションの対象外
				int log = parseIntOrDefault(request.getParameter("log"), 0);
		String[] days = request.getParameterValues("days");
		String a = "";
		
		// **必須チェックロジック**
				// 1. 家事名
				if (housework_name == null || housework_name.trim().isEmpty()) {
				    hasValidationError = true;
				}
				// 2. 頻度
				// 「選択してください」(value="") が選ばれている場合、または
				// 「曜日を選択」(value="1") が選ばれているのに曜日が一つも選択されていない場合
				if (frequency_param == null || frequency_param.trim().isEmpty() ||
				    ("1".equals(frequency_param) && (days == null || days.length == 0))) {
				    hasValidationError = true;
				}
				
				// 3. 負担度
				// housework_levelが0の場合（星が一つも選択されていない状態）
				if (housework_level == 0) {
				    hasValidationError = true;
				}
				
				// **バリデーションエラーがあった場合**
				if (hasValidationError) {
				    // エラーメッセージをリクエスト属性に設定
				    request.setAttribute("errorMessage", "必須項目が入力されていません");
				    // userListを再度取得してリクエスト属性に設定 (JSPでプルダウンを表示するため)
				    List<dto.user> userList = new userDAO().getUsersByFamilyid(familyId);
				    request.setAttribute("userList", userList);
				    // 登録ページにフォワードしてエラーメッセージを表示し、処理を終了
				    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/housework_regist.jsp");
				    dispatcher.forward(request, response);
				    return; // ここで処理を終了し、DB登録に進まないようにする
				}
				// **バリデーション成功: データベース登録処理へ**
				// DTOに渡す最終的なfrequencyの値を生成
				String finalFrequencyValueForDB = "";
				if ("0".equals(frequency_param)) { // 「毎日」の場合
				    finalFrequencyValueForDB = "0";
				} else if ("8".equals(frequency_param)) { // 「不定期」の場合
				    finalFrequencyValueForDB = "8";
				} else { // 「曜日を選択」(value="1") が選ばれている場合
				    // 曜日の値はdays配列に入っているので、カンマ区切りで結合
				    if (days != null && days.length > 0) {
				        finalFrequencyValueForDB = String.join(",", days);
				    } else {
		                // ここはバリデーションで弾かれるはずだが、念のため空文字列にしておく
		                finalFrequencyValueForDB = "";
		            }
				}
				 // DTOにセット
					// finalFrequencyValueForDB を渡す
					housework hw = new housework(0, housework_name, familyId, category_id, housework_level,
					    noti_flag, noti_time, finalFrequencyValueForDB, manual, fixed_role, variable_role, log);
					// 登録処理を行う
					houseworkDAO hDao = new houseworkDAO();
					
					if (hDao.insert(hw)) { // 登録成功
						// 一覧ページにフォワードする
						RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/housework_list.jsp");
			 	dispatcher.forward(request, response);
					} else { // 登録失敗 (DBエラーなど)
					    // 登録失敗時もエラーメッセージを表示してJSPに戻す
					    request.setAttribute("errorMessage", "家事の登録に失敗しました。再度お試しください。");
					   
					    // userListを再度取得してリクエスト属性に設定 (JSPでプルダウンを表示するため)
					    List<dto.user> userList = new userDAO().getUsersByFamilyid(familyId);
					    request.setAttribute("userList", userList);
					    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/housework_regist.jsp");
					    dispatcher.forward(request, response);
					}
				}}