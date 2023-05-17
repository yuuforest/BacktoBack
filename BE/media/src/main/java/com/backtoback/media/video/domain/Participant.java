package com.backtoback.media.video.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@RedisHash
public class Participant implements Serializable {

  @Id
  private String id;

  private String userId;

  private String gameSeq;

  private String webRtcEndpointId;
}
