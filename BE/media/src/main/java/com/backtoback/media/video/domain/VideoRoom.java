package com.backtoback.media.video.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Data
@RedisHash
public class VideoRoom implements Serializable {

    @Id
    private String id;

    private String mediaPipelineId;

    private String playerEndpointId;

    private String recordEndpointId;


}
