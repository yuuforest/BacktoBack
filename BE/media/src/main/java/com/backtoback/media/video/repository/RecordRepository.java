package com.backtoback.media.video.repository;

import com.backtoback.media.video.domain.Record;
import org.springframework.data.repository.CrudRepository;

public interface RecordRepository extends CrudRepository<Record,String> {
}
