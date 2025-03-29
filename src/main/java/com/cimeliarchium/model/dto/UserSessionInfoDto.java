package com.cimeliarchium.model.dto;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.validation.constraints.NotNull;

import com.cimeliarchium.model.BaseEntity;
import com.cimeliarchium.model.dao.Calendar;
import com.cimeliarchium.model.dao.Nation;
import com.cimeliarchium.model.dao.Region;
import com.cimeliarchium.model.dao.Rite;
import com.cimeliarchium.model.dao.User;

@Entity
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "UserSessionInfoMappingQuery", 
		procedureName = "select_session_info_by_user", 
		resultClasses = { UserSessionInfoDto.class },
		parameters = { 
			@StoredProcedureParameter(
				mode = ParameterMode.IN, 
				name = "_user_id", 
				type = Long.class)
		}),
	@NamedStoredProcedureQuery(
			name = "UserSessionInfoAdminMappingQuery", 
			procedureName = "select_user_session_info", 
			resultClasses = { UserSessionInfoDto.class })
	})
public class UserSessionInfoDto extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_session_info_dto_id")
	private String userSessionInfoDtoId;

	@Column(name = "user_id")
	private Long userId;

	@NotNull
	@Column(name = "username")
	private String username;

	@Column(name = "email")
	private String email;

	@Column(name = "calendar_id")
	private Long calendarId;

	@Column(name = "calendar_code")
	private String calendarCode;

	@Column(name = "calendar_name")
	private String calendarName;

	@Column(name = "calendar_detail")
	private String calendarDetail;

	@Column(name = "rite_id")
	private Long riteId;

	@Column(name = "rite_code")
	private String riteCode;

	@Column(name = "rite_name")
	private String riteName;

	@Column(name = "rite_detail")
	private String riteDetail;

	@Column(name = "nation_id")
	private Long nationId;

	@Column(name = "nation_code")
	private String nationCode;

	@Column(name = "nation_name")
	private String nationName;

	@Column(name = "region_id")
	private Long regionId;

	@Column(name = "region_code")
	private String regionCode;

	@Column(name = "region_name")
	private String regionName;

	@Column(name = "region_timezone_offset")
	private String regionTimezoneOffset;

	@Column(name = "creation_date")
	private Instant creationDate;

	@Column(name = "last_login_attempt_date")
	private Instant lastLoginAttemptDate;

	@Column(name = "failed_login_count")
	private Long failedLoginCount;

	@Column(name = "last_login_success_date")
	private Instant lastLoginSuccessDate;

	@Column(name = "total_login_count")
	private Long totalLoginCount;

	@Column(name = "last_login_ip")
	private String lastLoginIp;

	@Column(name = "has_admin_role")
	private Boolean hasAdminRole;

	@Column(name = "has_admin_lock")
	private Boolean hasAdminLock;

	@Column(name = "has_admin_limit")
	private Boolean hasAdminLimit;

	@Column(name = "has_cleared_cache")
	private Boolean hasClearedCache;

	@Column(name = "request_count")
	private Long requestCount;

	public User getUser() {
		return new User.Builder()
				.withUserId(userId)
				.withUsername(username)
				.withEmail(email)
				.withCalendarId(calendarId)
				.withRiteId(riteId)
				.withNationId(nationId)
				.withRegionId(regionId)
				.withCreationDate(creationDate)
				.withLastLoginAttemptDate(lastLoginAttemptDate)
				.withFailedLoginCount(failedLoginCount)
				.withLastLoginSuccessDate(lastLoginSuccessDate)
				.withTotalLoginCount(totalLoginCount)
				.withLastLoginIp(lastLoginIp)
				.withHasAdminRole(hasAdminRole)
				.withHasAdminLock(hasAdminLock)
				.withHasAdminLimit(hasAdminLimit)
				.withHasClearedCache(hasClearedCache)
				.withRequestCount(requestCount)
				.build();
	}

	public Calendar getCalendar() {
		return new Calendar.Builder()
				.withCalendarId(calendarId)
				.withCalendarName(calendarName)
				.withCalendarDetail(calendarDetail)
				.withCalendarCode(calendarCode)
				.build();
	}

	public Rite getRite() {
		return new Rite.Builder()
				.withRiteId(riteId)
				.withRiteName(riteName)
				.withRiteDetail(riteDetail)
				.withRiteCode(riteCode)
				.withCalendarName(calendarName)
				.withCalendarId(calendarId)
				.build();
	}

	public Nation getNation() {
		return new Nation.Builder()
				.withNationId(nationId)
				.withNationName(nationName)
				.withNationCode(nationCode)
				.build();
	}

	public Region getRegion() {
		return new Region.Builder()
				.withRegionId(regionId)
				.withRegionName(regionName)
				.withRegionCode(regionCode)
				.withUtcOffset(regionTimezoneOffset)
				.build();
	}
}
