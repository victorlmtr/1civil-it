package com.backend.user.model.repository;

import com.backend.user.model.Entity.PictureCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureCardRepository extends JpaRepository <PictureCard, Integer> {
}
