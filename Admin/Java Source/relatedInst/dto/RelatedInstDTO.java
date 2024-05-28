package kr.ge.kwdi.gep.admin.domain.relatedInst.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "RelatedInst DTO")
@Data
public class RelatedInstDTO {

    // Request DTO
    @ApiModel(value = "RelatedInstDTO.SearchRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class SearchRequest {
        @ApiModelProperty(value = "검색어")
        private String q;
        @ApiModelProperty(value = "검색 조건(title : 기관명)")
        private String searchType;
    }

    @ApiModel(value = "RelatedInstDTO.RltdInstInsertRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class RltdInstInsertRequest {
        @ApiModelProperty(value = "기관명")
        private String name;
        @ApiModelProperty(value = "URL")
        private String url;
        @ApiModelProperty(value = "사용 여부", required = true)
        private Boolean visible;
        @ApiModelProperty(value = "비고")
        private String rmrk;
    }

    @ApiModel(value = "RelatedInstDTO.RltdInstUpdateRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class RltdInstUpdateRequest {
        @ApiModelProperty(value = "유관기관 번호")
        private Long entityId;
        @ApiModelProperty(value = "기관명")
        private String name;
        @ApiModelProperty(value = "URL")
        private String url;
        @ApiModelProperty(value = "사용 여부", required = true)
        private Boolean visible;
        @ApiModelProperty(value = "비고")
        private String rmrk;
    }

    @ApiModel(value = "RelatedInstDTO.deleteList")
    @Getter
    @NoArgsConstructor
    @Data
    public static class deleteList {
        @ApiModelProperty(value = "선택 삭제할 유관 기관 번호 pk (List<Long>)")
        private List<Long> deleteList;
    }



    // Response DTO
    @ApiModel(value = "RelatedInstDTO.RltdInstListResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class RltdInstListResponse {
        @ApiModelProperty(value = "유관기관 번호")
        private Long id;
        @ApiModelProperty(value = "기관명")
        private String name;
        @ApiModelProperty(value = "기관로고")
        private String fileOriginal;
        @ApiModelProperty(value = "기관로고명")
        private String fileStorage;
        @ApiModelProperty(value = "URL")
        private String url;
        @ApiModelProperty(value = "사용 여부")
        private boolean visible;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "등록일")
        private LocalDateTime created;
    }

    @ApiModel(value = "RelatedInstDTO.RltdInstDetailResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class RltdInstDetailResponse {
        @ApiModelProperty(value = "유관기관 번호")
        private Long id;
        @ApiModelProperty(value = "기관명")
        private String name;
        @ApiModelProperty(value = "URL")
        private String url;
        @ApiModelProperty(value = "기관로고")
        private String fileOriginal;
        @ApiModelProperty(value = "로고 사이즈")
        private Long fileSize;
        @ApiModelProperty(value = "사용 여부")
        private boolean visible;
        @ApiModelProperty(value = "비고")
        private String rmrk;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "등록일")
        private LocalDateTime created;
    }
}
