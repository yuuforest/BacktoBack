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

<<<<<<< HEAD
    private Long mediaStartTime;

    public MessageDto(Long gameSeq, GameActiveType gameActiveType,Long mediaStartTime) {
        this.gameSeq = gameSeq;
        this.gameActiveType = gameActiveType;
        this.mediaStartTime = mediaStartTime;
=======
    public MessageDto(Long gameSeq, GameActiveType gameActiveType) {
        this.gameSeq = gameSeq;
        this.gameActiveType = gameActiveType;
>>>>>>> d3e242c9852359e36e08f18e14d7f969c3bdb292
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "gameSeq=" + gameSeq +
                ", gameActiveType=" + gameActiveType +
                '}';
    }

}
