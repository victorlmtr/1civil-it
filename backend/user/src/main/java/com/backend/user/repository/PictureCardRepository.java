package com.backend.user.repository;

import com.backend.user.model.Picturecard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureCardRepository extends JpaRepository <Picturecard, Integer> {
}
