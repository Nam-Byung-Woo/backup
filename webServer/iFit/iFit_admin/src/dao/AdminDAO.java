package dao;

import java.util.List;
import java.util.Map;

import dto.AdminDTO;


public interface AdminDAO {
	public AdminDTO getAdminRow(Map<String, Object> paramMap);		//	조건에 맞는 관리자목록
}
