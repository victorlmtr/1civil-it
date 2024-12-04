package com.backend.user.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.user.model.Entity.City;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    Optional<City> findByPostcodeAndInseecode(String postcode, String inseecode);

}
