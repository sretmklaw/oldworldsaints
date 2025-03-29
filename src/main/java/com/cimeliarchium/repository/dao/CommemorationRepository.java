package com.cimeliarchium.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cimeliarchium.model.dao.Commemoration;

@Repository
public interface CommemorationRepository extends JpaRepository<Commemoration, Long> {

	public Commemoration findByCommemorationId(Long commemorationId);
}