package kr.ge.kwdi.gep.admin.domain.apiApply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "ApiApply DTO")
@Data
public class ApiApplyDTO {

    // Request DTO
    @ApiModel(value = "ApiApplyDTO.SearchRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class SearchRequest {
        @ApiModelProperty(value = "검색어")
        private String q;
        @ApiModelProperty(value = "검색 조건(applyName, applyInstNm, applyEml)")
        private String searchType;
    }

    @ApiModel(value = "ApiApplyDTO.UpdateRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class UpdateRequest {
        @ApiModelProperty(value = "상태", required = true)
        private Boolean visible;
    }

    @ApiModel(value = "ApiApplyDTO.deleteList")
    @Getter
    @NoArgsConstructor
    @Data
    public static class deleteList {
        @ApiModelProperty(value = "선택 삭제할 Api 신청 번호 pk (List)")
        private List<Long> deleteList;
    }

    @ApiModel(value = "ApiApplyDTO.Content")
    @Getter
    @NoArgsConstructor
    @Data
    public static class Content {
        @ApiModelProperty(value = "담당자")
        private String name;
        @ApiModelProperty(value = "사유")
        private String content;
    }

    @ApiModel(value = "ApiApplyDTO.CertRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class CertRequest {
        @ApiModelProperty(value = "이메일 인증번호")
        private Long cert;
        @ApiModelProperty(value = "생성일시")
        private String createDate;
        @ApiModelProperty(value = "종료일시")
        private String endDate;
        @ApiModelProperty(value = "상태")
        private Boolean visible;
    }

    @ApiModel(value = "ApiApplyDTO.AprvRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class AprvRequest {
        @ApiModelProperty(value = "번호")
        private List<Long> entityId;
    }



    // Response DTO
    @ApiModel(value = "ApiApplyDTO.ListResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ListResponse {
        @ApiModelProperty(value = "API 신청 번호")
        private Long id;
        @ApiModelProperty(value = "신청자명")
        private String name;
        @ApiModelProperty(value = "신청자 연락처")
        private String telNumber;
        @ApiModelProperty(value = "신청자 기관명")
        private String instName;
        @ApiModelProperty(value = "신청자 이메일")
        private String email;
        @ApiModelProperty(value = "이용서비스명")
        private String serviceName;
        @ApiModelProperty(value = "활용 목적")
        private String purpose;
        @ApiModelProperty(value = "상태")
        private String visible;
    }

    @ApiModel(value = "ApiApplyDTO.DetailResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DetailResponse {
        @ApiModelProperty(value = "API 신청 번호")
        private Long id;
        @ApiModelProperty(value = "신청자명")
        private String name;
        @ApiModelProperty(value = "신청자 연락처")
        private String telNumber;
        @ApiModelProperty(value = "신청자 기관명")
        private String instName;
        @ApiModelProperty(value = "신청자 이메일")
        private String email;
        @ApiModelProperty(value = "이용서비스명")
        private String serviceName;
        @ApiModelProperty(value = "활용 목적")
        private String purpose;
        @ApiModelProperty(value = "API 키")
        private String apiKey;
        @ApiModelProperty(value = "상태")
        private String visible;
    }

    @ApiModel(value = "ApiApplyDTO.RjctResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class RjctResponse {
        @ApiModelProperty(value = "API 신청 번호")
        private Long id;
        @ApiModelProperty(value = "담당자(ID)")
        private String name;
        @ApiModelProperty(value = "사유")
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "일시")
        private LocalDateTime created;
    }
}
