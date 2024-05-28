package kr.ge.kwdi.gep.admin.domain.faq.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "Faq DTO")
@Data
public class FaqDTO {

	// Request DTO
	@ApiModel(value = "FaqDTO.SearchRequest")
	@Getter
	@NoArgsConstructor
	@Data
	public static class SearchRequest {
		@ApiModelProperty(value = "검색어")
		private String q;
		@ApiModelProperty(value = "검색 조건(all : 전체, question : 제목, answer : 내용)")
		private String searchType;
	}

	@ApiModel(value = "FaqDTO.InsertRequest")
	@Getter
	@NoArgsConstructor
	@Data
	public static class InsertRequest {
		@ApiModelProperty(value = "질문")
		private String question;
		@ApiModelProperty(value = "답변")
		private String answer;
		@ApiModelProperty(value = "상태")
		private Boolean visible;
	}

	@ApiModel(value = "FaqDTO.UpdateRequest")
	@Getter
	@NoArgsConstructor
	@Data
	public static class UpdateRequest {
		@ApiModelProperty(value = "질문")
		private String question;
		@ApiModelProperty(value = "답변")
		private String answer;
		@ApiModelProperty(value = "상태")
		private Boolean visible;
	}

	@ApiModel(value = "FaqDTO.deleteList")
	@Getter
	@NoArgsConstructor
	@Data
	public static class deleteList {
		@ApiModelProperty(value = "선택 삭제할 FAQ 번호 pk (List<Long>)")
		private List<Long> deleteList;
	}



	// Response DTO
	@ApiModel(value = "FaqDTO.ListResponse")
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class ListResponse {
		@ApiModelProperty(value = "Faq 번호")
		private Long id;
		@ApiModelProperty(value = "질문")
		private String question;
		@ApiModelProperty(value = "답변")
		private String answer;
		@ApiModelProperty(value = "최초작업객체")
		private String frstJobObj;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		@ApiModelProperty(value = "등록일")
		private LocalDateTime frstJobDt;
		@ApiModelProperty(value = "상태")
		private String visible;
	}

	@ApiModel(value = "FaqDTO.DetailResponse")
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class DetailResponse {
		@ApiModelProperty(value = "Faq 번호")
		private Long id;
		@ApiModelProperty(value = "질문")
		private String question;
		@ApiModelProperty(value = "답변")
		private String answer;
		@ApiModelProperty(value = "상태")
		private boolean visible;
	}
}
