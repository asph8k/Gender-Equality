package kr.ge.kwdi.gep.platform.domain.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@ApiModel(value = "MainNotice DTO")
@Data
public class MainNoticeDTO {

    @ApiModel(value = "MainNotice DTO")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Response {
        @ApiModelProperty(value = "제목")
        private String title;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "등록일")
        private LocalDateTime created;
        @ApiModelProperty(value = "URL")
        private String url;
    }
}
