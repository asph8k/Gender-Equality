package kr.ge.kwdi.gep.admin.domain.termsUse.dto;

import kr.ge.kwdi.gep.admin.global.util.crypto.CryptoTarget;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TermsUseListVO {

    // Param VO
    @Data
    @Builder
    public static class SearchParam {
        private String startDate;  //기간 검색 (시작일 yyyyMMdd)
        private String endDate;  //기간 검색 (종료일 yyyyMMdd)
    }

    @Data
    @Builder
    public static class InsertParam {
        private Long entityId;  //이용약관 번호
        private String content;  //이용약관 내용
        @CryptoTarget
        private String frstJobObj;  //최초작업객체
        private String lastJobObj;  //최종작업객체
        private String startDate;  //이용약관 등록 일시
    }

    @Data
    @Builder
    public static class UpdateParam {
        private Long entityId;  //이용약관 번호
        private String startDate;  //이용약관 종료 일시
        private String content;  //이용약관 내용
        @CryptoTarget
        private String lastJobObj;  //최종작업객체
    }

    @Data
    @Builder
    public static class DeleteParam {
        private List<Long> list;  //이용약관 번호
    }

    @Data
    @Builder
    public static class BeforeDateParam {
        private Long entityId;  //이용약관 번호
        private String endDate;  //이용약관 종료 일시
    }



    // Result VO
    @Data
    public static class ListResult {
        private Long id;  //이용약관 번호
        private String startDate;  //이용약관 등록 일시
        private String endDate;  //이용약관 종료 일시
        private LocalDateTime created;  //등록일
    }

    @Data
    public static class DetailResult {
        private Long id;  //이용약관 번호
        private String content;  //이용약관 내용
        private String startDate;  //이용약관 등록 일시
        private String endDate;  //이용약관 종료 일시
        private LocalDateTime created;  //등록일
    }

    @Data
    public static class MngNoResult {
        private Long id;  //이용약관 번호
    }

    @Data
    public static class BeforeDateResult {
        private String endDate;  //이용약관 종료 일시
    }

    @Data
    public static class TermsPkListResult {
        private Long id;  //이용약관 번호
        private String endDate;  //이용약관 종료 일시
    }

    @Data
    public static class BeforeDataInfoResult {
        private Long id;  //이용약관 번호
        private String startDate;  //이용약관 등록 일시
        private String endDate;  //이용약관 종료 일시
        private String content;  //내용
    }

    @Data
    public static class FirstBeginDtResult {
        private String startDate;  //이용약관 등록 일시
    }

    @Data
    public static class AllResult {
        private Long id;  //이용약관 번호
        private String startDate;  //이용약관 등록 일시
        private String endDate;  //이용약관 종료 일시
        private LocalDateTime created;  //등록일
    }

    @Data
    public static class MinusPKResult {
        private Long id;  //이용약관 번호
    }

    @Data
    public static class BeforeDatePlusResult {
        private String startDate;  //이용약관 등록 일시
    }

    @Data
    public static class NewBeforeDataResult {
        private Long id;  //이용약관 번호
    }

    @Data
    public static class FirstInsertTermsResult {
        private Long id;  //이용약관 번호
        private String content;  //내용
    }

    @Data
    public static class PrvsStartDateResult {
        private String startDate;  //이용약관 등록 일시
    }
}
