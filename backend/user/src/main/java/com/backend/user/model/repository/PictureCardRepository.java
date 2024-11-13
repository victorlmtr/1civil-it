package com.backend.user.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.user.model.Entity.PictureCard;

@Repository
public interface PictureCardRepository extends JpaRepository<PictureCard, Integer> {

}
