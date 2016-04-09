package util.config;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ActionConfig{
	
	String boardConfigArray[];
	HashMap hashmap = new HashMap();
	String separator = "&";			// 조건이 여러가지 일 때 구분자
	String falseFlag = ":";				// 조건이 맞지 않을 때 returnURL 시작 부분

	public ActionConfig(){
		/************session체크 모든 action정보 [13.12.01 by.Nam]**********
		
		isAdmin : 관리자세션 필수여부(true/false)
		isMember : 회원세션 필수여부(true/false)
		pageTitle : action에 대한 title(설명)
		pageSubTitle : action에 대한 sub title(부제목)
		
		결제됬는지체크
		
		***********************************************************************/
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// ----------------------------struts.xml-----------------------------
		hashmap.put("index", 						"isAdmin => false,	isMember =>false,	pageTitle => 인덱스");
		hashmap.put("adminLogin", 				"isAdmin => false,	isMember =>false,	pageTitle => global");
		hashmap.put("admin", 						"isAdmin => false,	isMember =>false,	pageTitle => 리다이렉트용");
		
		// ----------------------------common.xml-----------------------------
		hashmap.put("pageTest",		 			"isAdmin => false,	isMember =>false,	pageTitle => 테스트용");
		hashmap.put("httpConnectTest", 			"isAdmin => false,	isMember =>false,	pageTitle => 테스트용");
		hashmap.put("address_search", 			"isAdmin => false,	isMember =>false,	pageTitle => 우편번호 찾기");
		hashmap.put("address_select_change", 	"isAdmin => false,	isMember =>false,	pageTitle => 시도 셀렉트 생성");
		
		// ----------------------------admin.xml-----------------------------
		hashmap.put("adminMain", 				"isAdmin => true,	isMember =>false,	pageTitle => ");
		hashmap.put("adminLogin", 				"isAdmin => false, 	isMember =>false,	pageTitle => ");
		hashmap.put("adminLoginAction", 		"isAdmin => false, 	isMember =>false,	pageTitle => ");
		hashmap.put("adminLogoutAction",		"isAdmin => true, 	isMember =>false,	pageTitle => ");
		hashmap.put("adminHireList", 			"isAdmin => true, 	isMember =>false,	pageTitle => 게시판,	pageSubTitle => 글목록 ");
		hashmap.put("hireWrite", 					"isAdmin => true, 	isMember =>false,	pageTitle => 게시판,	pageSubTitle => 글작성/글수정 ");
		hashmap.put("hireWriteAction", 			"isAdmin => true, 	isMember =>false,	pageTitle => 게시판,	pageSubTitle => 글작성/글수정 ");
		hashmap.put("pushTest", 					"isAdmin => true,	isMember =>false,	pageTitle => ");
		
		// ----------------------------app.xml-----------------------------
		hashmap.put("insertDevice", 								"isAdmin => false,	isMember =>false,	pageTitle => 앱 최초 실행시 DB INSERT");
		hashmap.put("deleteDevice", 								"isAdmin => false,	isMember =>false,	pageTitle => 앱 최초 실행시 등록되어있으면 추가발급을 위해 DB DELETE");
		hashmap.put("appMain",	 								"isAdmin => false,	isMember =>false,	pageTitle => 앱 메인화면");
		hashmap.put("appHireList",	 							"isAdmin => false,	isMember =>false,	pageTitle => 앱 메인화면");
		hashmap.put("appMyPage",	 							"isAdmin => false,	isMember =>false,	pageTitle => 앱 메인화면");
		hashmap.put("appSetting",	 								"isAdmin => false,	isMember =>false,	pageTitle => 앱 메인화면");
		hashmap.put("appHireList", 								"isAdmin => false,	isMember =>false,	pageTitle => 채용정보 게시판 리스트");
		hashmap.put("appHireListAddressChangeAjax", 		"isAdmin => false,	isMember =>false,	pageTitle => 채용정보 게시판 리스트(Ajax)");
		hashmap.put("appHireView",	 						"isAdmin => false,	isMember =>false,	pageTitle => 채용정보 게시판 상세페이지");
		hashmap.put("make_address_select", 					"isAdmin => false,	isMember =>false,	pageTitle => 시도 셀렉트 관련");
		
		// ----------------------------app.xml-----------------------------
		hashmap.put("insertDevice", 				"isAdmin => false,	isMember =>false,	pageTitle => 앱 최초 실행시 DB INSERT");
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	public String getActionAttr(String actionName, String key){		// action이름을 이용하여 원하는 key 정보 추출
		String rtnVal = "";
		if(actionName == null) return "";
		if(!hashmap.containsKey(actionName)){
			return "";
		}else{
			String board_config_str = hashmap.get(actionName).toString().trim();
			
			String config_array[] = board_config_str.split(",");
			
			for(int i = 0 ; i < config_array.length ; i++){
				
				String attr[] = config_array[i].trim().split("=>");
				if(attr[0].trim().equalsIgnoreCase(key)){
					if(attr.length<2 || attr[1].equals("''")) return "";
					return attr[1].trim();
				}
			}
			
		}	
		return rtnVal;
	}
	
	public boolean ActionNameChk(String actionName){		// Action이름 유효성검사
		if(actionName == null || actionName.equals("")){			// 파라미터가 없을경우 실패
			return false;
		}else if(!hashmap.containsKey(actionName)){				// hashmap에 명시되지 않은경우 실패
			return false;
		}else{																// 그외의 경우 성공
			return true;
		}
	}
	
	/*
	private boolean sessionLevChk(String[] attrList, String userLev){		// 접근권한체크(LEV)
		for(int i=0; i<attrList.length; i++){
			if(attrList[i].equals(code.getMemberLevCodeMap().get(userLev))){		// 접근 가능한 lev 인지 체크
				return true;			// 일치 할 경우 true
			}
		}
		return false;
	}
	*/
}