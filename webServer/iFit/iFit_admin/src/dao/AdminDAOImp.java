package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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

import dto.AdminDTO;

@Repository
public class AdminDAOImp implements AdminDAO {

	private AdminDTO adminDTO; 
	private NamedParameterJdbcTemplate jdbcTemplate;
	private String table_name = " ADMIN  ";
	
	@Autowired
	public void setDataSource(DataSource dataSource){ 
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public AdminDAOImp(){
	
	}
	
	//	조건에 맞는 관리자목록
	public AdminDTO getAdminRow(Map<String, Object> paramMap) {	
		Map<String,Object> sqlMap = new HashMap<String,Object>();
		List<AdminDTO> list = new ArrayList<AdminDTO>();
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
        
        list  = this.jdbcTemplate.query(sql,sqlMap,new BeanPropertyRowMapper(AdminDTO.class));
        this.adminDTO = (list.size() == 1) ? list.get(0) : null;
        return this.adminDTO;
	}
}
