package kr.ge.kwdi.gep.platform.domain.main.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MainNoticeVO {

    @Data
    public static class Result {
        private Long id;  // 공지사항 번호
        private String title;  // 게시물 제목
        private LocalDateTime created;  // 등록일
    }
}
