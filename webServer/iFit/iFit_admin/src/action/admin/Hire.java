package action.admin;

import java.io.File;
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
import util.file.FileUpload;
import util.gcm.GCMServer;
import util.system.AESCrypto;
import util.system.MySqlFunction;
import util.system.StringUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


import dao.HireDAO;
import dto.HireDTO;
import lombok.Data;

@Data
public class Hire extends ActionSupport  {
	private Map session;
	private HireDAO hireDAO;
	private HireDTO hireDTO;
	private List<HireDTO> list;
	private Paging paging = new Paging();
	private ActionContext context;
	private WebApplicationContext wac;
	private ActionConfig actionConfig = new ActionConfig();
	private DefaultSetting defaultSetting = new DefaultSetting();
	private Map<String, Object> defaultMap = new HashMap<String, Object>();
	private AlertMessage alertMessage = new AlertMessage();		//	정의되어있는 알림메세지
	private String pagingHTML = "";
	
	private Code code = new Code();

	private List<String> uploadsContentType = new ArrayList<String>(); // 첨부파일 종류
	private List<String> uploadsFileName = new ArrayList<String>(); // 첨부파일명
	private List<File> uploads = new ArrayList<File>(); // 첨부파일 리스트
	private String FilePath_detail = "upload/hire/"; // 세부 경로(test,real동일)
	
	private int pageNum = 0;				//	페이지번호
	
	private String title="";							//	글제목
	private String brand_name="";				//	업체명
	private String eupmyeondong_code="";	//	읍면동(법정동) 코드
	private String address_road="";				//	도로명주소
	private String address_jibun="";				//	지번주소
	private String gender="";						//	성별
	private String age_start="";					//	최저연령
	private String age_end="";					//	최고연령
	private String wage_kind="";					//	급여종류
	private int wage=0;							//	급여
	
	private String validationJSONString = "";

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
	}
	
	//	리스트
	public String hireList() throws Exception{	
		init();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("isCount",true);
		int totalCount = (int)this.hireDAO.getHireList(paramMap);
		this.pageNum = this.pageNum == 0 || (this.pageNum*Code.countPerPage>totalCount && this.pageNum*Code.countPerPage-totalCount >= Code.countPerPage)  ? 1  : this.pageNum;
		
		paramMap.put("isCount",false);
		paramMap.put("pageNum",this.pageNum);
		paramMap.put("countPerPage",Code.countPerPage);
		this.list = (List<HireDTO>)this.hireDAO.getHireList(paramMap);
		
		this.pagingHTML = paging.getPaging(totalCount, this.pageNum, Code.countPerPage);
		
		return SUCCESS;
	}
	
	//	글작성 or 글수정 폼
	public String hireWrite() throws Exception {	
		init();
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		this.list = this.hireDAO.getHireList(paramMap);
		
		return SUCCESS;
	}
	
	//	글작성 or 글수정 DB저장
	public String hireWriteAction() throws Exception {	
		init();
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		this.list = this.hireDAO.getHireList(paramMap);
		
		if(!validation()){
			return "validation";
		}
		
		FileUpload file = new FileUpload();
		String[] filePath = new String[1];
		String saveFileName = "";
		long[] fileSize = new long[1];
		int maxSize = 50;		//	MB단위로 넣는다
		if(!file.fileUploadChk(getUploads(), getUploadsFileName(), this.uploads, maxSize)){	// 첨부파일 확인
			return SUCCESS;	
		}else{
			for (int i = 0; i < this.uploads.size(); i++) { // 파일이 있는경우 업로드
				saveFileName = file.fileUpload(getUploadsFileName(), getUploads(), this.FilePath_detail, i, 0);	//파일업로드
				filePath[i] = getUploadsFileName().get(i);
				fileSize[i] = getUploads().get(i).length();
			}
		
			this.defaultMap.put("proc", "Y");
			this.defaultMap.put("rtnURL", StringUtil.getCompleteUrl("/admin/hireList"));
			this.hireDTO.setTitle(this.title);
			this.hireDTO.setBrand_name(this.brand_name);
			this.hireDTO.setEupmyeondong_code(this.eupmyeondong_code);
			this.hireDTO.setAddress_road(this.address_road);
			this.hireDTO.setAddress_jibun(this.address_jibun);
			this.hireDTO.setGender(this.gender);
			this.hireDTO.setAge_start(this.age_start);
			this.hireDTO.setAge_end(this.age_end);
			this.hireDTO.setWage_kind(this.wage_kind);
			this.hireDTO.setWage(this.wage);
//			filePath[0] ->업로드시실제파일명
			this.hireDTO.setFile_name(saveFileName);	//	저장된파일명
			
			this.hireDAO.wirteHire(this.hireDTO);
			
			Map<String, Object> gcmMap = new HashMap<String, Object>();
			gcmMap.put("mode", "refresh");
			gcmMap.put("target", "appHireList");
			gcmMap.put("eupmyeondong_code", this.eupmyeondong_code);
			GCMServer gcmServer = new GCMServer();
			gcmServer.sender(gcmMap);
		}
		
		return SUCCESS;
	}
	
	public String pushTest(){
		Map<String, Object> gcmMap = new HashMap<String, Object>();
		gcmMap.put("mode", "push");
		gcmMap.put("title", "제목테스트");
		gcmMap.put("msg", "메세지테스트");
		GCMServer gcmServer = new GCMServer();
		gcmServer.sender(gcmMap);
		return SUCCESS;
	}
	
	private boolean validation(){
		//	제목 체크
		if(this.title.equals("")){
			makeJSONString("title_Null");
			return false;
		}
		
		//	업체명 체크
		if(this.brand_name.equals("")){
			makeJSONString("brand_name_Null");
			return false;
		}
		
		//	주소 체크
		if(this.eupmyeondong_code.equals("") || this.address_road.equals("") || this.address_jibun.equals("")){
			makeJSONString("address_Null");
			return false;
		}
		
		return true;
	}
	
	private void makeJSONString(String mode){
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		
		//	제목 입력값 없음
		if(mode.equals("title_Null")){
			jsonArray = new JSONArray();
			
			jsonObject = new JSONObject();
			jsonObject.put("action","alert");
			jsonObject.put("msg","제목을 입력해 주세요.");
			jsonArray.add(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("action","focus");
			jsonObject.put("id","title");
			jsonArray.add(jsonObject);
		}
		
		//	업체명 입력값 없음
		if(mode.equals("brand_name_Null")){
			jsonArray = new JSONArray();
			
			jsonObject = new JSONObject();
			jsonObject.put("action","alert");
			jsonObject.put("msg","업체명을 입력해 주세요.");
			jsonArray.add(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("action","focus");
			jsonObject.put("id","brand_name");
			jsonArray.add(jsonObject);
		}
		
		//	주소 입력값 없음
		if(mode.equals("address_Null")){
			jsonArray = new JSONArray();
			
			jsonObject = new JSONObject();
			jsonObject.put("action","alert");
			jsonObject.put("msg","올바른 주소를 입력해 주세요.");
			jsonArray.add(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("action","focus");
			jsonObject.put("id","address_road");
			jsonArray.add(jsonObject);
		}

		
		this.validationJSONString = jsonArray.toString();
	}
}
