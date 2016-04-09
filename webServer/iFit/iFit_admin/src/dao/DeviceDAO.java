package dao;

import java.util.List;
import java.util.Map;

import dto.DeviceDTO;


public interface DeviceDAO {
	public DeviceDTO getDeviceOne(Map<String, Object> paramMap);				//	select Device
	public List<DeviceDTO> selectDeviceList(Map<String, Object> paramMap);		//	DeviceList
	public int insertDevice(DeviceDTO deviceDto);											//	Device Insert
	public int deleteDevice(DeviceDTO deviceDto);											//	Device Delete
}
