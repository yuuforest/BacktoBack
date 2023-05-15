package com.backtoback.media.video.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@RedisHash
public class HighLight implements Serializable {

    // high light id
    @Id
    private String id;

    private String gameSeq;

    private String highLightPath;
}
