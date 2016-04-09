package action.address;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

//import net.sf.json.xml.XMLSerializer;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import util.config.*;
import util.gcm.GCMServer;
import util.system.AESCrypto;
import util.system.MySqlFunction;
import util.system.StringUtil;
import util.system.URLConnect;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.AddressCodeDAO;
import dto.AddressCodeDTO;

import lombok.Data;

@Data
public class Address extends ActionSupport  {
	
	private List<AddressCodeDTO> list; 
	
	private Map session;
	private ActionContext context;
	private WebApplicationContext wac;
	private ActionConfig actionConfig = new ActionConfig();
	private DefaultSetting defaultSetting = new DefaultSetting();
	private Map<String, Object> defaultMap = new HashMap<String, Object>();
	private AlertMessage alertMessage = new AlertMessage();		//	정의되어있는 알림메세지
	
	private String rtnString = "";
	
	private String mode = "";						//	셀렉트 모드
	private String sido_selected = "";				//	선택된 시도명
	private String sigungu_selected = "";		//	선택된 시군구명
	private String keyword = "";					//	검색키워드
	private String currentPage = "";				//	선택한 페이지

	public Address() {
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
		this.context = ActionContext.getContext();	//session을 생성하기 위해
		this.session = this.context.getSession();		// Map 사용시
		this.defaultMap.put("pageTitle", this.actionConfig.getActionAttr(this.context.getName(),"pageTitle"));
		this.defaultMap.put("pageSubTitle", this.actionConfig.getActionAttr(this.context.getName(),"pageSubTitle"));
	}
	
	//	주소찾기시작
	public String address_search() throws Exception{	
		init();
		String countPerPage = "10";
		String confmKey = "bnVsbDIwMTUwMTE1MTMwNDAz";
		String keyword = this.keyword;
		String apiUrl= "http://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage="+this.currentPage+"&countPerPage="+countPerPage+"&keyword="+URLEncoder.encode(keyword,"UTF-8")+"&confmKey="+confmKey;
		String apiText = URLConnect.start(apiUrl);

		Map<String, Object> rtnMap = new HashMap<String, Object>();

		this.rtnString = StringUtil.xmlDeclareRemove(apiText);
		
		return SUCCESS;
	}
	
	// 시도, 구군, 읍면동 셀렉트박스
	public String address_select_change() throws Exception{
		AddressCodeDAO dao = (AddressCodeDAO)wac.getBean("addressCode");
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
//		String rtnTXT="";
		paramMap.put("mode", mode);
		paramMap.put("sido_selected", sido_selected);
		paramMap.put("sigungu_selected", sigungu_selected);
		list = dao.getAddressCodeList(paramMap);
		
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		jsonArray = new JSONArray();
//		jsonObject.put("action","alert");
//		jsonObject.put("msg","제목을 입력해 주세요.");
//		jsonArray.add(jsonObject);
		
		
		
		String tempValue = "";
		String[] rtnArr = new String[list.size()];
		for(int i=0; i<list.size(); i++){
			jsonObject = new JSONObject();
			if(mode.equals("sido")){
//				tempValue = list.get(i).getSido_name();
				jsonObject.put("code",list.get(i).getEupmyeondong_code());
				jsonObject.put("name",list.get(i).getSido_name());
			}else if(mode.equals("sigungu")){
				jsonObject.put("code",list.get(i).getEupmyeondong_code());
				jsonObject.put("name",list.get(i).getSigungu_name());
//				tempValue = list.get(i).getSigungu_name();
			}else if(mode.equals("eupmyeondong")){
				jsonObject.put("code",list.get(i).getEupmyeondong_code());
				jsonObject.put("name",list.get(i).getEupmyeondong_name());
//				tempValue = list.get(i).getEupmyeondong_name();
			}
//			rtnTXT += "<option value='"+tempValue+"'>"+tempValue+"</option>";
//			rtnArr[i] = tempValue;
			jsonArray.add(jsonObject);
		}
		
		Gson gson = new Gson();
//		rtnMap.put("rtnTXT", rtnTXT);
		this.rtnString = gson.toJson(jsonArray);
		
		return SUCCESS;
	}
	
}
