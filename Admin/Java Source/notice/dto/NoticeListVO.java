package kr.ge.kwdi.gep.admin.domain.notice.dto;

import kr.ge.kwdi.gep.admin.global.util.crypto.CryptoTarget;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NoticeListVO {

	// Param VO
	@Data
	@Builder
	public static class SearchParam {
		private String q; //검색어
		private String searchType; //검색 조건
	}

	@Data
	@Builder
	public static class InsertParam {
		private Long entityId; //공지사항 번호
		private String title; //게시물 제목
		private String visible; //게시물 내용 공개 여부
		private String content; //게시물 내용
		private Long mngNo; //관리자회원 번호
		private String existFile; //첨부파일여부
		private int fileCount; //파일 갯수
		private int atchFileSize; //첨부파일 크기
		private Long atchFileNo; //첨부파일 번호
		private List<MultipartFile> files; //첨부파일 리스트
		@CryptoTarget
		private String frstJobObj; //최초작업객체
		private String lastJobObj; //최종작업객체
	}

	@Data
	public static class FileInsertParam {
		private Long entityId; //공지사항 번호
		private String fileOrgnlNm; //파일 원본명
		private String fileActlStrgNm; //파일 실제 저장명 (UUID)
		private String fileClsfCd; //파일 확장자 구분 코드
		private Long fileSize; //파일 사이즈
	}

	@Data
	@Builder
	public static class UpdateParam {
		private Long entityId; //공지사항 번호
		private String title; //게시물 제목
		private String visible; //게시물 내용 공개 여부
		private String content; //게시물 내용
		@CryptoTarget
		private String lastJobObj; //최종작업객체
		private List<MultipartFile> files; //첨부파일 리스트
	}

	@Data
	@Builder
	public static class DeleteParam {
		private List<Long> list; //pk 정보 list
		private String lastJobObj; //최종작업객체
	}



	// Result VO
	@Data
	public static class ListResult {
		private Long id; //공지사항 번호
		private String title; //게시물 제목
		private LocalDateTime createdAt; //최초작업일시
		private String visible; //게시물 내용 공개 여부
		private String frstJobObj;
	}

	@Data
	public static class DetailResult {
		private Long id; //공지사항 번호
		private String title; //제목
		private String visible; //게시물 내용 공개 여부
		private String content; //게시물 내용
		private LocalDateTime createdAt; //최초작업일시
	}

	@Getter
	public static class FileResult {
		private Long id; //첨부파일 번호
		private String originalName; //첨부파일 원본 명
		private String storageName; //첨부파일 실제저장 명
		private String code; //첨부파일 분류 코드
		private int fileSize; //첨부파일 크기
		private int ordr; //첨부파일 순서
	}
}
