package kr.ge.kwdi.gep.admin.domain.apiApply.dto;

import kr.ge.kwdi.gep.admin.global.util.crypto.CryptoTarget;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiApplyVO {

    // Param VO
    @Data
    @Builder
    public static class SearchParam {
        private String q; //검색어
        private String searchType; //검색 조건(applyName 신청자 명: , applyInstNm : 신청자 기관명, applyEml : 신청자 이메일)
    }

    @Data
    @Builder
    public static class UpdateParam {
        private Long entityId; //API 신청 번호
        private String visible; //허용 상태
        @CryptoTarget
        private String lastJobObj; //최종작업객체
    }

    @Data
    @Builder
    public static class DeleteParam {
        private List<Long> list; //API 신청 번호
    }

    @Data
    @Builder
    public static class RjctParam {
        private Long entityId; //API 신청 번호
        private String name; //반려자 이름
        private String content; //반려사유
        @CryptoTarget
        private String lastJobObj; //최종작업객체
    }



    // Result VO
    @Data
    public static class ListResult {
        private Long id; //API 신청 번호
        private String name; //신청자 명
        private String telNumber; //신청자 연락처
        private String instName; //신청자 기관명
        private String email; //신청자 이메일
        private String serviceName; //활용서비스 명
        private String purpose; //활용목적
        private String visible; //허용 상태
        private String applyDate; //허용 일시
        private String delYn; //삭제 여부
        private String apiKey; //API KEY
    }

    @Data
    public static class DetailResult {
        private Long id; //API 신청 번호
        private String name; //신청자 명
        private String telNumber; //신청자 연락처
        private String instName; //신청자 기관명
        private String email; //신청자 이메일
        private String serviceName; //활용서비스 명
        private String purpose; //활용목적
        private String apiKey; //API KEY
        private String visible; //허용 상태
    }

    @Data
    public static class RjctResult {
        private Long id; //API 신청 번호
        private String name; //반려자 이름
        private String content; //반려사유
        private LocalDateTime created; //반려일시
    }
}
