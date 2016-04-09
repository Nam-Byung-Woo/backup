package dto;

import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;

@Data
public class DeviceDTO {
	private int seq;						//	DEVICE 시퀀스
	private String gcm_id;				//	gcm_id
}
