package com.cimeliarchium.model.dao;

import java.io.Serializable;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.cimeliarchium.enums.StatusCodeEnum;
import com.cimeliarchium.model.BaseEntity;

@Entity
@Table(name = "\"user\"", 
	uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5923933933367931852L;

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern("MM/dd/yy HH:mmX", Locale.US)
			.withZone(BaseEntity.ZONE_ID);

	@Id
	@NotNull
	@Column(name = "user_id")
	@GenericGenerator(name = "AppIdentityGenerator", strategy = "com.cimeliarchium.config.AppIdentityGenerator")
	@GeneratedValue(generator = "AppIdentityGenerator")
	private Long userId;

	@NotNull
	@Column(name = "username")
	private String username;

	@NotNull
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "password")
	private String password;

	@Transient
	private String passwordConfirm;

	@Transient
	private String oldPassword;

	@Column(name = "calendar_id")
	private Long calendarId;

	@Column(name = "rite_id")
	private Long riteId;

	@Column(name = "nation_id")
	private Long nationId;

	@Column(name = "region_id")
	private Long regionId;

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

	@Column(name = "status_code")
	private String statusCode;

	@Column(name = "captcha")
	private String captcha;

	private User() {}

	public static class Builder {

		private Long userId;
		private String username;
		private String email;
		private Long calendarId;
		private Long riteId;
		private Long nationId;
		private Long regionId;
		private Instant creationDate;
		private Instant lastLoginAttemptDate;
		private Long failedLoginCount;
		private Instant lastLoginSuccessDate;
		private Long totalLoginCount;
		private String lastLoginIp;
		private Boolean hasAdminRole;
		private Boolean hasAdminLock;
		private Boolean hasAdminLimit;
		private Boolean hasClearedCache;
		private Long requestCount;
		private String statusCode;
		private String captcha;

		public Builder withUserId (Long userId){
			this.userId = userId;
			return this;
		}

		public Builder withUsername (String username){
			this.username = username;
			return this;
		}

		public Builder withEmail (String email){
			this.email = email;
			return this;
		}

		public Builder withCalendarId (Long calendarId){
			this.calendarId = calendarId;
			return this;
		}

		public Builder withRiteId (Long riteId){
			this.riteId = riteId;
			return this;
		}

		public Builder withNationId (Long nationId){
			this.nationId = nationId;
			return this;
		}

		public Builder withRegionId (Long regionId){
			this.regionId = regionId;
			return this;
		}

		public Builder withCreationDate (Instant creationDate){
			this.creationDate = creationDate;
			return this;
		}

		public Builder withLastLoginAttemptDate (Instant lastLoginAttemptDate){
			this.lastLoginAttemptDate = lastLoginAttemptDate;
			return this;
		}

		public Builder withFailedLoginCount (Long failedLoginCount){
			this.failedLoginCount = failedLoginCount;
			return this;
		}

		public Builder withLastLoginSuccessDate (Instant lastLoginSuccessDate){
			this.lastLoginSuccessDate = lastLoginSuccessDate;
			return this;
		}

		public Builder withTotalLoginCount (Long totalLoginCount){
			this.totalLoginCount = totalLoginCount;
			return this;
		}

		public Builder withLastLoginIp (String lastLoginIp){
			this.lastLoginIp = lastLoginIp;
			return this;
		}

		public Builder withHasAdminRole (Boolean hasAdminRole){
			this.hasAdminRole = hasAdminRole;
			return this;
		}

		public Builder withHasAdminLock (Boolean hasAdminLock){
			this.hasAdminLock = hasAdminLock;
			return this;
		}

		public Builder withHasAdminLimit (Boolean hasAdminLimit){
			this.hasAdminLimit = hasAdminLimit;
			return this;
		}

		public Builder withHasClearedCache (Boolean hasClearedCache){
			this.hasClearedCache = hasClearedCache;
			return this;
		}

		public Builder withRequestCount (Long requestCount){
			this.requestCount = requestCount;
			return this;
		}

		public Builder withStatusCode (String statusCode){
			this.statusCode = statusCode;
			return this;
		}

		public Builder withCaptcha (String captcha){
			this.captcha = captcha;
			return this;
		}

		public User build() {
			User user = new User();
			user.userId = this.userId;
			user.username = this.username;
			user.email = this.email;
			user.calendarId = this.calendarId;
			user.riteId = this.riteId;
			user.nationId = this.nationId;
			user.regionId = this.regionId;
			user.creationDate = this.creationDate;
			user.lastLoginAttemptDate = this.lastLoginAttemptDate;
			user.failedLoginCount = this.failedLoginCount;
			user.lastLoginSuccessDate = this.lastLoginSuccessDate;
			user.totalLoginCount = this.totalLoginCount;
			user.lastLoginIp = this.lastLoginIp;
			user.hasAdminRole = this.hasAdminRole;
			user.hasAdminLock = this.hasAdminLock;
			user.hasAdminLimit = this.hasAdminLimit;
			user.hasClearedCache = this.hasClearedCache;
			user.requestCount = this.requestCount;
			user.statusCode = this.statusCode;
			user.captcha = this.captcha;
			return user;
		}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Long getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(Long calendarId) {
		this.calendarId = calendarId;
	}

	public Long getRiteId() {
		return riteId;
	}

	public void setRiteId(Long riteId) {
		this.riteId = riteId;
	}

	public Long getNationId() {
		return nationId;
	}

	public void setNationId(Long nationId) {
		this.nationId = nationId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public Long setRegionId(Long regionId) {
		this.regionId = regionId;
		return this.regionId;
	}

	public Instant getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Instant creationDate) {
		this.creationDate = creationDate;
	}

	public Instant getLastLoginAttemptDate() {
		return lastLoginAttemptDate;
	}

	public void setLastLoginAttemptDate(Instant lastLoginAttemptDate) {
		this.lastLoginAttemptDate = lastLoginAttemptDate;
	}

	public Long getFailedLoginCount() {
		return failedLoginCount;
	}

	public void setFailedLoginCount(Long failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	public Instant getLastLoginSuccessDate() {
		return lastLoginSuccessDate;
	}

	public String getLastLoginSuccessDateFormatted() {
		return (lastLoginSuccessDate != null) 
				? DATE_TIME_FORMATTER.format(lastLoginSuccessDate) 
				: "Never";
	}

	public void setLastLoginSuccessDate(Instant lastLoginSuccessDate) {
		this.lastLoginSuccessDate = lastLoginSuccessDate;
	}

	public Long getTotalLoginCount() {
		return totalLoginCount;
	}

	public void setTotalLoginCount(Long totalLoginCount) {
		this.totalLoginCount = totalLoginCount;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Boolean getHasAdminRole() {
		return hasAdminRole;
	}

	public void setHasAdminRole(Boolean hasAdminRole) {
		this.hasAdminRole = hasAdminRole;
	}

	public Boolean getHasAdminLock() {
		return hasAdminLock;
	}

	public void setHasAdminLock(Boolean hasAdminLock) {
		this.hasAdminLock = hasAdminLock;
	}

	public Boolean getHasAdminLimit() {
		return hasAdminLimit;
	}

	public void setHasAdminLimit(Boolean hasAdminLimit) {
		this.hasAdminLimit = hasAdminLimit;
	}

	public Boolean getHasClearedCache() {
		return (hasClearedCache != null) ? hasClearedCache : true;
	}

	public void setHasClearedCache(Boolean hasClearedCache) {
		this.hasClearedCache = hasClearedCache;
	}

	public Long getRequestCount() {
		return requestCount;
	}

	public User setRequestCount(Long requestCount) {
		this.requestCount = requestCount;
		return this;
	}

	/**
	 * Method used to determine status code by checking count of failed logins
	 * against the current time
	 * 
	 * @return statusCode
	 */
	public String getStatusCode() {

		final Instant targetDate = Instant.now();
		if (this.hasAdminLock) {
			return StatusCodeEnum.RED.getValue();
		} else if (this.hasAdminLimit 
				|| (this.failedLoginCount > 3 
						&& this.lastLoginAttemptDate
							.plusSeconds(6000L)
							.isAfter(targetDate))) {
			return StatusCodeEnum.YELLOW.getValue();
		} else {
			return StatusCodeEnum.GREEN.getValue();
		}
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", email=" + email + ", calendarId=" + calendarId
				+ ", riteId=" + riteId + ", nationId=" + nationId + ", regionId=" + regionId + ", creationDate="
				+ creationDate + ", lastLoginAttemptDate=" + lastLoginAttemptDate + ", failedLoginCount="
				+ failedLoginCount + ", lastLoginSuccessDate=" + lastLoginSuccessDate + ", totalLoginCount="
				+ totalLoginCount + ", lastLoginIp=" + lastLoginIp + ", hasAdminRole=" + hasAdminRole
				+ ", hasAdminLock=" + hasAdminLock + ", hasAdminLimit=" + hasAdminLimit + ", hasClearedCache="
				+ hasClearedCache + ", requestCount=" + requestCount + ", statusCode=" + statusCode + ", captcha="
				+ captcha + "]";
	}
}