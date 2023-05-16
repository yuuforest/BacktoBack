package com.backtoback.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private Long teamSeq;
    private String teamName;
}
