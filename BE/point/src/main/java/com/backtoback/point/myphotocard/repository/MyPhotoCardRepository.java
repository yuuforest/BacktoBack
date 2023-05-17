package com.backtoback.point.myphotocard.repository;

import com.backtoback.point.myphotocard.domain.MyPhotoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyPhotoCardRepository extends JpaRepository<MyPhotoCard, Long>{

}

