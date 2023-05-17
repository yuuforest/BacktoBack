package com.backtoback.media.video.dto;

import com.backtoback.media.game.domain.GameActiveType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class MessageDto implements Serializable {

    private Long gameSeq;

    private GameActiveType gameActiveType;

    public MessageDto(Long gameSeq, GameActiveType gameActiveType) {
        this.gameSeq = gameSeq;
        this.gameActiveType = gameActiveType;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "gameSeq=" + gameSeq +
                ", gameActiveType=" + gameActiveType +
                '}';
    }

}
