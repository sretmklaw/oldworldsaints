package com.cimeliarchium.model.dao;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.cimeliarchium.model.BaseEntity;

@Entity
@Table(name = "commemoration_type")
public class CommemorationType extends BaseEntity implements Comparable<CommemorationType> {

	@Id
	@NotNull
	@Column(name = "commemoration_type_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commemorationTypeId;

	@Column(name = "commemoration_type_name")
	private String commemorationTypeName;

	@Column(name = "commemoration_type_name_alt")
	private String commemorationTypeNameAlt;

	private CommemorationType() {}

	public static class Builder {

		private Long commemorationTypeId;
		private String commemorationTypeName;
		private String commemorationTypeNameAlt;

		public Builder withCommemorationTypeId(Long commemorationTypeId) {
			this.commemorationTypeId = commemorationTypeId;
			return this;
		}

		public Builder withCommemorationTypeName(String commemorationTypeName) {
			this.commemorationTypeName = commemorationTypeName;
			return this;
		}

		public Builder withCommemorationTypeNameAlt(String commemorationTypeNameAlt) {
			this.commemorationTypeNameAlt = commemorationTypeNameAlt;
			return this;
		}

		public CommemorationType build() {
			CommemorationType commemorationType = new CommemorationType();
			commemorationType.commemorationTypeId = this.commemorationTypeId;
			commemorationType.commemorationTypeName = this.commemorationTypeName;
			commemorationType.commemorationTypeNameAlt = this.commemorationTypeNameAlt;
			return commemorationType;
		}
	}

	public Long getCommemorationTypeId() {
		return commemorationTypeId;
	}

	public String getCommemorationTypeName() {
		return commemorationTypeName;
	}

	public String getCommemorationTypeNameAlt() {
		return commemorationTypeNameAlt;
	}

	/**
	 * Method used to display alternate Commemoration Type when the user's Rite is 'Tridentine Catholic'.
	 * 
	 * @param showAll
	 * @param userRiteId
	 * @param creedId
	 * @return
	 */
	public String getCommemorationTypeNameFormatted(Long userRiteId) {
		if (userRiteId == RITE_TRIDENTINE_ID) {
			return commemorationTypeNameAlt;
		} else {
			return commemorationTypeName;
		}
	}

	@Override
	public String toString() {
		return "CommemorationType [commemorationTypeId=" + commemorationTypeId + ", commemorationTypeName="
				+ commemorationTypeName + ", commemorationTypeNameAlt=" + commemorationTypeNameAlt + "]";
	}

	@Override
	public int compareTo(CommemorationType other) {
		return Comparator
				.comparing(CommemorationType::getCommemorationTypeName, Comparator.nullsFirst(Comparator.naturalOrder()))
				.compare(this, other);
	}
}
