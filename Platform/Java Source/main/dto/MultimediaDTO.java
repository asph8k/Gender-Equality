package kr.ge.kwdi.gep.platform.domain.main.dto;

import lombok.Data;

@Data
public class MultimediaDTO {
	@Data
	public static class Response {
		private Integer gndrEqltDataNo;
		private String dataTtl;
		private String pblcnInstNm;
		private String imgUrlAddr;
	}
}
