package com.backtoback.point.myphotocard.service;

import com.backtoback.point.myphotocard.domain.MyPhotoCard;
import com.backtoback.point.myphotocard.repository.MyPhotoCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MyPhotoCardServiceImpl implements MyPhotoCardService{
    private final MyPhotoCardRepository myphotocardRepository;
    @Override
    @Transactional
    public void createMyPhotocard(MyPhotoCard myPhotoCard){
        myphotocardRepository.save(myPhotoCard);
    }
}
