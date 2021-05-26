package org.mariworld.boardreply.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long bno;
    private String title;
    private String content;
    private LocalDateTime modDate;
    private LocalDateTime regDate;
    private String writerEmail;
    private String writerName;
    private int replyCount;
}
