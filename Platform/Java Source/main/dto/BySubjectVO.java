package kr.ge.kwdi.gep.platform.domain.main.dto;

import lombok.Data;

@Data
public class BySubjectVO {
	@Data
	public static class Param {
		private String dataClsfSystCd;
	}

	@Data
	public static class Result {
		private Integer gndrEqltDataNo;
		private String dataTtl;
		private String dataPblsDt;
		private String dataTypeCd;
		private String intrInstCd;
		private String pblcnInstNm;
		private String linkYn;
		private String orgnAddr;
	}
}
