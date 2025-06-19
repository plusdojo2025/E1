package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dao.notificationDAO;
import dto.housework;


/**
 * Servlet implementation class ReminderServlet
 */

@WebServlet("/ReminderServlet")
public class ReminderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Timer timer;
	private String a;//家事名取り出し用

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession session=request.getSession(false);
		
		String user_id=(String) session.getAttribute("user_id");
		notificationDAO notiDao = new notificationDAO();
		List<housework> notiList = notiDao.select_rimnoti(user_id);
		
		JSONObject json = new JSONObject();
		/*int role = Integer.parseInt(request.getParameter("role"));
		try {
			// データベースとの接続の確立
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/e1_db";
			Connection con = DriverManager.getConnection(url, "root", "password");


			//DBから指定ロールのユーザだけを抽出
			String sql1= "SELECT * from USER WHERE ROLE=?";
			//指定ロールユーザが何人いるか確認しループ処理のカウンターにする
			String sql2= "SELECT count(*) as number from USER WHERE ROLE=?";
			//SQLインジェクション対策
//			PreparedStatement stmt1 = con.prepareStatement(sql1);
//			PreparedStatement stmt2 = con.prepareStatement(sql2);
			PreparedStatement stmt1 = con.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			PreparedStatement stmt2 = con.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			//　SQL文"?"の箇所に値を埋める
			stmt1.setInt(1, role);
			stmt2.setInt(1, role);

			//DBに対しQuery実行。rsに実行結果を蓄積
			ResultSet rs2 = stmt2.executeQuery();
			//結果セットの最終行に合計数なのでlastを呼び出してDBカーソルを合わせる
			rs2.last();
			int num = rs2.getInt("number");
			//SQLの実行結果の処理

			String role_name;
			String[][] profile=new String[num][2];
			int i=0;
			ResultSet rs1 = stmt1.executeQuery();
			while (rs1.next()) {
				//profile = new String[rs.getRow()][2];
				String user = rs1.getString("USERNAME");
				int urole = rs1.getInt("ROLE");
				if (urole == 1)
					role_name = "管理者";
				else if (urole == 2)
					role_name = "編集者";
				else
					role_name = "寄稿者";

				 profile[i][0] = user;
				 profile[i][1] = role_name;
				 i++;

				//json.put("username",user);
				//json.put("role", role_name);
				//System.out.printf("ユーザ名:%s 役割:%s\n", user, role_name);
			}*/
			//json.put("任意の名前",値)
			//json.put("data", a);
			//System.out.println("user:"+Arrays.deepToString(a));

			//DB接続終了
			/*rs1.close();
			rs2.close();
			stmt1.close();
			stmt2.close();
			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	    //httpヘッダー送信の登録
		/*response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");
	    //送信データの登録
		PrintWriter out = response.getWriter();
		//送信データをネットストリームへ書き込む
		out.print(json);*/
	//}

		
		
		for(int i=0;i<notiList.size();i++) {
			a=notiList.get(i).getHousework_name();
			//頻度をString配列に変換→int配列に変換→intだと配列の一つ一つを読み取ってくれないのでIntegerに変換
			int[] freqArray=Stream.of((notiList.get(i).getFrequency()).split(",")).mapToInt(Integer::parseInt).toArray();
			List<Integer> freqList=Arrays.stream(freqArray).boxed().collect(Collectors.toList());
			
			//毎日
			if(freqList.contains(0)) {
				TimerTask task = new TimerTask() {
		            @Override
		            public void run() {
		                // 実行したい処理をここに書く
		                //executeScheduledTask();
		            	json.put("data",a);
		                //httpヘッダー送信の登録
		        		response.setContentType("application/json");
		        		response.setHeader("Cache-Control", "nocache");
		        		response.setCharacterEncoding("utf-8");
		        		//送信データの登録
		        		PrintWriter out;
						try {
							out = response.getWriter();
							//送信データをネットストリームへ書き込む
			        		out.print(json);
						} catch (IOException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
		        		
		            }
		        };

		        // スケジュール設定
		        Calendar calendar = Calendar.getInstance();
		        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt((notiList.get(i).getNoti_time()).substring(0,2)));
		        calendar.set(Calendar.MINUTE, Integer.parseInt((notiList.get(i).getNoti_time()).substring(3,5)));
		        calendar.set(Calendar.SECOND, Integer.parseInt((notiList.get(i).getNoti_time()).substring(6,8)));

		        // 24時間 = 24 * 60 * 60 * 1000 ミリ秒
		        timer.scheduleAtFixedRate(task, calendar.getTime(), 24 * 60 * 60 * 1000);
		    }
			
			//毎日以外
			for(int k=1;k<8;k++) {
				if(freqList.contains(k)) {
			    	TimerTask task = new TimerTask() {
			            @Override
			            public void run() {
			                // 実行したい処理をここに書く
			                //executeScheduledTask();
			                json.put("data",a);
			                //httpヘッダー送信の登録
			        		response.setContentType("application/json");
			        		response.setHeader("Cache-Control", "nocache");
			        		response.setCharacterEncoding("utf-8");
			        		//送信データの登録
			        		PrintWriter out;
							try {
								out = response.getWriter();
								//送信データをネットストリームへ書き込む
				        		out.print(json);
							} catch (IOException e) {
								// TODO 自動生成された catch ブロック
								e.printStackTrace();
							}
			        		
			            }
			        };
	
			        // スケジュール設定
			        Calendar calendar = Calendar.getInstance();
			        calendar.set(Calendar.DAY_OF_WEEK, k);
			        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt((notiList.get(i).getNoti_time()).substring(0,2)));
			        calendar.set(Calendar.MINUTE, Integer.parseInt((notiList.get(i).getNoti_time()).substring(3,5)));
			        calendar.set(Calendar.SECOND, Integer.parseInt((notiList.get(i).getNoti_time()).substring(6,8)));
			        
			        //複数曜日設定の場合
			        if(freqArray.length>=2) {
			        	for(int j=1;j<freqArray.length;j++) {
			        	calendar.add(Calendar.DAY_OF_WEEK, freqArray[j]);
			        	}
			        }
			        
			        timer.scheduleAtFixedRate(task, calendar.getTime(), 7 * 24 * 60 * 60 * 1000);
		    }}
			
		}
			
		
		
}
	/*private void executeScheduledTask() {
        // 定期実行する処理を実装
		JSONObject json = new JSONObject();
		json.put("data", a);
		
		//httpヘッダー送信の登録
		/*response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");
		//送信データの登録
		PrintWriter out = response.getWriter();
		//送信データをネットストリームへ書き込む
		out.print(json);*/
		/*SystemTray tray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().createImage(
        		getClass().getResource("仮置き.png"));

        	TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
	        trayIcon.setImageAutoSize(true);
	        trayIcon.setToolTip("System tray icon demo");
	    try {
	    	tray.add(trayIcon);
	        trayIcon.setImageAutoSize(true);
        }catch(AWTException e){
	    	System.out.println();
	    }
		trayIcon.displayMessage(a+"の時間です",
                "カジミエール", MessageType.INFO);*/
    //}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
