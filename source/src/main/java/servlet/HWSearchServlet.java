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
 * Servlet implementation class HWSearchServlet
 */
@WebServlet("/HWSearchServlet")
public class HWSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private Object cardList;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		 もしもログインしていなかったらログインサーブレットにリダイレクトする

		HttpSession session = request.getSession();
		if (session.getAttribute("user_id") == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}

	    

		String family_Id = (String)session.getAttribute("family_id");
		System.out.println("取得したファミリーID" + family_Id);
		// houseworkDAOを使用してuserListを取得し、リクエスト属性に設定する
	    List<dto.user> userList = new userDAO().getUsersByFamilyid(family_Id);
	    request.setAttribute("userList", userList);
		
		//初期化
		request.setCharacterEncoding("UTF-8");
		/* 一旦コメントアウト 
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
		request.setAttribute("cardList", cardList);*/
	if (request.getParameter("search") == null) {
		 // パラメータ取得
		
	    String searchType = request.getParameter("searchType");
	    String sortOrder = request.getParameter("sortOrder");
	    String housework_name = request.getParameter("housework_name");
	    String category_id = request.getParameter("category_id");
	    String noti_flag = request.getParameter("noti_flag");
	    String frequency = request.getParameter("frequency");
	    List<housework> cardList = null;  // これが大事
	    // sortOrderがnullまたはasc/desc以外ならascにする
	    if (sortOrder == null || (!sortOrder.equals("asc") && !sortOrder.equals("desc"))) {
	        sortOrder = "asc";
	    }
	    
	    if (noti_flag == null) {

		    houseworkDAO hwsDao = new houseworkDAO();
	
		    // searchTypeによりカテゴリIDを決定
		    int categoryId = 0;  // 0 = 全件取得
	
		    if ("掃除".equals(searchType)) {
		        categoryId = 1;
		    } else if ("洗濯".equals(searchType)) {
		        categoryId = 2;
		    } else if ("料理".equals(searchType)) {
		        categoryId = 3;
		    } else if ("その他".equals(searchType)) {
		        categoryId = 4;
		    } else {
		        // 「一覧」やnullなどは全件取得
		        categoryId = 0;
		    }
	
		    // DAOからデータ取得（カテゴリ指定 or 全件）
		    if (categoryId == 0) {
		        cardList = hwsDao.selectAllSorted(sortOrder, family_Id);
		    } else {
		        cardList = hwsDao.selectByCategorySorted(categoryId, sortOrder, family_Id);
		    }
		    // 結果をリクエストスコープへ
		    request.setAttribute("cardList", cardList);
		    request.setAttribute("searchType", searchType);
		    request.setAttribute("sortOrder", sortOrder);
	
			// 家事一覧ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/housework_list.jsp");
			dispatcher.forward(request, response);
	    } else {
			houseworkDAO hwDao = new houseworkDAO();		
//			cardList = hwDao.select(new housework(housework_id, housework_name,  family_id, category_id, housework_level, noti_flag, noti_time, 
//					frequency, manual, fixed_role, variable_role, log));
			// 検索処理
	        cardList = hwDao.searchHouseworkSorted(category_id, housework_name, frequency, noti_flag, sortOrder, family_Id);
		   
			// 検索結果をリクエストスコープに格納する
		    
	        request.setAttribute("housework_name", housework_name);
	        request.setAttribute("category_id", category_id);
	        request.setAttribute("noti_flag", noti_flag);
	        request.setAttribute("frequency", frequency);
			request.setAttribute("cardList", cardList);
	    
			// 家事一覧ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/housework_list.jsp");
			dispatcher.forward(request, response);		
		}
			
	} else {
		request.setCharacterEncoding("UTF-8");
		String searchType = request.getParameter("searchType");
		//int housework_id = 0;
		String housework_name = "";
		String family_id = "";
		int category_id = 0;
		//int housework_level = 0;
		int noti_flag = 0;
		//String noti_time = "";
		int frequency = 0;
		//String manual = "";
		//String fixed_role = "";
		//String variable_role = "";
		//int log = 0;
		//String role = "";
		List<housework> cardList = null;  // これが大事
		
		// 押されたボタンのnameにより格納するカテゴリIDを変更
		if ("掃除".equals(searchType)) {
			//掃除のみ表示
			category_id = 1;
			houseworkDAO hwDao = new houseworkDAO();		
			cardList = hwDao.searchclean(category_id, family_Id);


		} else if ("洗濯".equals(searchType)) {
			// 洗濯のみ表示
			category_id = 2;
			houseworkDAO hwDao = new houseworkDAO();		
			cardList = hwDao.searchwash(category_id, family_Id);
			
		} else if ("料理".equals(searchType)) {
			// 料理のみ
			category_id = 3;
			houseworkDAO hwDao = new houseworkDAO();		
			cardList = hwDao.searchcook(category_id, family_Id);

			
		} else if ("その他".equals(searchType)) {
			// その他
			category_id = 4;
			houseworkDAO hwDao = new houseworkDAO();
			cardList = hwDao.searchother(category_id, family_Id);

			
		} else if ("一覧".equals(searchType)) {
			// 全件表示
			houseworkDAO hwDao = new houseworkDAO();		
			cardList = hwDao.all(family_Id);

		}
		
//		housework_id = Integer.parseInt(request.getParameter("housework_id"));
		housework_name = request.getParameter("housework_name");
		family_id = request.getParameter("family_id");
		category_id = Integer.parseInt(request.getParameter("category_id"));
//		housework_level = Integer.parseInt(request.getParameter("housework_level"));
		noti_flag = Integer.parseInt(request.getParameter("noti_flag"));
//		noti_time = request.getParameter("noti_time");
		frequency = Integer.parseInt(request.getParameter("frequency"));
//		manual = request.getParameter("manual");
//		fixed_role = request.getParameter("fixed_role");
//		variable_role = request.getParameter("variable_role");
//		log = Integer.parseInt(request.getParameter("log"));

		// 検索処理を行う
//		List<housework> cardList = null;
		houseworkDAO hwDao = new houseworkDAO();		
//		cardList = hwDao.select(new housework(housework_id, housework_name,  family_id, category_id, housework_level, noti_flag, noti_time, 
//				frequency, manual, fixed_role, variable_role, log));
		// 検索処理
        cardList = hwDao.searchHousework(family_Id, category_id, housework_name, frequency, noti_flag);


		// 検索結果をリクエストスコープに格納する
        request.setAttribute("housework_name", housework_name);
        request.setAttribute("family_id", family_id);
        request.setAttribute("category_id", category_id);
        request.setAttribute("noti_flag", noti_flag);
        request.setAttribute("frequency", frequency);
		request.setAttribute("cardList", cardList);
		

		// 家事一覧ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/housework_list.jsp");
		dispatcher.forward(request, response);		
	}
		
	}

