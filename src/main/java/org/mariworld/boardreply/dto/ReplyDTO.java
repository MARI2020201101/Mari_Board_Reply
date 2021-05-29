package org.mariworld.boardreply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
    private Long rno;
    private String text;
    private String replyer;
    private LocalDateTime modDate;
    private LocalDateTime regDate;
    private Long bno;
}
