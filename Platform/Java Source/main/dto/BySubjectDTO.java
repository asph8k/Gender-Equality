package kr.ge.kwdi.gep.platform.domain.main.dto;

import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class BySubjectDTO {
	@ApiOperation(value = "BySubjectDTO.Request")
	@Data
	public static class Request {
		@ApiModelProperty(value = "주제 코드")
		private String dataClsfSystCd;
	}

	@Data
	public static class Response {
		@ApiModelProperty(value = "id")
		private Integer gndrEqltDataNo;
		@ApiModelProperty(value = "제목")
		private String dataTtl;
		@ApiModelProperty(value = "날짜")
		private String dataPblsDt;
		@ApiModelProperty(value = "자료 유형")
		private String dataTypeCd;
		@ApiModelProperty(value = "인터페이스 유형 코드")
		private String intrInstCd;
		@ApiModelProperty(value = "발행기관")
		private String pblcnInstNm;
		@ApiModelProperty(value = "원문 링크 이동 여부")
		private String linkYn;
		@ApiModelProperty(value = "원문 링크")
		private String orgnAddr;
		@ApiModelProperty(value = "상세 링크")
		private String url;
	}

	@ApiOperation(value = "BySubjectDTO.SubjectsResponse")
	@Getter
	@Builder
	public static class SubjectsResponse {
		@ApiModelProperty(value = "주제")
		private String subject;
		@ApiModelProperty(value = "공통코드")
		private String code;
	}
}
