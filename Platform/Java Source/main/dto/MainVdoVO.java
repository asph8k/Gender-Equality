package kr.ge.kwdi.gep.platform.domain.main.dto;

import lombok.Data;

@Data
public class MainVdoVO {
	@Data
	public static class Param {
		private String dataTypeCd;
	}

	@Data
	public static class Result {
		private Integer gndrEqltDataNo;
		private String dataTtl;
		private String pblcnInstNm;
		private String imgUrlAddr;
	}
}
