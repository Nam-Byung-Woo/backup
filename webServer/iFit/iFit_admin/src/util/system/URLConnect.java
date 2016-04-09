package util.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.*;
import java.util.*;

public class URLConnect {
	
	public URLConnect(){}
	
	//	요청한 URL의 값을 string으로 가져온다
	public static String start(String urlStr) throws Exception{
		URL url= new URL(urlStr);
		BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		StringBuffer stringBuffer = new StringBuffer();
		String tempStr= null;
		while(true){
			tempStr= bufferedReader.readLine();
			if(tempStr== null){
				break;
			}
			stringBuffer.append(tempStr);
		}
		bufferedReader.close();
		
		return stringBuffer.toString();
	}
	
	//	xml의 선언자 제외한 값추출
	public static String xmlDeclareRemove(String xmlStr){
		
		return "";
	}
}
