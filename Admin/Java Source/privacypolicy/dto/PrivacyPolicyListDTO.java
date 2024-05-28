package kr.ge.kwdi.gep.admin.domain.privacypolicy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "PrivacyPolicy DTO")
@Data
public class PrivacyPolicyListDTO {

    // Request DTO
    @ApiModel(value = "PrivacyPolicyListDTO.SearchRequest")
    @Getter
    @Setter
    @NoArgsConstructor
    @Data
    public static class SearchRequest {
        @ApiModelProperty(value = "기간 검색 (시작일 yyyyMMdd)")
        private String startDate;
        @ApiModelProperty(value = "기간 검색 (종료일 yyyyMMdd)")
        private String endDate;
    }

    @ApiModel(value = "PrivacyPolicyListDTO.InsertRequest")
    @Getter
    @Setter
    @NoArgsConstructor
    @Data
    public static class InsertRequest {
        @ApiModelProperty(value = "시행일자")
        private String startDate;
        @ApiModelProperty(value = "내용")
        private String content;
    }

    @ApiModel(value = "PrivacyPolicyListDTO.Request")
    @Getter
    @Setter
    @NoArgsConstructor
    @Data
    public static class UpdateRequest {
        @ApiModelProperty(value = "시행일자")
        private String startDate;
        @ApiModelProperty(value = "내용")
        private String content;
    }

    @ApiModel(value = "PrivacyPolicyListDTO.deleteList")
    @Getter
    @Setter
    @NoArgsConstructor
    @Data
    public static class deleteList {
        @ApiModelProperty(value = "선택 삭제할 FAQ 번호 pk (List<Long>)")
        private List<Long> deleteList;
    }



    // Response DTO
    @ApiModel(value = "PrivacyPolicyListDTO.ListResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class ListResponse {
        @ApiModelProperty(value = "개인정보처리방침 번호")
        private Long id;
        @ApiModelProperty(value = "시행일자")
        private String startDate;
        @ApiModelProperty(value = "종료일자")
        private String endDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "등록일")
        private LocalDateTime created;
    }

    @ApiModel(value = "PrivacyPolicyListDTO.DetailResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class DetailResponse {
        @ApiModelProperty(value = "시행일자")
        private String startDate;
        @ApiModelProperty(value = "종료일자")
        private String endDate;
        @ApiModelProperty(value = "내용")
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "등록일")
        private LocalDateTime created;
    }

    @ApiModel(value = "PrivacyPolicyListDTO.PrvcPlcyPkListResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class PrvcPlcyPkListResponse {
        @ApiModelProperty(value = "개인정보처리방침 번호")
        private Long id;
    }

    @ApiModel(value = "PrivacyPolicyListDTO.PrvcPlcyPkMaxResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class PrvcPlcyPkMaxResponse {
        @ApiModelProperty(value = "개인정보처리방침 번호")
        private Long id;
    }

    @ApiModel(value = "PrivacyPolicyListDTO.BeginDtListResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class BeginDtListResponse {
        @ApiModelProperty(value = "개인정보처리방침 번호")
        private Long id;
        @ApiModelProperty(value = "시행일자")
        private String startDate;
    }

    @ApiModel(value = "PrivacyPolicyListDTO.BeforeDataInfoResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class BeforeDataInfoResponse {
        @ApiModelProperty(value = "번호")
        private Long id;
        @ApiModelProperty(value = "시행일자")
        private String startDate;
        @ApiModelProperty(value = "종료일자")
        private String endDate;
        @ApiModelProperty(value = "내용")
        private String content;
    }

    @ApiModel(value = "PrivacyPolicyListDTO.FirstBeginDtResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class FirstBeginDtResponse {
        @ApiModelProperty(value = "시행일자")
        private String startDate;
    }

    @ApiModel(value = "PrivacyPolicyListDTO.BeginDateMaxResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class BeginDateMaxResponse {
        @ApiModelProperty(value = "시행일자")
        private String startDate;
        @ApiModelProperty(value = "번호")
        private Long id;
    }

    @ApiModel(value = "PrivacyPolicyListDTO.FirstInsertPrcyResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class FirstInsertPrcyResponse {
        @ApiModelProperty(value = "번호")
        private Long id;
        @ApiModelProperty(value = "내용")
        private String content;
    }
}
