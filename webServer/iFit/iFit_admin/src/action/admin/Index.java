package action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import util.config.*;
import util.system.AESCrypto;
import util.system.MySqlFunction;
import util.system.StringUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


import dao.AdminDAO;
import dto.AddressCodeDTO;
import dto.AdminDTO;
import lombok.Data;

@Data
public class Index extends ActionSupport  {
	private Map session;
	private AdminDAO adminDAO;
	private AdminDTO adminDTO;
	private ActionContext context;
	private WebApplicationContext wac;
	private ActionConfig actionConfig = new ActionConfig();
	private DefaultSetting defaultSetting = new DefaultSetting();
	private Map<String, Object> defaultMap = new HashMap<String, Object>();
	private AlertMessage alertMessage = new AlertMessage();		//	정의되어있는 알림메세지
	
	private String id;				//	관리자 아이디
	private String password;		//	관리자 비밀번호
	
	private String validationJSONString = "";
	
	public Index() {
		ServletContext servletContext = ServletActionContext.getServletContext();
	    this.wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	    this.defaultMap = this.defaultSetting.init();
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	//	요소 초기화 및 세팅
	public void init(){
		this.adminDAO = (AdminDAO)this.wac.getBean("admin");
		this.adminDTO = new AdminDTO();
		this.context = ActionContext.getContext();	//session을 생성하기 위해
		this.session = this.context.getSession();		// Map 사용시
		this.defaultMap.put("pageTitle", this.actionConfig.getActionAttr(this.context.getName(),"pageTitle"));
		this.defaultMap.put("pageSubTitle", this.actionConfig.getActionAttr(this.context.getName(),"pageSubTitle"));
	}
	
	//	로그인 체크
	public String loginAction() throws Exception {	
		init();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		this.id = StringUtil.isNullOrSpace(this.id,"").trim();
		this.password = StringUtil.isNullOrSpace(this.password,"").trim();
		String passwordEncode = MySqlFunction.password(AESCrypto.encryptPassword(this.password));
		if(!validation()){
			return "validation";
		}
		paramMap.put("searchCol", "id");
		paramMap.put("searchVal", this.id);
		this.adminDTO = this.adminDAO.getAdminRow(paramMap);
		if(this.adminDTO == null){
			//아이디 불일치
			makeJSONString("not_member");
			return "validation";
		}else if(!this.adminDTO.getPassword().equals(passwordEncode)){
			//비밀번호 불일치
			makeJSONString("not_member");
			return "validation";
		}
		
		paramMap.clear();
//			session.put("admin_seq", this.adminDTO.getAdmin_seq());
		this.session.put("isAdmin", "true");
		this.context.setSession(this.session);
		
		return SUCCESS;
	}
	
	public String logoutAction(){
		init();
		
		this.session.remove("isAdmin");
		this.context.setSession(this.session);
		return SUCCESS;		
	}
	
	private boolean validation(){
		//	아이디 체크
		if(this.id.equals("")){
			makeJSONString("id_Null");
			return false;
		}
		
		//	비밀번호 체크
		if(this.password.equals("")){
			makeJSONString("password_Null");
			return false;
		}
		
		return true;
	}
	
	private void makeJSONString(String mode){
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		
		//	아이디 입력값 없음
		if(mode.equals("id_Null")){
			jsonArray = new JSONArray();
			
			jsonObject = new JSONObject();
			jsonObject.put("action","alert");
			jsonObject.put("msg","아이디를 입력해 주세요.");
			jsonArray.add(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("action","focus");
			jsonObject.put("id","id");
			jsonArray.add(jsonObject);
		}
		
		//	비밀번호 입력값 없음
		if(mode.equals("password_Null")){
			jsonArray = new JSONArray();
			
			jsonObject = new JSONObject();
			jsonObject.put("action","alert");
			jsonObject.put("msg","비밀번호를 입력해 주세요.");
			jsonArray.add(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("action","focus");
			jsonObject.put("id","password");
			jsonArray.add(jsonObject);
		}
		
		//	회원정보 불일치
		if(mode.equals("not_member")){
			jsonArray = new JSONArray();
			
			jsonObject = new JSONObject();
			jsonObject.put("action","alert");
			jsonObject.put("msg",this.alertMessage.getLoginError());
			jsonArray.add(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("action","input");
			jsonObject.put("id","id");
			jsonObject.put("value","");
			jsonArray.add(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("action","input");
			jsonObject.put("id","password");
			jsonObject.put("value","");
			jsonArray.add(jsonObject);
		}
		
		this.validationJSONString = jsonArray.toString();
	}
}
