package util.config;

import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;

@Data
public class AlertMessage{
	public AlertMessage(){
	}
	//*******************기본알림*******************/
	private String InputError = "입력값이 올바르지 않습니다.";				//	입력값오류
	private String fileUploadError = "파일 첨부가 실패 하였습니다.";	
	
	//*******************로그인관련*******************/
	private String LoginError = "입력하신 내용과 일치하는 정보를 찾을 수 없습니다.\\n아이디와 비밀번호를 확인해주시기 바랍니다.";
//    private String okMSG = "처리 되었습니다.";
//    private String failMSG = "실패하였습니다.";
//    private String expireMSG = "만료된 페이지 입니다. 다시 등록하여 주십시요.";
//    private String retryMSG = "잠시후 다시 시도 하여 주십시요.";
//    private String timeoutMSG = "수정 제한시간이 초과 되었습니다.\\n관리자에게 요청하여 주십시요.";
//    private String findPasswordSuccess = "가입시 입력하신 메일 주소로 변경된 임시번호가 발송되었습니다.\\n\\n대단히 감사합니다.";
//    private String findPasswordFail = "임시 비밀번호 발송이 실패되었습니다.\\n\\n시스템 관리자 (admin@kipex.or.kr 또는 02-570-5224)에게 문의 하시기 바랍니다.";
//    private String joinErrorMSG = "이미 회원가입 되었거나 잘못된 요청 입니다.";
//    private String falseUrlMSG = "잘못된 접근입니다.";
	
	//*******************회원가입관련*******************/
	private String joinClause1 = "";
	private String joinClause2 = "";
	private String checkClauseFalse = "약관 동의를 체크 하지 않았습니다.";
	private String checkMemIdValidation = "아이디는 첫글자가 영문이어야 하며 5~15자리의 영문 대 · 소문자 또는 숫자만 가능합니다.";
	private String checkMemIdDuplication = "이미 등록되어 있는 아이디 입니다.";
	private String checkMemPasswordValidation = "비밀번호는 6~15자리의 영문 대 · 소문자와 숫자의 조합만 가능합니다.";
	private String checkMemPasswordReValidation = "비밀번호가 일치하지 않습니다.";
	private String checkMemNameValidation = "올바른 이름을 입력해 주세요.";
	private String checkMemBirthValidation = "올바른 생년월일을 입력해 주세요.";
	private String checkMemGenderValidation = "올바른 성별을 선택해 주세요.";
	private String checkMemEmailValidation = "올바른 이메일주소를 입력해 주세요.";
	private String checkMemMobileValidation = "올바른 휴대폰번호를 입력해 주세요.";
	private String checkMemAddressValidation = "올바른 주소를 입력해 주세요.";
	private String checkJoinInputValidationSuccess = "작성완료";
}