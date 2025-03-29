package com.cimeliarchium.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cimeliarchium.model.dao.Reading;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long> {

	public Reading findByReadingId(Long readingId);
}