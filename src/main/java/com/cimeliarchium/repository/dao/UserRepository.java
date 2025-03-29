package com.cimeliarchium.repository.dao;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimeliarchium.model.dao.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findByUsername(String username);

	User findByEmail(String email);

	User findByUserId(Long userId);

	@Modifying
	@Transactional
	@Query("update User u set "
			+ "u.lastLoginAttemptDate = :date, "
			+ "u.failedLoginCount = :count "
			+ "where u.userId = :id")
	int setLastLoginAttemptDateAndFailedLoginCountForUser(
			@NotNull @Param("date") Instant date, 
			@NotNull @Param("count") Long count, 
			@NotNull @Param("id") Long id);

	@Modifying
	@Transactional
	@Query("update User u set "
			+ "u.lastLoginSuccessDate = :date, "
			+ "u.lastLoginIp = :ip, "
			+ "u.totalLoginCount = :count "
			+ "where u.userId = :id")
	int setLastLoginSuccessDateAndLastLoginIpAndTotalLoginCountForUser(
			@NotNull @Param("date") Instant date, 
			@NotNull @Param("ip") String ip,
			@NotNull @Param("count") Long count, 
			@NotNull @Param("id") Long id);

	@Modifying
	@Transactional
	@Query("update User u set "
			+ "u.hasAdminLock = :lock, "
			+ "u.hasAdminLimit = :limit, "
			+ "u.failedLoginCount = :count "
			+ "where u.userId = :id " 
			+ "and u.hasAdminRole = false") // Never target Administrator
	int setHasAdminLockAndAdminLimitAndFailedLoginCountForUser(
			@NotNull @Param("lock") Boolean lock,
			@NotNull @Param("limit") Boolean limit,
			@NotNull @Param("count") Long count,
			@NotNull @Param("id") Long id);


	@Modifying
	@Transactional
	@Query("update User u set "
			+ "u.requestCount = :count "
			+ "where u.userId = :id")
	int setRequestCountForUser(
			@NotNull @Param("count") Long count,
			@NotNull @Param("id") Long id);

	@Modifying
	@Transactional
	@Query("update User u set "
			+ "u.hasClearedCache = false")
	int resetHasClearedCacheForAllUsers();

	@Modifying
	@Transactional
	@Query("update User u set "
			+ "u.hasClearedCache = :status "
			+ "where u.userId = :id")
	int setHasClearedCacheForUser(
			@NotNull @Param("status") Boolean status,
			@NotNull @Param("id") Long id);
}