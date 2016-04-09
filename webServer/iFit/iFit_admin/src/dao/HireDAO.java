package dao;

import java.util.List;
import java.util.Map;

import dto.HireDTO;


public interface HireDAO {
	public Object getHireList(Map<String, Object> paramMap);		//	HireList
	public HireDTO getHireRow(Map<String, Object> paramMap);
	public int wirteHire(HireDTO hireDto);										//	Write hire
}
