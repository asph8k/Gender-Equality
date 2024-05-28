package kr.ge.kwdi.gep.platform.domain.main.dto;

import lombok.Data;
@Data
public class MainNewsDTO {
	@Data
	public static class Response {
		private Integer gndrEqltDataNo;
		private String nwsImgUrlAddr;
		private String dataTtl;
		private String orglAddr;
	}
}