//↓POST
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ↓
		// 一覧画面で家族情報を受け取りたい
		HttpSession session = request.getSession();
		String family_Id = (String)session.getAttribute("family_id");
		System.out.println("取得したファミリーID" + family_Id);		
	    // userListを再度取得してリクエスト属性に設定 (JSPでプルダウンを表示するため)
	    List<dto.user> userList = new userDAO().getUsersByFamilyid(family_Id);
	    request.setAttribute("userList", userList);
	    // ↑
		
		    doGet(request, response);
	}

		// もしもログインしていなかったらログインサーブレットにリダイレクトする
//		HttpSession session = request.getSession();
//		if (session.getAttribute("user_id") == null) {
//			response.sendRedirect(request.getContextPath() + "/LoginServlet");
//			return;
//		}
		
		// リクエストパラメータを取得する
		//初期化
/*		request.setCharacterEncoding("UTF-8");
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
		housework_name = request.getParameter("housework_name");
//		family_id = request.getParameter("family_id");
		category_id = Integer.parseInt(request.getParameter("category_id"));
//		housework_level = Integer.parseInt(request.getParameter("housework_level"));
		noti_flag = Integer.parseInt(request.getParameter("noti_flag"));
//		noti_time = request.getParameter("noti_time");
		frequency = Integer.parseInt(request.getParameter("frequency"));
//		manual = request.getParameter("manual");
//		fixed_role = request.getParameter("fixed_role");
//		variable_role = request.getParameter("variable_role");
//		log = Integer.parseInt(request.getParameter("log"));

		// 検索処理を行う
//		List<housework> cardList = null;
		houseworkDAO hwDao = new houseworkDAO();		
//		cardList = hwDao.select(new housework(housework_id, housework_name,  family_id, category_id, housework_level, noti_flag, noti_time, 
//				frequency, manual, fixed_role, variable_role, log));
		// 検索処理
        List<housework> cardList = hwDao.searchHousework(category_id, housework_name, frequency, noti_flag);


		// 検索結果をリクエストスコープに格納する
		request.setAttribute("cardList", cardList);

		// 家事一覧ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/housework_list.jsp");
		dispatcher.forward(request, response);		
		
		
		System.out.println("cardList size: " + ((List<housework>) cardList).size());

		// TODO Auto-generated method stub 自動生成
		//doGet(request, response);
	} */

} 
