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

import dto.AddressCodeDTO;

@Repository
public class AddressCodeDAOImp implements AddressCodeDAO {

	private AddressCodeDTO addressCodeDTO; 
	private NamedParameterJdbcTemplate jdbcTemplate;
	private String table_name = " ADDRESS_CODE  ";
	
	@Autowired
	public void setDataSource(DataSource dataSource){ 
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public AddressCodeDAOImp(){
	
	}
	
	//	HIRE LIST
	public List<AddressCodeDTO> getAddressCodeList(Map<String, Object> paramMap) {		
		Map<String,Object> sqlMap = new HashMap<String,Object>();
		List<AddressCodeDTO> list = new ArrayList<AddressCodeDTO>();
		String mode = paramMap.containsKey("mode") ? (String)paramMap.get("mode") : "";
		String sido_selected = paramMap.containsKey("sido_selected") ? (String)paramMap.get("sido_selected") : "";
		String sigungu_selected = paramMap.containsKey("sigungu_selected") ? (String)paramMap.get("sigungu_selected") : "";
		mode = StringUtil.isNullOrSpace(mode,"").trim();
		sido_selected = StringUtil.isNullOrSpace(sido_selected,"").trim();
		sigungu_selected = StringUtil.isNullOrSpace(sigungu_selected,"").trim();
		
		String sql = "";
		
		sqlMap.put("one", 1);
		
        sql = " SELECT seq, eupmyeondong_code, sido_name, sigungu_name, eupmyeondong_name, dongri_name, reg_date, del_date FROM "+ table_name + " WHERE :one = :one \n";
        
        if(mode.equals("sido")){
	        sql += " and ( sigungu_name <> '' or eupmyeondong_name <> '' )		\n";
	        sql += " group by sido_name	\n";
	        sql += " ORDER BY seq ASC		\n";
        }else if(mode.equals("sigungu")){
        	sql += " and sigungu_name <> ''		\n";
        	sql += " and eupmyeondong_name <> ''		\n";
        	sql += " and sido_name = :sido_selected		\n";
	        sql += " group by sigungu_name	\n";
	        sql += " ORDER BY sigungu_name ASC		\n";
	        sqlMap.put("sido_selected", sido_selected);
        }else if(mode.equals("eupmyeondong")){
        	sql += " and eupmyeondong_name <> ''		\n";
        	sql += " and sido_name = :sido_selected		\n";
        	sql += " and sigungu_name = :sigungu_selected		\n";
	        sql += " group by eupmyeondong_name	\n";
	        sql += " ORDER BY eupmyeondong_name ASC		\n";
	        sqlMap.put("sido_selected", sido_selected);
	        sqlMap.put("sigungu_selected", sigungu_selected);
        }
        
        System.out.println("sql::   "+sql);
        list  = this.jdbcTemplate.query(sql, sqlMap, new BeanPropertyRowMapper(AddressCodeDTO.class));
        System.out.println("?????????????????????");
        return list;
	}
	
	public AddressCodeDTO getAddressCodeRow(Map<String, Object> paramMap) {		
		Map<String,Object> sqlMap = new HashMap<String,Object>();
		List<AddressCodeDTO> list = new ArrayList<AddressCodeDTO>();
		String searchCol = paramMap.containsKey("searchCol") ? (String)paramMap.get("searchCol") : "";
		String searchVal = paramMap.containsKey("searchVal") ? (String)paramMap.get("searchVal") : "";
		String sql = "";
		
		searchCol = StringUtil.isNullOrSpace(searchCol,"").trim();
		searchVal = StringUtil.isNullOrSpace(searchVal,"").trim();
		
		sqlMap.put("one", 1);
		sqlMap.put("searchVal", searchVal);
		
        sql = " SELECT seq, eupmyeondong_code, sido_name, sigungu_name, eupmyeondong_name, dongri_name, reg_date, del_date FROM "+ table_name + " WHERE :one = :one \n";
        if(!searchCol.equals("")){
	        sql += "	AND " + searchCol + " = :searchVal	\n";
        }
        
        list  = this.jdbcTemplate.query(sql, sqlMap, new BeanPropertyRowMapper(AddressCodeDTO.class));
        this.addressCodeDTO = (list.size() == 1) ? list.get(0) : null;
        return this.addressCodeDTO;
	}
}
