package com.backtoback.point.pointlog.repository;

import com.backtoback.point.pointlog.domain.PointLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointLogRepository extends JpaRepository<PointLog, Long> {

    List<PointLog> findByMemberMemberSeqOrderByPointLogSeqDesc(Long memberSeq);

}
