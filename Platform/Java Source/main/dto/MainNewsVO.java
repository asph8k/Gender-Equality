package kr.ge.kwdi.gep.platform.domain.main.dto;

import lombok.Data;

@Data
public class MainNewsVO {
	@Data
	public static class Result {
		private Integer gndrEqltDataNo;
		private String nwsImgUrlAddr;
		private String dataTtl;
		private String orglAddr;
	}
}
