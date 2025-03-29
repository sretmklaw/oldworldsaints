package com.cimeliarchium.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cimeliarchium.model.dao.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

	public Location findByLocationId(Long locationId);
}