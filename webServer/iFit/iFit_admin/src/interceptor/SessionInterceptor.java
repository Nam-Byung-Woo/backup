package interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import util.config.ActionConfig;
import util.system.StringUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionInterceptor extends AbstractInterceptor {
	ActionConfig actionConfig = new ActionConfig();
	/*
		
		Code code = new Code();
		
		/************session체크 모든 intercept [13.08.28 by.Nam]**********
		
		접근하는 모든페이지에 대해 체크한다.
		error종류는 struts.xml -> global-results 명시되어 있는 4가지
		ActionConfig에서 체크한 후 return 받아온다.
		1. login	: 로그인이 필요할 경우
		 (1-1) loginPop : 팝업용 로그인
		2. auth	: 접근 권한이 없을 경우
		3. exception	: 기타 모든 오류
		
		***********************************************************************/

        @Override
        public String intercept(ActionInvocation invocation) throws Exception {
        	System.out.println("인터셉터시작" + "(sessionIntercepteor.java)");
        	ActionContext context = ActionContext.getContext();
        	ServletContext servletContext = ServletActionContext.getServletContext();
        	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        	HttpServletRequest request = ServletActionContext.getRequest();
        	Map<String, Object> session = (Map<String, Object>) context.getSession();
        	
        	String prevPath = request.getHeader("referer");				//	이동 요청한 페이지 전체 경로(이전페이지)
        	String fullURL = request.getRequestURL().toString();			//	이동할 페이지 전체 경로(다음페이지)
        	String nextAction = request.getRequestURI().substring(1);	//	이동할 페이지 액션(다음페이지)
        	String hostURL = fullURL.replace(nextAction,"");				//	서버URL 경로
        	String prevAction = "";												//	이동 요청한 페이지 액션(이전페이지)

        	prevPath = prevPath==null ? "" : prevPath;
        	if(prevPath.equals(hostURL)){
        		prevAction = StringUtil.getCompleteUrl("/index");
        	}else if(prevPath.replace(hostURL, "").equals(StringUtil.getCompleteUrl("member/memberLogin"))){
        		prevAction = (String)session.get("prevAction");
        	}else{
        		prevAction = prevPath.replace(hostURL, "");		
        	}
        	
        	String isAdminSession = (String)session.get("isAdmin");		//	관리자세션
        	String isMemberSession = (String)session.get("isMember");	//	회원세션
        	
        	isAdminSession = StringUtil.isNullOrSpace(isAdminSession,"").trim();
        	isMemberSession = StringUtil.isNullOrSpace(isMemberSession,"").trim();
        	
        	String isAdminValue = actionConfig.getActionAttr(context.getName(), "isAdmin");			// Action의 isAdmin 값 추출
        	String isMemberValue = actionConfig.getActionAttr(context.getName(), "isMember");		// Action의 isMember 값 추출
        	
        	String result = "";
        	
        	if(!actionConfig.ActionNameChk(context.getName())){		// 1. Action이 명시되어 있는지 체크
        		result = "exception";
        	}else if(Boolean.valueOf(isAdminValue).booleanValue() && !Boolean.valueOf(isAdminSession).booleanValue()){
        		result = "adminLogin";
        	}else if(Boolean.valueOf(isMemberValue).booleanValue() && !Boolean.valueOf(isMemberSession).booleanValue()){
        		result = "memberLogin";
        	}
        	
        	System.out.println("context::::" + context.getName() + "(sessionIntercepteor.java)");
        	System.out.println("result::::" + result + "(sessionIntercepteor.java)");
        	
        	if(result.equals("")){
        		return invocation.invoke();		// 정상적인 진행 
        	}else{
        		return result;
        	}
        }
        
        @Override
        public void init() {
         // TODO Auto-generated method stub
         super.init();
        }
        
        @Override
        public void destroy() {
         // TODO Auto-generated method stub
         super.destroy();
        }
}

