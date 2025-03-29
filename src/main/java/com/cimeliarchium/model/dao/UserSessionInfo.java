package com.cimeliarchium.model.dao;

public class UserSessionInfo {

	private User user;

	private Calendar calendar;

	private Rite rite;

	private Nation nation;

	private Region region;

	private Boolean showNotification = true;

	private UserSessionInfo() {}

	public static class Builder {

		private User user;
		private Calendar calendar;
		private Rite rite;
		private Nation nation;
		private Region region;

		public Builder withUser(User user) {
			this.user = user;
			return this;
		}

		public Builder withCalendar(Calendar calendar) {
			this.calendar = calendar;
			return this;
		}

		public Builder withRite(Rite rite) {
			this.rite = rite;
			return this;
		}

		public Builder withNation(Nation nation) {
			this.nation = nation;
			return this;
		}

		public Builder withRegion(Region region) {
			this.region = region;
			return this;
		}

		public UserSessionInfo build() {
			UserSessionInfo userSessionInfo = new UserSessionInfo();
			userSessionInfo.user = this.user;
			userSessionInfo.calendar = this.calendar;
			userSessionInfo.rite = this.rite;
			userSessionInfo.nation = this.nation;
			userSessionInfo.region = this.region;
			return userSessionInfo;
		}
	}

	public User getUser() {
		return user;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public Rite getRite() {
		return rite;
	}

	public Nation getNation() {
		return nation;
	}

	public Region getRegion() {
		return region;
	}

	public Boolean getShowNotification() {
		return showNotification;
	}

	public void setShowNotification(Boolean showNotification) {
		this.showNotification = showNotification;
	}
}
