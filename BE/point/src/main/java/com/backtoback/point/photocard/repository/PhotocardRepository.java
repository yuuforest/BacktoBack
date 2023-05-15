package com.backtoback.point.photocard.repository;

import com.backtoback.point.photocard.domain.PhotoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotocardRepository extends JpaRepository<PhotoCard, Long>{

}
