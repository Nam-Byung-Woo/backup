package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import util.system.StringUtil;

import dto.HireDTO;

@Repository
public class HireDAOImp implements HireDAO {

	private HireDTO hireDTO; 
	private NamedParameterJdbcTemplate jdbcTemplate;
	private String table_name = " HIRE  ";
	
	@Autowired
	public void setDataSource(DataSource dataSource){ 
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public HireDAOImp(){
	
	}
	
	//	HIRE LIST
	public Object getHireList(Map<String, Object> paramMap) {
		Map<String,Object> sqlMap = new HashMap<String,Object>();
		List<HireDTO> list = new ArrayList<HireDTO>();
		boolean isCount = paramMap.containsKey("isCount") ? (boolean)paramMap.get("isCount") : false;
		String searchCol = paramMap.containsKey("searchCol") ? (String)paramMap.get("searchCol") : "";
		String searchVal = paramMap.containsKey("searchVal") ? (String)paramMap.get("searchVal") : "";
		String code_type = paramMap.containsKey("code_type") ? (String)paramMap.get("code_type") : "";
		String eupmyeondong_code = paramMap.containsKey("eupmyeondong_code") ? (String)paramMap.get("eupmyeondong_code") : "";
		String listStartSeq = paramMap.containsKey("listStartSeq") ? (String)paramMap.get("listStartSeq") : "";
		int pageNum = paramMap.containsKey("pageNum") ? (int)paramMap.get("pageNum") : 0;
		int countPerPage = paramMap.containsKey("countPerPage") ? (int)paramMap.get("countPerPage") : 0;
		int startNum = (pageNum-1)*countPerPage;
		
		String sql = "";
		
		searchCol = StringUtil.isNullOrSpace(searchCol,"").trim();
		searchVal = StringUtil.isNullOrSpace(searchVal,"").trim();
		code_type = StringUtil.isNullOrSpace(code_type,"").trim();
		eupmyeondong_code = StringUtil.isNullOrSpace(eupmyeondong_code,"").trim();
		
		sqlMap.put("one", 1);
		sqlMap.put("searchVal", searchVal);
		sqlMap.put("startNum", startNum);
		sqlMap.put("countPerPage", countPerPage);
		
		if(isCount){
			sql += "	SELECT COUNT(*)	\n";
		}else{
			sql += "	SELECT TITLE, BRAND_NAME, EUPMYEONDONG_CODE, ADDRESS_ROAD, ADDRESS_JIBUN, FILE_NAME, FILE_PATH, GENDER, WAGE_KIND, WAGE, DATE_FORMAT(REG_DATE, '%Y-%m-%d') AS REG_DATE, IS_CLOSE, 	\n";
			sql += "	CASE \n";
			sql += "	WHEN TIMESTAMPDIFF( HOUR , REG_DATE, NOW( ) ) < 24 AND TIMESTAMPDIFF( HOUR , REG_DATE, NOW( ) ) > 0 THEN CONCAT( TIMESTAMPDIFF( HOUR , REG_DATE, NOW( ) ) ,  '시간 전' )";
			sql += "  WHEN TIMESTAMPDIFF( HOUR , REG_DATE, NOW( ) ) < 1 AND TIMESTAMPDIFF( MINUTE , REG_DATE, NOW( ) ) > 0 THEN CONCAT( TIMESTAMPDIFF( MINUTE , REG_DATE, NOW( ) ) ,  '분 전' ) \n";
			sql += "  WHEN TIMESTAMPDIFF( MINUTE , REG_DATE, NOW( ) ) < 1 AND TIMESTAMPDIFF( SECOND , REG_DATE, NOW( ) ) > 0 THEN CONCAT( TIMESTAMPDIFF( SECOND , REG_DATE, NOW( ) ) ,  '초 전' ) \n";
			sql += "	ELSE DATE_FORMAT(REG_DATE, '%Y-%m-%d')	\n";
			sql += "	END AS REG_DIFF,	\n";
			sql += "	(SELECT SIDO_NAME FROM ADDRESS_CODE WHERE EUPMYEONDONG_CODE = T.EUPMYEONDONG_CODE) AS SIDO_NAME,		\n";
			sql += "	(SELECT SIGUNGU_NAME FROM ADDRESS_CODE WHERE EUPMYEONDONG_CODE = T.EUPMYEONDONG_CODE) AS SIGUNGU_NAME,		\n";
			sql += "	SEQ	\n";
		}
        sql += " FROM "+ table_name + " T WHERE :one = :one \n";
        if(!searchCol.equals("")){
	        sql += " and " + searchCol + " = :searchVal		\n";
        }
        
        if(!code_type.equals("") && !eupmyeondong_code.equals("0")){
        	
        	switch(code_type){
        		case "sido" :
        			sqlMap.put("eupmyeondong_code", eupmyeondong_code.substring(0, 2) + "%");
        			break;
        		case "sigungu" :
        			sqlMap.put("eupmyeondong_code", eupmyeondong_code.substring(0, 5) + "%");
        			break;
        		case "eupmyeondong" :
        			sqlMap.put("eupmyeondong_code", eupmyeondong_code.substring(0, 8) + "%");
        			break;
        		default :
        			break;
        	}
        	
        	System.out.println("eupmyeondong_code::::" + sqlMap.get("eupmyeondong_code"));
	        sql += " and eupmyeondong_code like :eupmyeondong_code		\n";
        }
        
        if(isCount){
		}else{
			if(code_type.equals("")){
				sql += " ORDER BY seq DESC		\n";
				sql += " LIMIT :startNum, :countPerPage	\n";
			}else{
				
			}
		}
        
        System.out.println("sql:::"+sql);
        
        if(isCount){
        	return this.jdbcTemplate.queryForInt(sql,sqlMap);
		}else{
			list  = this.jdbcTemplate.query(sql, sqlMap, new BeanPropertyRowMapper(HireDTO.class));
	        return list;
		}
	}
	
	//	Hire ROW
	public HireDTO getHireRow(Map<String, Object> paramMap) {		
		Map<String,Object> sqlMap = new HashMap<String,Object>();
		List<HireDTO> list = new ArrayList<HireDTO>();
		String searchCol = paramMap.containsKey("searchCol") ? (String)paramMap.get("searchCol") : "";
		String searchVal = paramMap.containsKey("searchVal") ? (String)paramMap.get("searchVal") : "";
		String sql = "";
		
		searchCol = StringUtil.isNullOrSpace(searchCol,"").trim();
		searchVal = StringUtil.isNullOrSpace(searchVal,"").trim();
		
		sqlMap.put("one", 1);
		sqlMap.put("searchVal", searchVal);
		
        sql = "	SELECT * FROM "+ table_name + " WHERE :one = :one	\n";
        if(!searchCol.equals("")){
	        sql += "	AND " + searchCol + " = :searchVal	\n";
        }
        
        list  = this.jdbcTemplate.query(sql,sqlMap,new BeanPropertyRowMapper(HireDTO.class));
        this.hireDTO = (list.size() == 1) ? list.get(0) : null;
        return this.hireDTO;
	}
	
	//	HIRE Write
	public int wirteHire(HireDTO dto) {
		System.out.println("db insert 함수시작");
//		int next_seq = getMaxSeq();
//		if(next_seq == 0){
//			next_seq = 1;
//		}
		String sql = "";
		sql += "	INSERT INTO " + table_name + "	\n";
		sql += "	(title, brand_name, eupmyeondong_code, address_road, address_jibun, file_name, gender, age_start, age_end, wage_kind, wage)	\n";
		sql += "	values(:title, :brand_name, :eupmyeondong_code, :address_road, :address_jibun, :file_name, :gender, :age_start, :age_end, :wage_kind, :wage)	\n";

//		SqlLobValue lobValue = new SqlLobValue(dto.getBbs_content(), lobHandler);

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("title", dto.getTitle(), Types.VARCHAR);
		paramSource.addValue("brand_name", dto.getBrand_name(), Types.VARCHAR);
		paramSource.addValue("eupmyeondong_code", dto.getEupmyeondong_code(), Types.VARCHAR);
		paramSource.addValue("address_road", dto.getAddress_road(), Types.VARCHAR);
		paramSource.addValue("address_jibun", dto.getAddress_jibun(), Types.VARCHAR);
		paramSource.addValue("file_name", dto.getFile_name(), Types.VARCHAR);
		paramSource.addValue("gender", dto.getGender(), Types.VARCHAR);
		paramSource.addValue("age_start", dto.getAge_start(), Types.VARCHAR);
		paramSource.addValue("age_end", dto.getAge_end(), Types.VARCHAR);
		paramSource.addValue("wage_kind", dto.getWage_kind(), Types.VARCHAR);
		paramSource.addValue("wage", dto.getWage(), Types.INTEGER);
		
		int rtnInt = 0;
		
		try{
			rtnInt = this.jdbcTemplate.update(sql, paramSource);
		}catch(Exception e){
			System.out.println("error::::"+e);
		}
		
		if(rtnInt > 0){
			return rtnInt;
		}else{
			return 0;
		}
	}	
}
