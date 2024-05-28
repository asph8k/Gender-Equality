package kr.ge.kwdi.gep.admin.domain.relatedInst.dto;

import kr.ge.kwdi.gep.admin.global.util.crypto.CryptoTarget;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RelatedInstVO {

    // Param VO
    @Data
    @Builder
    public static class SearchParam {
        private String q;  // 검색어
        private String searchType;  // 검색 조건(name : 기관명)
    }

    @Data
    @Builder
    public static class RltdInstInsertParam {
        private String name;  // 유관기관 이름
        private String url;  // 유관기관 URL 주소
        private String visible;  // 유관기관 표출 여부
        private String rmrk;  // 비고
        private String originalName;  // 이미지 파일 원본 명
        private String storageName;  // 이미지 파일 실제저장 명
        private Long fileSize;  // 이미지 파일 사이즈
        @CryptoTarget
        private String frstJobObj;  // 최초작업객체
        private String lastJobObj;  // 최종작업객체
        private Long entityId;  // 유관기관 번호
    }

    @Data
    @Builder
    public static class RltdInstUpdateParam {
        private Long entityId;  // 유관기관 번호
        private String name;  // 유관기관 이름
        private String url;  // 유관기관 URL 주소
        private String visible;  // 유관기관 표출 여부
        private String rmrk;  // 비고
        private String originalName;  // 이미지 파일 원본 명
        private String storageName;  // 이미지 파일 실제저장 명
        private Long fileSize;  // 유관기관 이미지 파일 사이즈
        @CryptoTarget
        private String lastJobObj;  // 최종작업객체
    }

    @Data
    @Builder
    public static class RltdInstDeleteParam {
        private List<Long> list;  // 유관기관 번호
    }



    // Result VO
    @Data
    public static class RltdInstListResult {
        private Long id;  // 유관기관 번호
        private String name;  // 유관기관 이름
        private String fileOriginal;  // 이미지 파일 원본 명
        private String fileStorage;  // 이미지 파일 실제저장 명
        private Long fileSize;  // 이미지 파일 사이즈
        private String url;  // 유관기관 URL 주소
        private String visible;  // 유관기관 표출 여부
        private LocalDateTime created;  // 최초작업일시
    }

    @Data
    public static class RltdInstDetailResult {
        private Long id;  // 유관기관 번호
        private String name;  // 유관기관 이름
        private String url;  // 유관기관 URL 주소
        private String fileOriginal;  // 이미지 파일 원본 명
        private String fileStorage;  // 이미지 파일 실제저장 명
        private Long fileSize;  // 이미지 파일 사이즈
        private String visible;  // 유관기관 표출 여부
        private String rmrk;  // 비고
        private LocalDateTime created;  // 최초작업일시
    }

    @Data
    public static class ImgFileDtlResult {
        private Long id;  // 유관기관 번호
        private String originalName;  // 이미지 파일 원본 명
        private String storageName;  // 이미지 파일 실제저장 명
        private Long imgSize;  // 이미지 파일 사이즈
        private int ordr;  // 이미지 파일 순서
    }
}
