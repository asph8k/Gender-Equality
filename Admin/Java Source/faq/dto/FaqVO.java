package kr.ge.kwdi.gep.admin.domain.faq.dto;

import kr.ge.kwdi.gep.admin.global.util.crypto.CryptoTarget;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FaqVO {
	// Param VO
	@Data
	@Builder
	public static class SearchParam {
		private String q; //검색어
		private String searchType; //검색 조건(all : 전체, question : 제목, answer : 내용)
	}

	@Data
	@Builder
	public static class InsertParam {
		private String question; //faq 제목
		private String answer; //faq 내용
		private Long ordr; //faq 순서
		@CryptoTarget
		private String frstJobObj; //최초작업객체
		private String lastJobObj; //최종작업객체
		private String visible; //게시 상태 코드
	}

	@Data
	@Builder
	public static class UpdateParam {
		private Long entityId; //faq 번호
		private String question; //faq 제목
		private String answer; //faq 내용
		private String visible; //게시 상태 코드
		@CryptoTarget
		private String lastJobObj; //최종작업객체
	}

	@Data
	@Builder
	public static class DeleteParam {
		private List<Long> list; //faq 번호
	}



	// Result VO
	@Data
	public static class ListResult {
		private Long id; //faq 번호
		private String question; //faq 제목
		private String answer; //faq 내용
		private String frstJobObj; //최초작업객체
		private LocalDateTime frstJobDt; //최초작업일시
		private String visible; //게시 상태 코드
	}

	@Data
	public static class DetailResult {
		private Long id; //faq 번호
		private String question; //faq 제목
		private String answer; //faq 내용
		private Long ordr; //faq 순서
		private String visible; //게시 상태 코드
	}

	@Data
	public static class OrdrCountResult {
		private Long ordrCount; //순서 증가값
	}
}
