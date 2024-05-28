package kr.ge.kwdi.gep.platform.domain.notice.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.ge.kwdi.gep.platform.domain.file.dto.FileDTO;
import kr.ge.kwdi.gep.platform.global.constant.ConstantValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "Notice DTO")
public class NoticeDTO {

	@ApiModel(value = "NoticeDTO.Request")
	@Data
	public static class Request {
		@ApiModelProperty(value = "검색어")
		private String q;
		@ApiModelProperty(value = "검색 타입(제목 : title)")
		private String searchType;
		@ApiModelProperty(value = "페이지 인덱스")
		private int pageIndex = 0;
		@ApiModelProperty(value = "페이지 로우 카운트")
		private int rowCount = ConstantValue.PAGE_DEFAULT_ROW_COUNT;
	}

	@ApiModel(value = "NoticeDTO.Response")
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		/** 공지사항 */
		@ApiModelProperty(value = "공지사항 번호")
		private Long noticeNo;
		@ApiModelProperty(value = "게시물 제목")
		private String postTitle;
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@ApiModelProperty(value = "등록 날자")
		private LocalDateTime registrationDate;
	}

	@ApiModel(value = "NoticeDTO.Detail")
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Detail {
		/** 공지사항 */
		@ApiModelProperty(value = "공지사항 번호")
		private Long noticeNo;
		@ApiModelProperty(value = "게시물 제목")
		private String postTitle;
		@ApiModelProperty(value = "게시물 내용")
		private String postContent;
		@ApiModelProperty(value = "게시물 첨부파일 여부")
		private String postAttachedFileYn;
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@ApiModelProperty(value = "등록 날짜")
		private LocalDateTime registrationDate;
		@ApiModelProperty(value = "첨부 파일")
		private List<FileDTO.Response> atchFileList;
		@ApiModelProperty(value = "이전 글")
		private NoticeVO.Previous previous;
		@ApiModelProperty(value = "다음 글")
		private NoticeVO.Next next;
	}


	@Getter
	@Builder
	public static class Previous {
		@ApiModelProperty(value = "이전 글 번호")
		private Long previousNoticeNo;
		@ApiModelProperty(value = "이전 글 제목")
		private String previousNoticeTitle;
	}

	@Getter
	@Builder
	public static class Next {
		@ApiModelProperty(value = "다음 글 번호")
		private Long nextNoticeNo;
		@ApiModelProperty(value = "이전 글 제목")
		private String nextNoticeTitle;
	}

}
