package dto;

import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;

@Data
public class HireDTO {
	private int seq;									//	HIRE 시퀀스
	private String title;								//	글제목
	private String brand_name;					//	업체명
	private String eupmyeondong_code;		//	읍면동(법정동) 코드
	private String address_road;					//	도로명 주소
	private String address_jibun;					//	지번 주소
	private String sido_name;						//	법정동주소테이블 시도명
	private String sigungu_name;					//	법정동주소테이블 시군구명
	private String file_name;						//	첨부파일 실제 파일명
	private String file_path;						//	첨부파일 저장 경로 + 파일명	
	private String gender;							//	성별
	private String age_start;						//	최저연령
	private String age_end;						//	최고연령
	private String wage_kind;						//	급여종류
	private int wage;								//	급여
	private String reg_date;						//	작성일
	private String is_close;							//	채용마감여부(0-false, 1-true)
	private String reg_diff;							//	작성일 차이시간
}
