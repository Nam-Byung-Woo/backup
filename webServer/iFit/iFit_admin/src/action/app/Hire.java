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
import dto.HireDTO;


import lombok.Data;

@Data
public class Hire extends ActionSupport  {
	
	private HireDAO hireDAO;
	private HireDTO hireDTO;
	private List<HireDTO> list;
	
	private Map session;
	private ActionContext context;
	private WebApplicationContext wac;
	private ActionConfig actionConfig = new ActionConfig();
	private DefaultSetting defaultSetting = new DefaultSetting();
	private Map<String, Object> defaultMap = new HashMap<String, Object>();
	private AlertMessage alertMessage = new AlertMessage();		//	정의되어있는 알림메세지
	
	private JSONObject jsonObject = new JSONObject();
	private String rtnString = "";
	
	private String seq = "";
	private String listStartSeq = "";
	private int pageNum = 0;
	private int totalCount = 0;
	private String code_type = "";
	private String mode = "";
	private String eupmyeondong_code = "";
	private int countPerPage_App = 0;

	public Hire() {
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
		this.hireDAO = (HireDAO)this.wac.getBean("hire");
		this.hireDTO = new HireDTO();
		this.context = ActionContext.getContext();	//session을 생성하기 위해
		this.session = this.context.getSession();		// Map 사용시
		this.defaultMap.put("pageTitle", this.actionConfig.getActionAttr(this.context.getName(),"pageTitle"));
		this.defaultMap.put("pageSubTitle", this.actionConfig.getActionAttr(this.context.getName(),"pageSubTitle"));
		this.countPerPage_App = Code.countPerPage_App;
	}
	
	//	HireList
	public String hireList() throws Exception {	
		init();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("isCount",true);
		paramMap.put("code_type",mode);
		paramMap.put("eupmyeondong_code",eupmyeondong_code);
		totalCount = (int)this.hireDAO.getHireList(paramMap);
		this.pageNum = this.pageNum == 0 || (this.pageNum*this.countPerPage_App>totalCount && this.pageNum*this.countPerPage_App-totalCount >= this.countPerPage_App)  ? 1  : this.pageNum;
		paramMap.put("isCount",false);
		paramMap.put("pageNum",this.pageNum);
		paramMap.put("countPerPage",this.countPerPage_App);
		paramMap.put("listStartSeq", this.listStartSeq);
		this.list = (List<HireDTO>)this.hireDAO.getHireList(paramMap);
		
		Gson gson = new Gson();
		System.out.println("리스트사이즈:::"+this.list.size());

		jsonObject.put("result", true);
		jsonObject.put("val", this.list.size() == 0 ? gson.toJson("") : gson.toJson(this.list));
		
		this.rtnString = jsonObject.toJSONString();

		return SUCCESS;
	}
	
//	//	HireListAddressChangeAjax
//	public String hireListAddressChangeAjax() throws Exception {	
//		init();
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		
//		paramMap.put("isCount",true);
//		paramMap.put("code_type",mode);
//		paramMap.put("eupmyeondong_code",eupmyeondong_code);
//		totalCount = (int)this.hireDAO.getHireList(paramMap);
//		this.pageNum = this.pageNum == 0 || (this.pageNum*this.countPerPage_App>totalCount && this.pageNum*this.countPerPage_App-totalCount >= this.countPerPage_App)  ? 1  : this.pageNum;
//		paramMap.put("isCount",false);
//		paramMap.put("pageNum",this.pageNum);
//		paramMap.put("countPerPage",this.countPerPage_App);
//		this.list = (List<HireDTO>)this.hireDAO.getHireList(paramMap);
//		
//		Gson gson = new Gson();
//		System.out.println("리스트사이즈:::"+this.list.size());
//		this.rtnString = this.list.size() == 0 ? gson.toJson("") : gson.toJson(this.list);
//		
//		this.rtnString += "@@{";
//		this.rtnString += "\"totalCount\":";
//		this.rtnString += this.totalCount;
//		this.rtnString += ",\"pageNum\":";
//		this.rtnString += this.pageNum;
//		this.rtnString += ",\"countPerPage_App\":";
//		this.rtnString += this.countPerPage_App;
//		this.rtnString += "}@@";
//		
//		return SUCCESS;
//	}
	
	//	HireView
	public String hireView() throws Exception {	
		init();
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("searchCol", "seq");
//		paramMap.put("searchVal", this.seq);
//		
//		this.hireDTO = this.hireDAO.getHireRow(paramMap);
//
//		Gson gson = new Gson();
//		
//		this.rtnString = gson.toJson(this.hireDTO);
		
		return SUCCESS;
	}
}
