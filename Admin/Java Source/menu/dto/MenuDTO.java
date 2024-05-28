package kr.ge.kwdi.gep.admin.domain.menu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@ApiModel(value = "Menu DTO")
@Data
public class MenuDTO {

    // Request DTO
    @ApiModel(value = "MenuDTO.SearchRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class SearchRequest {
        @ApiModelProperty(value = "검색어")
        private String q;
        @ApiModelProperty(value = "검색 조건(groupName : 메뉴그룹명, name : 메뉴명)")
        private String searchType;
    }

    @ApiModel(value = "MenuDTO.InsertRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class InsertRequest {
        @ApiModelProperty(value = "메뉴그룹명")
        private String groupName;
        @JsonIgnore
        @ApiModelProperty(value = "상위 메뉴")
        private Long upperId;
        @ApiModelProperty(value = "메뉴명")
        private String name;
        @ApiModelProperty(value = "URL")
        private String url;
        @ApiModelProperty(value = "저작권 유형")
        private Long mngrId;
        @ApiModelProperty(value = "저작권 담당")
        private String levelId;
        @ApiModelProperty(value = "정렬순서")
        private int ordr;
        @ApiModelProperty(value = "사용여부")
        private Boolean visible;
        @ApiModelProperty(value = "비고")
        private String rmrk;
    }

    @ApiModel(value = "MenuDTO.UpdateRequest")
    @Getter
    @NoArgsConstructor
    @Data
    public static class UpdateRequest {
        @ApiModelProperty(value = "메뉴그룹명")
        private String groupName;
        @ApiModelProperty(value = "메뉴명")
        private String name;
        @ApiModelProperty(value = "저작권 유형")
        private String levelId;
        @ApiModelProperty(value = "저작권 담당")
        private Long mngrId;
        @ApiModelProperty(value = "정렬 순서")
        private int ordr;
        @ApiModelProperty(value = "사용여부")
        private Boolean visible;
        @ApiModelProperty(value = "비고")
        private String rmrk;
    }

    @ApiModel(value = "MenuDTO.deleteList")
    @Getter
    @NoArgsConstructor
    @Data
    public static class deleteList {
        @ApiModelProperty(value = "선택 삭제할 메뉴 번호 pk (List<Long>)")
        private List<Long> deleteList;
    }



    // Response DTO
    @ApiModel(value = "MenuDTO.MenuListResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MenuListResponse {
        @ApiModelProperty(value = "메뉴 번호")
        private Long id;
        @ApiModelProperty(value = "메뉴그룹명")
        private String groupName;
        @ApiModelProperty(value = "메뉴명")
        private String name;
        @ApiModelProperty(value = "메뉴ID")
        private Long upperId;
        @ApiModelProperty(value = "URL")
        private String url;
        @ApiModelProperty(value = "저작권 유형")
        private String levelName;
        @ApiModelProperty(value = "정렬순서")
        private int ordr;
        @ApiModelProperty(value = "사용여부")
        private boolean visible;
    }

    @ApiModel(value = "MenuDTO.DetailResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DetailResponse {
        @ApiModelProperty(value = "메뉴 번호")
        private Long id;
        @ApiModelProperty(value = "상위 아이디")
        private Long upperId;
        @ApiModelProperty(value = "메뉴그룹명")
        private String groupName;
        @ApiModelProperty(value = "메뉴명")
        private String name;
        @ApiModelProperty(value = "메뉴ID")
        private Long menuId;
        @ApiModelProperty(value = "URL")
        private String url;
        @ApiModelProperty(value = "저작권 번호")
        private String nuriLevelId;
        @ApiModelProperty(value = "저작권 유형")
        private String levelName;
        @ApiModelProperty(value = "내용")
        private String content;
        @ApiModelProperty(value = "저작권 담당 번호")
        private Long nuriMangerId;
        @ApiModelProperty(value = "담당 부서")
        private String department;
        @ApiModelProperty(value = "저작권 담당자")
        private String manager;
        @ApiModelProperty(value = "정렬순서")
        private int ordr;
        @ApiModelProperty(value = "정렬 순서 개수")
        private int ordrCount;
        @ApiModelProperty(value = "사용여부")
        private boolean visible;
        @ApiModelProperty(value = "비고")
        private String rmrk;
        @ApiModelProperty(value = "아이콘")
        private String image;
        @ApiModelProperty(value = "아이콘명")
        private String imageName;
    }

    @ApiModel(value = "MenuDTO.PblcNuriLvlListResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class PblcNuriLvlListResponse {
        @ApiModelProperty(value = "공공누리 레벨 번호")
        private Long id;
        @ApiModelProperty(value = "공공누리 레벨 아이디")
        private String levelId;
        @ApiModelProperty(value = "저작권명")
        private String levelName;
        @ApiModelProperty(value = "내용")
        private String content;
    }

    @ApiModel(value = "MenuDTO.PblcNuriMngListResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class PblcNuriMngListResponse {
        @ApiModelProperty(value = "공공누리 번호")
        private Long id;
        @ApiModelProperty(value = "담당부서")
        private String department;
        @ApiModelProperty(value = "담당자")
        private String manager;
    }

    @ApiModel(value = "MenuDTO.MenuNoResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MenuNoResponse {
        @ApiModelProperty(value = "메뉴 번호")
        private Long id;
        @ApiModelProperty(value = "URL")
        private String url;
    }

    @ApiModel(value = "MenuDTO.MenuGrpListResponse")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MenuGrpListResponse {
        @ApiModelProperty(value = "그룹메뉴명")
        private List<MenuVO.MenuGrpListResult> menuGroup;
        @ApiModelProperty(value = "메뉴명")
        private List<MenuVO.MenuClsList> menu;
    }
}
