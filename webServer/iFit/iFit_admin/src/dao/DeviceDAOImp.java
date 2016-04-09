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

import dto.DeviceDTO;

@Repository
public class DeviceDAOImp implements DeviceDAO {

	private DeviceDTO deviceDTO; 
	private NamedParameterJdbcTemplate jdbcTemplate;
	private String table_name = " DEVICE  ";
	
	@Autowired
	public void setDataSource(DataSource dataSource){ 
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public DeviceDAOImp(){
	
	}
	
	//	DEVICE LIST
	public List<DeviceDTO> selectDeviceList(Map<String, Object> paramMap) {	
		System.out.println("DeviceDAOImp.java | selectDeviceList 함수시작");
		Map<String,Object> sqlMap = new HashMap<String,Object>();
		List<DeviceDTO> list = new ArrayList<DeviceDTO>();
		String searchCol = paramMap.containsKey("searchCol") ? (String)paramMap.get("searchCol") : "";
		String searchVal = paramMap.containsKey("searchVal") ? (String)paramMap.get("searchVal") : "";
		String sql = "";
		
		searchCol = StringUtil.isNullOrSpace(searchCol,"").trim();
		searchVal = StringUtil.isNullOrSpace(searchVal,"").trim();
		
		sqlMap.put("one", 1);
		sqlMap.put("searchVal", searchVal);
		
        sql = " SELECT seq, gcm_id FROM "+ table_name + " WHERE :one = :one \n";
        if(!searchCol.equals("")){
	        sql += " and " + searchCol + " = :searchVal		\n";
        }
        System.out.println("?????????????????????");
        list  = this.jdbcTemplate.query(sql, sqlMap, new BeanPropertyRowMapper(DeviceDTO.class));
        System.out.println("?????????????????????");
        return list;
	}
	
	//	DEVICE LIST ONE
	public DeviceDTO getDeviceOne(Map<String, Object> paramMap){
		System.out.println("DeviceDAOImp.java | getDeviceOne 함수시작");
		Map<String,Object> sqlMap = new HashMap<String,Object>();
		List<DeviceDTO> list = new ArrayList<DeviceDTO>();
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
        
        list  = this.jdbcTemplate.query(sql,sqlMap,new BeanPropertyRowMapper(DeviceDTO.class));
        this.deviceDTO = (list.size() == 1) ? list.get(0) : null;
        return this.deviceDTO;
	}
	
	//	DEVICE INSERT
	public int insertDevice(DeviceDTO dto) {
		System.out.println("DeviceDAOImp.java | 함수시작");
//		int next_seq = getMaxSeq();
//		if(next_seq == 0){
//			next_seq = 1;
//		}
		String sql = "";
		sql += "	INSERT INTO " + table_name + "	\n";
		sql += "	(gcm_id)	\n";
		sql += "	values(:gcm_id)	\n";

//		SqlLobValue lobValue = new SqlLobValue(dto.getBbs_content(), lobHandler);

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("gcm_id", dto.getGcm_id(), Types.VARCHAR);
		int rtnInt = this.jdbcTemplate.update(sql, paramSource);
		if(rtnInt > 0){
			return rtnInt;
		}else{
			return 0;
		}
	}	
	
	//	DEVICE DELETE
	public int deleteDevice(DeviceDTO dto) {
		System.out.println("DeviceDAOImp.java | 함수시작");
//		int next_seq = getMaxSeq();
//		if(next_seq == 0){
//			next_seq = 1;
//		}
		String sql = "";
		sql += "	DELETE FROM " + table_name + "	\n";
		sql += "	WHERE seq = :seq	\n";

//		SqlLobValue lobValue = new SqlLobValue(dto.getBbs_content(), lobHandler);

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("seq", dto.getSeq(), Types.NUMERIC);
		int rtnInt = this.jdbcTemplate.update(sql, paramSource);
		if(rtnInt > 0){
			return rtnInt;
		}else{
			return 0;
		}
	}
}
