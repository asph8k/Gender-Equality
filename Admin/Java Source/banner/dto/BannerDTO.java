package kr.ge.kwdi.gep.admin.domain.banner.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@ApiModel(value = "Banner DTO")
@Data
public class BannerDTO {

    // Request DTO
    @ApiModel(value = "BannerDTO.SearchRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class SearchRequest {
        @ApiModelProperty(value = "검색 조건(mainDownOne : 메인 하단 한 개, mainDownLeftTwo : 메인 하단 두개 중 좌측, mainDownRightTwo : 메인 하단 두개 중 우측)")
        private String searchType;
    }

    @ApiModel(value = "BannerDTO.InsertRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class InsertRequest {
        @ApiModelProperty(value = "배너명")
        private String name;
        @ApiModelProperty(value = "배너 위치")
        private String positionCode;
        @ApiModelProperty(value = "시작일자")
        private String startDate;
        @ApiModelProperty(value = "시작시간")
        private String startTime;
        @ApiModelProperty(value = "종료일자")
        private String endDate;
        @ApiModelProperty(value = "종료시간")
        private String endTime;
        @ApiModelProperty(value = "사용 여부")
        private Boolean visible;
        @ApiModelProperty(value = "배너 링크")
        private String url;
        @ApiModelProperty(value = "배너 타겟")
        private String target;
        @ApiModelProperty(value = "배너 설명")
        private String content;
        @ApiModelProperty(value = "너비")
        private Long width;
        @ApiModelProperty(value = "높이")
        private Long height;
        @ApiModelProperty(value = "비고")
        private String rmrk;
    }

    @ApiModel(value = "BannerDTO.UpdateRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class UpdateRequest {
        @ApiModelProperty(value = "배너명")
        private String name;
        @ApiModelProperty(value = "시작일자")
        private String startDate;
        @ApiModelProperty(value = "시작시간")
        private String startTime;
        @ApiModelProperty(value = "종료일자")
        private String endDate;
        @ApiModelProperty(value = "종료시간")
        private String endTime;
        @ApiModelProperty(value = "사용 여부")
        private Boolean visible;
        @ApiModelProperty(value = "우선순위")
        private int ordr;
        @ApiModelProperty(value = "배너 링크")
        private String url;
        @ApiModelProperty(value = "배너 타겟")
        private String target;
        @ApiModelProperty(value = "배너 이미지")
        private String imageName;
        @ApiModelProperty(value = "배너 설명")
        private String content;
        @ApiModelProperty(value = "너비")
        private Long width;
        @ApiModelProperty(value = "높이")
        private Long height;
        @ApiModelProperty(value = "비고")
        private String rmrk;
    }

    @ApiModel(value = "BannerDTO.deleteList")
    @Getter
    @Setter
    public static class deleteList {
        @ApiModelProperty(value = "선택 삭제할 배너 번호 pk (List<Long>)")
        private List<Long> deleteList;
    }



    // Response DTO
    @ApiModel(value = "BannerDTO.BannerListResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class BannerListResponse {
        @ApiModelProperty(value = "배너 번호")
        private Long id;
        @ApiModelProperty(value = "배너명")
        private String name;
        @ApiModelProperty(value = "배너 위치")
        private String positionName;
        @ApiModelProperty(value = "배너 이미지")
        private String image;
        @ApiModelProperty(value = "배너 이미지 이름")
        private String imageName;
        @ApiModelProperty(value = "사용 여부")
        private String visible;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "기간")
        private String bnnrDate;
        @ApiModelProperty(value = "우선순위")
        private int ordr;
    }

    @ApiModel(value = "BannerDTO.BannerDetailResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class BannerDetailResponse {
        @ApiModelProperty(value = "배너 번호")
        private Long id;
        @ApiModelProperty(value = "배너명")
        private String name;
        @ApiModelProperty(value = "배너 위치 코드")
        private String positionCode;
        @ApiModelProperty(value = "배너 위치")
        private String positionName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "시작일자")
        private Timestamp startDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "시작시간")
        private Timestamp startTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "종료일자")
        private Timestamp endDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "종료시간")
        private Timestamp endTime;
        @ApiModelProperty(value = "사용 여부")
        private boolean visible;
        @ApiModelProperty(value = "우선순위")
        private int ordr;
        @ApiModelProperty(value = "우선순위 개수")
        private int ordrCount;
        @ApiModelProperty(value = "배너 링크")
        private String url;
        @ApiModelProperty(value = "배너 타겟 코드")
        private String target;
        @ApiModelProperty(value = "배너 타겟")
        private String targetName;
        @ApiModelProperty(value = "배너 이미지")
        private String imageName;
        @ApiModelProperty(value = "배너 이미지 사이즈")
        private Long imgSize;
        @ApiModelProperty(value = "배너 이미지 너비")
        private Long width;
        @ApiModelProperty(value = "배너 이미지 높이")
        private Long height;
        @ApiModelProperty(value = "배너 설명")
        private String content;
        @ApiModelProperty(value = "비고")
        private String rmrk;
    }

    @ApiModel(value = "BannerDTO.BannerPositionCodeResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class BannerPositionCodeResponse {
        @ApiModelProperty(value = "코드")
        private String id;
        @ApiModelProperty(value = "코드명")
        private String name;
    }

    @ApiModel(value = "BannerDTO.BannerTargetCodeResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class BannerTargetCodeResponse {
        @ApiModelProperty(value = "코드")
        private String id;
        @ApiModelProperty(value = "코드명")
        private String name;
    }
}
