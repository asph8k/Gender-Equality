package kr.ge.kwdi.gep.platform.domain.main.dto;

import lombok.Data;

@Data
public class MainNewsConverageVO {
	@Data
	public static class Result {
		private Integer gndrEqltDataNo; // 자료번호
		private String dataTtl; // 자료제목
		private String dataCn; // 자료내용
		private String atchFileYn; // 첨부파일 여부
		private Integer atchFileNo; // 첨부파일 갯수
	}
}
