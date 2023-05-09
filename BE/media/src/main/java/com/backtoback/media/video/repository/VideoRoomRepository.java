package com.backtoback.media.video.repository;

import com.backtoback.media.video.domain.VideoRoom;
import org.springframework.data.repository.CrudRepository;

public interface VideoRoomRepository extends CrudRepository<VideoRoom, String> {
}
