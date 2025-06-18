package servlet;

import java.io.IOException;
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
		                executeScheduledTask();
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
			                executeScheduledTask();
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
	private void executeScheduledTask() {
        // 定期実行する処理を実装
		
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
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
