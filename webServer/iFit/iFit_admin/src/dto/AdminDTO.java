package dto;

import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;

@Data
public class AdminDTO {
	private int seq;					//	관리자 시퀀스
	private String id;				//	관리자 아이디
	private String password;		//	관리자 비밀번호
	private String name;			//	관리자 이름
//	private String admin_email;			//	관리자 이메일
//	private String admin_regdate;		//	관리자 등록일
//	private String admin_moddate;		//	관리자 정보수정일
//	private String admin_cntlogin;		//	관리자 로그인횟수
//	private String admin_lastlogin;		//	관리자 마지막 로그인
}
