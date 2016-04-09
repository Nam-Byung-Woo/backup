package dto;

import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;

@Data
public class AddressCodeDTO {
	private int seq;								//	HIRE 시퀀스
	private String eupmyeondong_code;	//	법정동(읍면동)코드
	private String sido_name;					//	(광역)시도명
	private String sigungu_name;				//	시군구명
	private String eupmyeondong_name;	//	법정동(읍면동)명
	private String dongri_name;				//	동리명
	private String reg_date;					//	기관생성일
	private String del_date;					//	기관말소일
}
