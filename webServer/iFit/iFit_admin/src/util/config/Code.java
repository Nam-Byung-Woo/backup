package util.config;

import java.util.LinkedHashMap;
import java.util.Calendar;

import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;

@Data
public class Code{

	public static Calendar calendar = Calendar.getInstance();
	private String mainTitle = "주식회사 도움";		//	홈페이지 메인 타이틀
	public static int year = calendar.get(Calendar.YEAR);
    public static int countPerPage = 10;			//	한화면에 보여줄 리스트 개수
    public static int countPerPage_App = 20;		//	한화면에 보여줄 리스트 개수(앱전용)
    public static int pagingGroupNum = 4;		//	중앙기준 앞뒤로 보여질 페이지번호 수

  //첨부 가능한 파일 확장자 모음
    private String[][] attachFileExt = {
    	{"txt","txt"},		
    	{"jpeg","jpeg"},
    	{"jpg","jpg"},
    	{"gif","gif"},
    	{"bmp","bmp"},
    	{"png","png"},
    	{"xls","xls"},
    	{"xlsx","xlsx"},
    	{"doc","doc"},
    	{"docx","docx"},
    	{"ppt","ppt"},
    	{"pptx","pptx"},
    	{"pdf","pdf"},
    	{"hwp","hwp"},
    };	
    
    private LinkedHashMap genderMap = new LinkedHashMap(){  	//	성별
	    {
	    	put("all","성별무관");
	    	put("male","남성");			
	    	put("female","여성"); 			
	    }
    };
    
    public int minAge = 18;	//	연령선택 최소값
    public int maxAge = 51;	//	연령선택 최대값
    
    public int minWage = 5580; //	최저임금(2015.01.05)
    
    private LinkedHashMap wageMap = new LinkedHashMap(){  	//	성별
	    {
	    	put("k01","시급");			
	    	put("k02","일급");
	    	put("k03","주급");
	    	put("k04","월급");
	    	put("k05","연봉");
	    	put("k06","건별");
	    }
    };
//    
//    private LinkedHashMap mobileNumberMap = new LinkedHashMap() {  	//	핸드폰 앞자리
//    {
//    		put("010","010"); 				
//    		put("011","011");
//    		put("016","016");
//    		put("017","017");
//    		put("018","018");
//    		put("019","019");
//    }};
//    
//    private LinkedHashMap emailHostMap = new LinkedHashMap() {  	//	주요이메일주소목록
//    {
//    		put("naver.com","네이버"); 				
//    		put("hanmail.net","한메일");
//    		put("nate.com","네이트");
//    		put("gmail.com","지메일");
//    		put("empal.com","엠팔");
//    		put("hotmail.com","아웃룩");
//    		put("chol.com","천리안");
//    }};
//    
//    //page list 개수
//    private String[][] pagingSelectListArr = {
//    		{"10","10"}
//    		,{"15","15"}
//    		,{"20","20"}
//    		,{"30","30"}
//    		,{"40","40"}
//    		,{"50","50"}
//    };
//    
//    //page list 개수2
//    private String[][] pagingSelectListArr2 = {
//    		{"5","5"}
//    		,{"10","10"}
//    		,{"15","15"}
//    		,{"20","20"}
//    		,{"30","30"}
//    		,{"40","40"}
//    		,{"50","50"}
//    };
//    
//    private String pathFlag = System.getProperty("file.separator"); // 리눅스, 윈도우 파일경로 차이 해결 ("\" or "/")
}