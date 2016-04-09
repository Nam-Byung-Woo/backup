package dao;

import java.util.List;
import java.util.Map;

import dto.AddressCodeDTO;

public interface AddressCodeDAO {
	public List<AddressCodeDTO> getAddressCodeList(Map<String, Object> paramMap);		//	AddressCode List
	public AddressCodeDTO getAddressCodeRow(Map<String, Object> paramMap);			//	AddressCode Row
}
