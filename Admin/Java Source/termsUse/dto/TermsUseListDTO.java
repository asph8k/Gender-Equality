package kr.ge.kwdi.gep.admin.domain.termsUse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "TermsUse DTO")
@Data
public class TermsUseListDTO {

    // Request DTO
    @ApiModel(value = "TermsUseListDTO.SearchRequest")
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

    @ApiModel(value = "TermsUseListDTO.InsertRequest")
    @Getter
    @Setter
    @NoArgsConstructor
    @Data
    public static class InsertRequest {
        @ApiModelProperty(value = "내용")
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "시행일자")
        private String startDate;
    }

    @ApiModel(value = "TermsUseListDTO.UpdateRequest")
    @Getter
    @Setter
    @NoArgsConstructor
    @Data
    public static class UpdateRequest {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "종료일자")
        private String startDate;
        @ApiModelProperty(value = "내용")
        private String content;
    }

    @ApiModel(value = "TermsUseListDTO.deleteList")
    @Getter
    @Setter
    @NoArgsConstructor
    @Data
    public static class deleteList {
        @ApiModelProperty(value = "선택 삭제할 이용약관 번호 pk (List<Long>)")
        private List<Long> deleteList;
    }



    // Response DTO
    @ApiModel(value = "TermsUseListDTO.ListResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class ListResponse {
        @ApiModelProperty(value = "이용약관 번호")
        private Long id;
        @ApiModelProperty(value = "시행일자")
        private String startDate;
        @ApiModelProperty(value = "종료일자")
        private String endDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "등록일")
        private LocalDateTime created;
    }

    @ApiModel(value = "TermsUseListDTO.DetailResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class DetailResponse {
        @ApiModelProperty(value = "이용약관 번호")
        private Long id;
        @ApiModelProperty(value = "내용")
        private String content;
        @ApiModelProperty(value = "시행일자")
        private String startDate;
        @ApiModelProperty(value = "종료일자")
        private String endDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "등록일")
        private LocalDateTime created;
    }

    @ApiModel(value = "TermsUseListDTO.DetailResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class MngNoResponse {
        @ApiModelProperty(value = "이용약관 번호")
        private Long id;
    }

    @ApiModel(value = "TermsUseListDTO.BeforeDateResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class BeforeDateResponse {
        @ApiModelProperty(value = "종료일자")
        private String endDate;
    }

    @ApiModel(value = "TermsUseListDTO.TermsPkListResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TermsPkListResponse {
        @ApiModelProperty(value = "이용약관 번호")
        private Long id;
    }

    @ApiModel(value = "TermsUseListDTO.BeforeDataInfoResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BeforeDataInfoResponse {
        @ApiModelProperty(value = "이용약관 번호")
        private Long id;
        @ApiModelProperty(value = "시행일자")
        private String startDate;
        @ApiModelProperty(value = "종료일자")
        private String endDate;
        @ApiModelProperty(value = "내용")
        private String content;
    }

    @ApiModel(value = "TermsUseListDTO.BeginDtListResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BeginDtListResponse {
        @ApiModelProperty(value = "시행일자")
        private String startDate;
    }

    @ApiModel(value = "TermsUseListDTO.FirstInsertTermsResponse")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FirstInsertTermsResponse {
        @ApiModelProperty(value = "이용약관 번호")
        private Long id;
        @ApiModelProperty(value = "내용")
        private String content;
    }
}
