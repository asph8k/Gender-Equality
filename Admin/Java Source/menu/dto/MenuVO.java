package kr.ge.kwdi.gep.admin.domain.menu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.ge.kwdi.gep.admin.global.util.crypto.CryptoTarget;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class MenuVO {

    // Param VO
    @Data
    @Builder
    public static class SearchParam {
        private String q;  // 검색어
        private String searchType;  // 검색 조건(groupName : 메뉴그룹명, name : 메뉴명)
    }

    @Data
    @Builder
    public static class InsertParam {
        private Long entityId;  // 번호
        private String name;  // 메뉴 이름
        private String levelId;  // 공공누리 레벨
        private Long mngrId;  // 여정연 공공누리 담당자
        private int ordr;  // 메뉴 순서
        private String visible;  // 메뉴 표시 여부
        private String rmrk;  // 비고
        private Long level;  // 메뉴 레벨
        private String url;  // 메뉴 URL 주소
        private Long upperId;  // 상위 메뉴
        private String originalName; // 이미지 원본명
        private String storageName; // 이미지 실제저장명
        private Long imgSize; // 이미지 사이즈
        private String imgUrl; // 이미지 url
        @CryptoTarget
        private String frstJobObj;  // 최초작업객체
        private String lastJobObj;  // 최종작업객체
        private String existFile;  // 첨부파일 여부
        private int fileCount;  // 첨부파일 개수
    }

    @Data
    @Builder
    public static class UpdateParam {
        private String name;  // 메뉴 이름
        private Long upperId;  // 상위 메뉴
        private String levelId;  // 공공누리 레벨
        private Long mngrId;  // 여정연 공공누리 담당자
        private String visible;  // 메뉴 표시 여부
        private String rmrk;  // 비고
        private String originalName; // 이미지 원본명
        private String storageName; // 이미지 실제저장명
        private Long imgSize; // 이미지 사이즈
        private String imgUrl; // 이미지 url
        private int ordr;  // 정렬 순서
        @CryptoTarget
        private String lastJobObj;  // 최종작업객체
        private Long entityId;  // 메뉴 번호(MENU_NO)
        private String existFile;  // 첨부파일 여부
        private int fileCount;  // 첨부파일 개수
    }

    @Data
    @Builder
    public static class OrdrParam {
        private Long entityId;
        private int exstnOrdr;
        private int changeOrdr;
    }

    @Data
    @Builder
    public static class DeleteParam {
        private List<Long> list;
    }



    // Result VO
    @Data
    public static class MenuListResult {
        private Long id;  // 메뉴 번호
        private String groupName;  // 메뉴 그룹명
        private String name;  // 메뉴 이름
        private String url;  // 메뉴 URL 주소
        private Long upperId;  // 상위 메뉴
        private int ordr;  // 메뉴 순서
        private String visible;  // 메뉴 표시 여부
        private String levelName;  // 공공누리 레벨명
    }

    @Data
    public static class DetailResult {
        private Long id;  // 메뉴 번호
        private Long upperId;  // 상위 아아디
        private String groupName;  // 메뉴 그룹명
        private String name;  // 메뉴 이름
        private Long menuId;  // 상위 메뉴
        private String url;  // 메뉴 URL 주소
        private String nuriLevelId;  // 공공누리 레벨 번호
        private String levelName;  // 공공누리 레벨명
        private String content;  // 공공누리 내용
        private Long nuriMangerId;  // 여정연 공공누리 담당자
        private int ordr;  // 메뉴 순서
        private int ordrCount;
        private String visible;  // 메뉴 표시 여부
        private String rmrk;  // 비고
        private String manager;  // 담당자 이름
        private String department;  // 담당부서 이름
        private String fileUrl; // 이미지 url
        private String originalName; // 이미지 원본명
        private String storageName; // 이미지 실제저장명
    }

    @Data
    public static class PblcNuriLvlListResult {
        private Long id;  // 공공누리 레벨 번호
        private String levelName;  // 공공누리 레벨명
        private String levelId;  // 공공누리 레벨
        private String content;  // 공공누리 내용
    }

    @Data
    public static class PblcNuriMngListResult {
        private Long id;  // 공공누리 번호
        private String department;  // 담당부서 이름
        private String manager;  // 담당자 이름
    }

    @Data
    public static class MenuGrpListResult {
        private Long id;  // 메뉴 번호
        @JsonIgnore
        private Long upperId; // 상위 메뉴
        private String name;  // 메뉴명
        @JsonIgnore
        private String url;  // 메뉴 url
        private String codeType;  // 코드 타입
    }

    @Data
    public static class MenuClsList {
        private Long id;  // 메뉴 번호
        private Long upperId; // 상위 메뉴
        private String name;  // 메뉴명
        @JsonIgnore
        private String url;  // 메뉴 url
        private String codeType;  // 코드 타입
    }

    @Data
    public static class MenuListAllResult {
        private String name;  // 메뉴명
    }

    @Data
    public static class UpperIdResult {
        private Long upperId;  // 상위 아이디
    }

    @Data
    public static class CountOrdrResult {
        private int ordrCount;  // 순서 개수
    }

    @Data
    public static class UseOrNotMenuListResult {
        private Long id;  // 메뉴 번호
        private String visible;  // 메뉴 표시 여부
    }

    @Data
    @Getter
    @Builder
    public static class MenuImgFileResult {
        private Long id;  // 메뉴 번호
        private String originalName;  // 이미지 원본명
        private String storageName;  // 이미지 실제저장명
        private String url;  // 이미지 url
        private int fileSize;  // // 이미지 사이즈
    }
}
