package com.cimeliarchium.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cimeliarchium.model.dao.Inset;

@Repository
public interface InsetRepository extends JpaRepository<Inset, Long> {}