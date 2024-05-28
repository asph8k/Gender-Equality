package kr.ge.kwdi.gep.platform.domain.faq.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FaqVO {

	private Long faqNo;
	private String faqTtl;
	private String faqCn;
	private Long faqOrdr;
	private String frstJobObj;
	private LocalDateTime frstJobDt;
	private String lastJobObj;
	private LocalDateTime lastJobDt;

	@Getter
	@Builder
	public static class Param {
		private Long faqNo;
	}

	@Getter
	public static class Result {
		private Long faqNo;
		private String faqTtl;
		private String faqCn;
		private Long faqOrdr;
	}

}
