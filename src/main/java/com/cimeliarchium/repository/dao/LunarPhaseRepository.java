package com.cimeliarchium.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cimeliarchium.model.dao.LunarPhase;

@Repository
public interface LunarPhaseRepository extends JpaRepository<LunarPhase, Long> {

}