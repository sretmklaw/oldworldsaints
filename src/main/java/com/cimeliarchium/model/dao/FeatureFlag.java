package com.cimeliarchium.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * NOTE: Any updates to FeatureFlag IDs in the database must be accompanied by
 * an update to the static values enumerated in applications.properties, since
 * these references are hard-coded at various places throughout the application.
 */
@Entity
@Table(name = "feature_flag")
public class FeatureFlag {

	@Id
	@NotNull
	@Column(name = "feature_flag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long featureFlagId;

	@NotNull
	@Column(name = "feature_flag_name")
	private String featureFlagName;

	@NotNull
	@Column(name = "is_feature_flag_active")
	private Boolean isFeatureFlagActive;

	@Column(name = "feature_flag_value")
	private String featureFlagValue;

	public Long getFeatureFlagId() {
		return featureFlagId;
	}

	public void setFeatureFlagId(Long featureFlagId) {
		this.featureFlagId = featureFlagId;
	}

	public String getFeatureFlagName() {
		return featureFlagName;
	}

	public void setFeatureFlagName(String featureFlagName) {
		this.featureFlagName = featureFlagName;
	}

	public Boolean getIsFeatureFlagActive() {
		return isFeatureFlagActive;
	}

	public void setIsFeatureFlagActive(Boolean isFeatureFlagActive) {
		this.isFeatureFlagActive = isFeatureFlagActive;
	}

	public String getFeatureFlagValue() {
		return featureFlagValue;
	}

	public void setFeatureFlagValue(String featureFlagValue) {
		this.featureFlagValue = featureFlagValue;
	}

	@Override
	public String toString() {
		return "FeatureFlag [featureFlagId=" + featureFlagId + ", featureFlagName=" + featureFlagName
				+ ", isFeatureFlagActive=" + isFeatureFlagActive + ", featureFlagValue=" + featureFlagValue + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((featureFlagId == null) ? 0 : featureFlagId.hashCode());
		result = prime * result + ((featureFlagName == null) ? 0 : featureFlagName.hashCode());
		result = prime * result + ((featureFlagValue == null) ? 0 : featureFlagValue.hashCode());
		result = prime * result + ((isFeatureFlagActive == null) ? 0 : isFeatureFlagActive.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeatureFlag other = (FeatureFlag) obj;
		if (featureFlagId == null) {
			if (other.featureFlagId != null)
				return false;
		} else if (!featureFlagId.equals(other.featureFlagId))
			return false;
		if (featureFlagName == null) {
			if (other.featureFlagName != null)
				return false;
		} else if (!featureFlagName.equals(other.featureFlagName))
			return false;
		if (featureFlagValue == null) {
			if (other.featureFlagValue != null)
				return false;
		} else if (!featureFlagValue.equals(other.featureFlagValue))
			return false;
		if (isFeatureFlagActive == null) {
			if (other.isFeatureFlagActive != null)
				return false;
		} else if (!isFeatureFlagActive.equals(other.isFeatureFlagActive))
			return false;
		return true;
	}
}
