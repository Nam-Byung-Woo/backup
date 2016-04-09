package util.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;

@Data
public class DefaultSetting{
	public DefaultSetting(){};
	
	public Map<String, Object> init(){
		Code code = new Code();
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("mainTitle", code.getMainTitle());	//	메인타이틀
		rtnMap.put("alertContent","");						//	alert에 들어갈 메세지
		rtnMap.put("proc","");								//	action_ok 사용할 경우 입력값 체크 성공/실패 여부(주로 input입력 받을 경우 사용)
		rtnMap.put("rtnURL","");							//	이동할 페이지	
		rtnMap.put("pageTitle","");							//	페이지 제목
		rtnMap.put("pageSubTitle","");						//	페이지 부제목
		return rtnMap;
	}
}