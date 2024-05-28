package kr.ge.kwdi.gep.platform.domain.faq.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "Faq DTO")
public class FaqDTO {

	@ApiModel(value = "FaqDTO.Request")
	@Getter
	@NoArgsConstructor
	public static class Request {
		@ApiModelProperty(value = "번호")
		private Long faqNo;
		@ApiModelProperty(value = "제목")
		private String faqTitle;
		@ApiModelProperty(value = "내용")
		private String faqContent;
		@ApiModelProperty(value = "순서")
		private Long faqOrder;
	}

	@ApiModel(value = "Faq.Response")
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		@ApiModelProperty(value = "번호")
		private Long faqNo;
		@ApiModelProperty(value = "제목")
		private String faqTitle;
		@ApiModelProperty(value = "내용")
		private String faqContent;
		@ApiModelProperty(value = "순서")
		private Long faqOrder;
	}

}
