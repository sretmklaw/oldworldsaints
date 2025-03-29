package com.cimeliarchium.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cimeliarchium.model.dao.Patronage;

@Repository
public interface PatronageRepository extends JpaRepository<Patronage, Long> {

	public Patronage findByPatronageId(Long patronageId);
}