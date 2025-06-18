package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.houseworkDAO;
import dto.housework;

/**
 * Servlet implementation class HWSearchServlet
 */
@WebServlet("/HWSearchServlet")
public class HWSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Object cardList;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
//		HttpSession session = request.getSession();
//		if (session.getAttribute("id") == null) {
//			response.sendRedirect("/E1/LoginServlet");
//			return;
//		}

		//初期化
		request.setCharacterEncoding("UTF-8");
		//List<housework> cardList = null;
		int housework_id = 0;
		String housework_name = "";
		String family_id = "";
		int category_id = 0;
		int housework_level = 0;
		int noti_flag = 0;
		String noti_time = "";
		int frequency = 0;
		String manual = "";
		String fixed_role = "";
		String variable_role = "";
		int log = 0;
		String role = "";
		String searchType = request.getParameter("searchType");

		
		
		houseworkDAO hwDAO = new houseworkDAO();
		
		// 家事一覧を作成
		//if ("掃除".equals(searchType)) {
			// カテゴリが『掃除』のみを取得
			//List<housework> cardList = hwDAO.searchclean();
			
			//houseworkDAO hwDao = new houseworkDAO();

			
			List<housework>cardList = hwDAO.all();
	//}
		

		
		
				
		// 結果をスコープに格納
		request.setAttribute("cardList", cardList);

		// 家事一覧ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/housework_list.jsp");
		dispatcher.forward(request, response);
	}


//↓POST
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
//		HttpSession session = request.getSession();
//		if (session.getAttribute("id") == null) {
//			response.sendRedirect("/E1/LoginServlet");
//			return;
//		}
		
		// リクエストパラメータを取得する
		//初期化
		request.setCharacterEncoding("UTF-8");
		String searchType = request.getParameter("searchType");
		int housework_id = 0;
		String housework_name = "";
		String family_id = "";
		int category_id = 0;
		int housework_level = 0;
		int noti_flag = 0;
		String noti_time = "";
		int frequency = 0;
		String manual = "";
		String fixed_role = "";
		String variable_role = "";
		int log = 0;
		String role = "";
		
		
		// 押されたボタンのnameにより格納するカテゴリIDを変更
		if ("掃除".equals(searchType)) {
			//掃除のみ表示
			category_id = 1;
			houseworkDAO hwDao = new houseworkDAO();		
			cardList = hwDao.searchclean(category_id);


		} else if ("洗濯".equals(searchType)) {
			// 洗濯のみ表示
			category_id = 2;
			houseworkDAO hwDao = new houseworkDAO();		
			cardList = hwDao.searchwash(category_id);
			
		} else if ("料理".equals(searchType)) {
			// 料理のみ
			category_id = 3;
			houseworkDAO hwDao = new houseworkDAO();		
			cardList = hwDao.searchcook(category_id);

			
		} else if ("その他".equals(searchType)) {
			// その他
			category_id = 4;
			houseworkDAO hwDao = new houseworkDAO();
			cardList = hwDao.searchother(category_id);

			
		} else if ("一覧".equals(searchType)) {
			// 全件表示
			houseworkDAO hwDao = new houseworkDAO();		
			cardList = hwDao.all();

		}
		
//		housework_id = Integer.parseInt(request.getParameter("housework_id"));
//		housework_name = request.getParameter("housework_name");
//		family_id = request.getParameter("family_id");
//		category_id = Integer.parseInt(request.getParameter("category_id"));
//		housework_level = Integer.parseInt(request.getParameter("housework_level"));
//		noti_flag = Integer.parseInt(request.getParameter("noti_flag"));
//		noti_time = request.getParameter("noti_time");
//		frequency = Integer.parseInt(request.getParameter("frequency"));
//		manual = request.getParameter("manual");
//		fixed_role = request.getParameter("fixed_role");
//		variable_role = request.getParameter("variable_role");
//		log = Integer.parseInt(request.getParameter("log"));

		
		// 検索処理を行う
		//List<housework> cardList = null;
//		houseworkDAO hwDao = new houseworkDAO();		
//		cardList = hwDao.select(new housework(housework_id, housework_name,  family_id, category_id, housework_level, noti_flag, noti_time, 
//				frequency, manual, fixed_role, variable_role, log));

		// 検索結果をリクエストスコープに格納する
		request.setAttribute("cardList", cardList);

		// 家事一覧ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/housework_list.jsp");
		dispatcher.forward(request, response);		
		
		
		System.out.println("cardList size: " + ((List<housework>) cardList).size());

		// TODO Auto-generated method stub 自動生成
		//doGet(request, response);
	}

}
