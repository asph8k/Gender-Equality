package kr.ge.kwdi.gep.admin.domain.banner.dto;

import kr.ge.kwdi.gep.admin.global.util.crypto.CryptoTarget;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class BannerVO {

    // Param VO
    @Data
    @Builder
    public static class SearchParam {
        private String searchType;  // 검색 조건(mainDownOne : 메인 하단 한 개, mainDownLeftTwo : 메인 하단 두개 중 좌측​, mainDownRightTwo : 메인 하단 두개 중 우측)
    }

    @Data
    @Builder
    public static class InsertParam {
        private String name;  // 배너 이름
        private String content;  // 배너 설명
        private String positionCode;  // 위치 코드
        private Timestamp startDate;  // 시작일자
        private Timestamp endDate;  // 종료일자
        private String visible;  // 사용 여부
        private int ordr;  // 우선 순위
        private String url;  // url 주소
        private String target;  // 타겟코드
        private String imgOriginalName;  // 배너 이미지 원본 명
        private String imgStorageName;  // 배너 이미지 실제저장 명
        private Long imgSize;  // 배너 이미지 사이즈
        private Long width;  // 배너 이미지 가로길이
        private Long height;  // 배너 이미지 세로길이
        private String rmrk;  // 비고
        @CryptoTarget
        private String frstJobObj;  // 최초작업객체
        private String lastJobObj;  // 최종작업객체
    }

    @Data
    @Builder
    public static class UpdateParam {
        private Long entityId;  // 번호
        private String name;  // 배너 이름
        private String content;  // 배너 설명
        private Timestamp startDate;  // 시작일자
        private Timestamp endDate;  // 종료일자
        private String visible;  // 사용 여부
        private int ordr;  // 우선 순위
        private String url;  // url 주소
        private String target;  // 타겟코드
        private String imgOriginalName;  // 배너 이미지 원본 명
        private String imgStorageName;  // 배너 이미지 실제저장 명
        private Long imgSize;  // 배너 이미지 사이즈
        private Long width;  // 배너 이미지 가로길이
        private Long height;  // 배너 이미지 세로길이
        private String rmrk;  // 비고
        @CryptoTarget
        private String lastJobObj;  // 최종작업객체
    }

    @Data
    @Builder
    public static class DeleteParam {
        private List<Long> list;  // 번호
    }

    @Data
    @Builder
    public static class OrdrParam {
        private String entityId;  // 배너 위치 코드
        private int exstnOrdr;  // 기존 순서
        private int changeOrdr;  // 변경할 순서
    }



    // Result VO
    @Data
    public static class BannerListResult {
        private Long id;  // 배너 번호
        private String name;  // 배너 이름
        private String positionName;  // 배너 위치
        private String imgOriginalName;  // 배너 이미지 원본 명
        private String imgStorageName;  // 배너 이미지 실제저장 명
        private String visible;  // 사용 여부
        private String bnnrDate;  // 기간
        private int ordr;  // 우선 순위
    }

    @Data
    public static class BannerDetailResult {
        private Long id;  // 배너 번호
        private String name;  // 배너 이름
        private String positionCode;  // 위치 코드
        private String positionName;  // 배너 위치
        private Timestamp startDate;  // 시작일자
        private Timestamp startTime;  // 시작시간
        private Timestamp endDate;  // 종료일자
        private Timestamp endTime;  // 종료시간
        private String visible;  // 사용 여부
        private int ordr;  // 우선 순위
        private int ordrCount;  // 우선 순위 개수
        private String url;  // url 주소
        private String target;  // 타겟코드
        private String targetName;  // 타겟명
        private String imgOriginalName;  // 배너 이미지 원본 명
        private String imgStorageName;  // 배너 이미지 실제저장 명
        private Long imgSize;  // 배너 이미지 사이즈
        private Long width;  // 배너 이미지 가로길이
        private Long height;  // 배너 이미지 세로길이
        private String content;  // 배너 설명
        private String rmrk;  // 비고
    }

    @Data
    public static class BannerOrdrCountResult {
        private int ordrCount;  // 우선 순위 개수
    }

    @Data
    public static class BannerImgFileResult {
        private String imgOriginalName;  // 배너 이미지 원본 명
        private String imgStorageName;  // 배너 이미지 실제저장 명
        private Long imgSize;  // 배너 이미지 사이즈
        private Long width;  // 배너 이미지 가로길이
        private Long height;  // 배너 이미지 세로길이
    }

    @Data
    public static class BannerPositionCodeResult {
        private String id;  // 코드
        private String name;  // 코드명
    }

    @Data
    public static class BannerTargetCodeResult {
        private String id;  // 코드
        private String name;  // 코드명
    }
}
