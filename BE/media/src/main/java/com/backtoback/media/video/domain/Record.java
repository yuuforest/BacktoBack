package com.backtoback.media.video.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@RedisHash
public class Record implements Serializable {

    // 경기 id
    @Id
    private String id;

    private String recordPath;

}
