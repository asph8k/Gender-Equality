package kr.ge.kwdi.gep.admin.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "NoticeList DTO")
@Data
public class NoticeListDTO {

	// Request DTO
	@ApiModel(value = "NoticeListDTO.SearchRequest")
	@Getter
	@NoArgsConstructor
	@Data
	public static class SearchRequest {
		@ApiModelProperty(value = "검색어")
		private String q;
		@ApiModelProperty(value = "검색 조건(title)")
		private String searchType;
	}

	@ApiModel(value = "NoticeListDTO.InsertRequest")
	@Getter
	@NoArgsConstructor
	@Data
	public static class InsertRequest {
		@ApiModelProperty(value = "제목")
		private String title;
		@ApiModelProperty(value = "상태", required = true)
		private Boolean visible;
		@ApiModelProperty(value = "내용")
		private String content;
	}

	@ApiModel(value = "NoticeListDTO.UpdateRequest")
	@Getter
	@Setter
	@Data
	public static class UpdateRequest {
		@ApiModelProperty(value = "제목")
		private String title;
		@ApiModelProperty(value = "상태", required = true)
		private Boolean visible;
		@ApiModelProperty(value = "내용")
		private String content;
		@ApiModelProperty(value = "수정 시: 삭제 된 파일 List<String> 자료 pk AND 파일 pk")
		private List<Long> deleteFiles;
	}

	@ApiModel(value = "NoticeListDTO.deleteList")
	@Getter
	@Setter
	@Data
	public static class deleteList {
		@ApiModelProperty(value = "선택 삭제할 공지사항 번호 pk (List<Long>)")
		private List<Long> deleteList;
	}



	// Response DTO
	@ApiModel(value = "NoticeListDTO.ListResponse")
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class ListResponse {
		@ApiModelProperty(value = "공지사항 번호(PK)")
		private Long id;
		@ApiModelProperty(value = "제목")
		private String title;
		@ApiModelProperty(value = "등록일")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime createdAt;
		@ApiModelProperty(value =  "상태")
		private boolean visible;
		@ApiModelProperty(value = "최초작업객체")
		private String frstJobObj;
	}

	@ApiModel(value = "NoticeListDTO.DetailResponse")
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class DetailResponse {
		@ApiModelProperty(value = "공지사항 번호(PK)")
		private Long id;
		@ApiModelProperty(value = "제목")
		private String title;
		@ApiModelProperty(value = "상태")
		private boolean visible;
		@ApiModelProperty(value = "내용")
		private String content;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		@ApiModelProperty(value = "등록일")
		private LocalDateTime createdAt;
		@ApiModelProperty(value = "첨부파일")
		private List<NoticeFileResponse> files;
	}

	@ApiModel(value = "NoticeListDTO.MngResponse")
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class MngResponse {
		@ApiModelProperty(value = "공지사항 - 관리자 회원 번호")
		private Long id;
	}

	@Getter
	@ApiModel(value = "NoticeListDTO.NoticeFileResponse")
	@Builder
	public static class NoticeFileResponse {
		@ApiModelProperty(value = "첨부파일 번호 (pk)")
		private Long id;
		@ApiModelProperty(value = "첨부파일 전체 파일 명")
		private String name;
		@ApiModelProperty(value = "첨부파일 실제 저장명 (dir/uuid_yyyyMMdd)")
		private String uuid;
		@ApiModelProperty(value = "첨부파일 크기 (bytes)")
		private double size;
		@ApiModelProperty(value = "첨부파일 순서 (min : 1)")
		private int order;
	}
}
