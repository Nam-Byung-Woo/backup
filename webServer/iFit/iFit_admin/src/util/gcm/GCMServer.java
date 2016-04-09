package util.gcm;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
 
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import dao.DeviceDAO;
import dto.DeviceDTO;
 
/**
 * GCM 메시지 발송을 위한 샘플 구현 소스이다.
 * 하단의 내용을 커스터마이즈하여 실제 서버단에서 발송모듈로 구현 가능하다.
 * @author NAM
 *
 */
public class GCMServer {
     
    private static final String APIKEY = "AIzaSyCPZI9DsgBR7BjmzoGvmH0hUmRRn2Iinno"; //GCM에서 발급받은 ApiKey를 입력
    private static final boolean SHOW_ON_IDLE = true;    //기기가 활성화 상태일때 보여줄것인지
    private static final int LIVE_TIME = 1800;  //이건 시간이 아니고 초를 의미..
    private static final int RETRY = 3;  //메시지 전송실패시 재시도 횟수
    
    private List<DeviceDTO> deviceList;
     
    public GCMServer(){
    	System.out.println("GCMServer.java | 생성자 시작");
    	ServletContext servletContext = ServletActionContext.getServletContext();
    	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    	DeviceDAO deviceDAO = (DeviceDAO)wac.getBean("device");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		this.deviceList = deviceDAO.selectDeviceList(paramMap);
    }
    
    public void sender(Map<String, Object> paramMap) {
    	List<String> list = new ArrayList<String>();
    	MulticastResult multiResult;
    	
    	for(int i=0; i<this.deviceList.size(); i++){
    		list.add(this.deviceList.get(i).getGcm_id());
    	}
    	
        try {
//        	String setTicker = "내용 : 메시지가 보입니다";
  
            Sender sender = new Sender(APIKEY);
             
            /**
             * message Build 부분에서 addData로 추가한 값은 어플리케이션의
             * onMessage(context, intent)에서 Intent로 전달되며
             * intent.getExtras().getString("title")형태로 얻어와 사용 가능하다.
             */
            
            System.out.println(list.toString());
            
    		System.out.println("GCMServer.java sender | " + paramMap.get("mode").toString() + "모드");
        	
    		Message.Builder messageBuilder = new Message.Builder();
    		messageBuilder.delayWhileIdle(SHOW_ON_IDLE);
    		messageBuilder.timeToLive(LIVE_TIME).build();
            
            for( String key : paramMap.keySet() ){
            	messageBuilder.addData(key, URLEncoder.encode((String)paramMap.get(key), "UTF-8"));
//                System.out.println( String.format("키 : %s, 값 : %s", key, map.get(key)) );
            }
            
            //발송할 메시지, 발송할 타깃(RegistrationId), Retry 횟수
            multiResult = sender.send(messageBuilder.build(), list, RETRY);
            if (multiResult != null) {
            	List<Result> resultList = multiResult.getResults();
            	for (Result result : resultList) {
            		System.out.println("Send OK : " + result.getMessageId());
            	}
            } else {
                int error = multiResult.getFailure();
                System.out.println("Send fail : " + error);
            }
  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}