package action.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import util.config.*;
import util.gcm.GCMServer;
import util.system.AESCrypto;
import util.system.MySqlFunction;
import util.system.StringUtil;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.HireDAO;
import dao.DeviceDAO;
import dto.HireDTO;
import dto.DeviceDTO;


import lombok.Data;

@Data
public class Device extends ActionSupport  {
	
	private DeviceDAO deviceDAO;
	private DeviceDTO deviceDTO;
	private List<DeviceDTO> list;
	
	private Map session;
	private ActionContext context;
	private WebApplicationContext wac;
	private ActionConfig actionConfig = new ActionConfig();
	private DefaultSetting defaultSetting = new DefaultSetting();
	private Map<String, Object> defaultMap = new HashMap<String, Object>();
	private AlertMessage alertMessage = new AlertMessage();		//	정의되어있는 알림메세지
	
	private JSONObject jsonObject = new JSONObject();
	private String rtnString = "";
	
	private String testParam1;				//	param1
	private String testParam2;				//	param2
	private String gcm_id;					//	param2

	public Device() {
		ServletContext servletContext = ServletActionContext.getServletContext();
	    this.wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	    this.defaultMap = defaultSetting.init();
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	//	요소 초기화 및 세팅
	public void init(){
		this.deviceDAO = (DeviceDAO)this.wac.getBean("device");
		this.deviceDTO = new DeviceDTO();
		this.context = ActionContext.getContext();	//session을 생성하기 위해
		this.session = this.context.getSession();		// Map 사용시
		this.defaultMap.put("pageTitle", this.actionConfig.getActionAttr(this.context.getName(),"pageTitle"));
		this.defaultMap.put("pageSubTitle", this.actionConfig.getActionAttr(this.context.getName(),"pageSubTitle"));
	}
	
	//	테스트메인
	public String testMain() throws Exception {	
		init();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		return SUCCESS;
	}
	
	//	테스트 폼 액션
	public String testAction() throws Exception {	
		init();
		Map<String, Object> gcmMap = new HashMap<String, Object>();
		gcmMap.put("mode", "push");
		gcmMap.put("title", testParam1);
		gcmMap.put("msg", testParam2);
		
		GCMServer gcmServer = new GCMServer();
		gcmServer.sender(gcmMap);
		return SUCCESS;
	}
	
	//	httpTest
	public String httpTestAction() throws Exception {	
		init();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
//		this.hireDAO = (HireDAO)this.wac.getBean("hire");
//		this.hireDTO = new HireDTO();
//		
//		this.list = this.hireDAO.getHireList(paramMap);
		
		Gson gson = new Gson();
		
		rtnString = gson.toJson(this.list);
		
		System.out.println("jsonString"+rtnString);
		
		return SUCCESS;
	}
	
	//	장치GCM등록
	public String insertDevice() throws Exception{	
		init();
		if(!selectDevice() && this.gcm_id != null && !this.gcm_id.equals("")){
			this.deviceDTO.setGcm_id(this.gcm_id);
			this.deviceDAO.insertDevice(this.deviceDTO);
			jsonObject.put("result", true);
			jsonObject.put("val", this.deviceDTO.getGcm_id());
		}else{
			jsonObject.put("result", false);
		}
		
		rtnString = jsonObject.toJSONString();
		return SUCCESS;
	}
	
	//	장치GCM삭제
	public String deleteDevice() throws Exception{	
		init();
		if(selectDevice() && this.gcm_id != null && !this.gcm_id.equals("")){
			this.deviceDAO.deleteDevice(this.deviceDTO);
		}
		
		jsonObject.put("result", true);
		jsonObject.put("val", "");
		
		rtnString = jsonObject.toJSONString();
		return SUCCESS;
	}
	
	public boolean selectDevice() throws Exception{
		boolean rtnBool = false;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("searchCol", "gcm_id");
		paramMap.put("searchVal", this.gcm_id);
		this.deviceDTO = this.deviceDAO.getDeviceOne(paramMap);
		if(this.deviceDTO == null){
			this.deviceDTO = new DeviceDTO();
			rtnBool = false;
		}else{
			rtnBool = true;
		}
		return rtnBool;
	}
}
