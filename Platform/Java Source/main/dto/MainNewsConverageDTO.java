package kr.ge.kwdi.gep.platform.domain.main.dto;

import lombok.Data;

@Data
public class MainNewsConverageDTO {
	@Data
	public static class Response {
		private Integer gndrEqltDataNo; //보도자료 번호
		private String dataTtl; // 자료 제목
		private String dataCn; // 자료 내용
		private String atchFileYn; // 첨부파일 여부
		private Integer atchFileNo; // 첨부파일 갯수
	}
}
