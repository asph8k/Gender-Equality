package kr.ge.kwdi.gep.admin.domain.privacypolicy.dto;

import kr.ge.kwdi.gep.admin.global.util.crypto.CryptoTarget;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PrivacyPolicyListVO {

    // Param VO
    @Data
    @Builder
    public static class SearchParam {
        private String startDate; // 기간 검색 (시작일 yyyyMMdd)
        private String endDate; // 기간 검색 (종료 일 yyyyMMdd)
    }

    @Data
    @Builder
    public static class InsertParam {
        private Long entityId; //개인정보처리방침 번호
        private String startDate; //개인정보 처리방침 등록 일시
        private String content; //개인정보처리방침 내용
        @CryptoTarget
        private String frstJobObj; //최초작업객체
        private String lastJobObj; //최종작업객체
    }

    @Data
    @Builder
    public static class UpdateParam {
        private Long entityId; //개인정보처리방침 번호
        private String startDate; //시행일자
        private String content; // 내용
        @CryptoTarget
        private String lastJobObj; //최종작업객체
    }

    @Data
    @Builder
    public static class DeleteParam {
        private List<Long> list; //개인정보처리방침 번호
    }

    @Data
    @Builder
    public static class BeforeDateParam {
        private Long entityId; //개인정보처리방침 번호
        private String endDate; //개인정보 처리방침 종료 일시
    }



    // Result VO
    @Data
    public static class ListResult {
        private Long id; //개인정보처리방침 번호
        private String startDate; //개인정보 처리방침 등록 일시
        private String endDate; //개인정보 처리방침 종료 일시
        private LocalDateTime created; //등록일
    }

    @Data
    public static class DetailResult {
        private String startDate; //개인정보 처리방침 등록 일시
        private String endDate; //개인정보 처리방침 종료 일시
        private String content; //개인정보처리방침 내용
        private LocalDateTime created; //등록일
    }

    @Data
    public static class BeforeDateResult {
        private String endDate; //개인정보 처리방침 종료 일시
    }

    @Data
    public static class PrvcPlcyPkListResult {
        private Long id; //개인정보처리방침 번호
        private String endDate; //개인정보 처리방침 종료 일시
    }

    @Data
    public static class PrvcPlcyPkMaxResult {
        private Long id; //개인정보처리방침 번호
    }

    @Data
    public static class BeforeDataInfoResult {
        private Long id; //개인정보처리방침 번호
        private String startDate; //개인정보처리방침 시행일자
        private String endDate; //개인정보처리방침 종료일자
        private String content; //내용
    }

    @Data
    public static class FirstBeginDtResult {
        private String startDate; //시행일자
    }

    @Data
    public static class AllResult {
        private Long id; //개인정보처리방침 번호
        private String startDate; //개인정보처리방침 시행일자
        private String endDate; //개인정보처리방침 종료일자
        private LocalDateTime created; //등록일
    }

    @Data
    public static class MinusPKResult {
        private Long id; //개인정보처리방침 번호
    }

    @Data
    public static class BeforeDatePlusResult {
        private String startDate; //개인정보처리방침 시행일자
    }

    @Data
    public static class NewBeforeDataResult {
        private Long id; //개인정보처리방침 번호
    }

    @Data
    public static class FirstInsertPrcyResult {
        private Long id; //개인정보처리방침 번호
        private String content; //내용
    }

    @Data
    public static class PrvsStartDateResult {
        private String startDate; //개인정보처리방침 시행일자
    }
}
