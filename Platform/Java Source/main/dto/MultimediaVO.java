package kr.ge.kwdi.gep.platform.domain.main.dto;

import lombok.Data;
@Data
public class MultimediaVO {
	@Data
	public static class Result {
		private Integer gndrEqltDataNo;
		private String dataTtl;
		private String pblcnInstNm;
		private String imgUrlAddr;
	}
}
