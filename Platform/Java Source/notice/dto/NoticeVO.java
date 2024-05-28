package kr.ge.kwdi.gep.platform.domain.notice.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeVO {

	@Getter
	@Builder
	public static class Param {
		private String q;
		private String searchType;
		private int pageIndex;
		private int rowCount;
	}

	@Getter
	@Builder
	public static class NoticeList {
		private Long ntcMttrNo;
		private String pstTtl;
		private LocalDateTime frstJobDt;
	}

	@Getter
	@Builder
	public static class Detail {
		private Long ntcMttrNo;
		private String pstTtl;
		private String pstCn;
		private LocalDateTime frstJobDt;
		private String pstAtchFileYn;
	}

	@Getter
	@Builder
	public static class Previous {
		private Long previousNoticeNo;
		private String previousNoticeTitle;
	}

	@Getter
	@Builder
	public static class Next {
		private Long nextNoticeNo;
		private String nextNoticeTitle;
	}

}
