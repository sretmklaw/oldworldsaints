package com.cimeliarchium.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cimeliarchium.model.dao.Reference;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, String> {}