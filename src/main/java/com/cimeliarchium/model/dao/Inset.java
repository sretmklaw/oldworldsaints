package com.cimeliarchium.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

@Entity
@Table(name = "inset")
public class Inset {

	@Id
	@NotNull
	@Column(name = "inset_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long insetId;

	@Column(name = "inset_name")
	private String insetName;

	@Column(name = "inset_x")
	private Double insetX;

	@Column(name = "inset_y")
	private Double insetY;

	@Column(name = "inset_bounds_x1")
	private Double insetBoundsX1;

	@Column(name = "inset_bounds_y1")
	private Double insetBoundsY1;

	@Column(name = "inset_bounds_x2")
	private Double insetBoundsX2;

	@Column(name = "inset_bounds_y2")
	private Double insetBoundsY2;

	@Column(name = "nation_id")
	private Long nationId;

	@Column(name = "min_zoom")
	private Double minZoom;

	@Column(name = "max_zoom")
	private Double maxZoom;

	private Inset() {}

	public static class Builder {

		private Long insetId;
		private String insetName;
		private Double insetX;
		private Double insetY;
		private Double insetBoundsX1;
		private Double insetBoundsY1;
		private Double insetBoundsX2;
		private Double insetBoundsY2;
		private Long nationId;
		private Double minZoom;
		private Double maxZoom;

		public Builder withInsetId(Long insetId) {
			this.insetId = insetId;
			return this;
		}

		public Builder withInsetName (String insetName) {
			this.insetName = insetName;
			return this;
		}

		public Builder withInsetX (Double insetX) {
			this.insetX = insetX;
			return this;
		}

		public Builder withInsetY (Double insetY) {
			this.insetY = insetY;
			return this;
		}

		public Builder withInsetBoundsX1 (Double insetBoundsX1) {
			this.insetBoundsX1 = insetBoundsX1;
			return this;
		}

		public Builder withInsetBoundsY1 (Double insetBoundsY1) {
			this.insetBoundsY1 = insetBoundsY1;
			return this;
		}

		public Builder withInsetBoundsX2 (Double insetBoundsX2) {
			this.insetBoundsX2 = insetBoundsX2;
			return this;
		}

		public Builder withInsetBoundsY2 (Double insetBoundsY2) {
			this.insetBoundsY2 = insetBoundsY2;
			return this;
		}

		public Builder withNationId (Long nationId) {
			this.nationId = nationId;
			return this;
		}

		public Builder withMinZoom (Double minZoom) {
			this.minZoom = minZoom;
			return this;
		}

		public Builder withMaxZoom (Double maxZoom) {
			this.maxZoom = maxZoom;
			return this;
		}

		public Inset build() {
			Inset inset = new Inset();
			inset.insetId = this.insetId;
			inset.insetName = this.insetName;
			inset.insetX = this.insetX;
			inset.insetY = this.insetY;
			inset.insetBoundsX1 = this.insetBoundsX1;
			inset.insetBoundsY1 = this.insetBoundsY1;
			inset.insetBoundsX2 = this.insetBoundsX2;
			inset.insetBoundsY2 = this.insetBoundsY2;
			inset.nationId = this.nationId;
			inset.minZoom = this.minZoom;
			inset.maxZoom = this.maxZoom;
			return inset;
		}
	}

	public Long getInsetId() {
		return insetId;
	}

	public String getInsetName() {
		return insetName;
	}

	public String getInsetNameCapitalized() {
		return StringUtils.capitalize(insetName);
	}

	public Double getInsetX() {
		return insetX;
	}

	public Double getInsetY() {
		return insetY;
	}

	public Double getInsetBoundsX1() {
		return insetBoundsX1;
	}

	public Double getInsetBoundsY1() {
		return insetBoundsY1;
	}

	public Double getInsetBoundsX2() {
		return insetBoundsX2;
	}

	public Double getInsetBoundsY2() {
		return insetBoundsY2;
	}

	public Long getNationId() {
		return nationId;
	}

	public Double getMinZoom() {
		return minZoom;
	}

	public Double getMaxZoom() {
		return maxZoom;
	}

	@Override
	public String toString() {
		return "Inset [insetId=" + insetId + ", insetName=" + insetName + ", insetX=" + insetX + ", insetY=" + insetY
				+ ", insetBoundsX1=" + insetBoundsX1 + ", insetBoundsY1=" + insetBoundsY1 + ", insetBoundsX2="
				+ insetBoundsX2 + ", insetBoundsY2=" + insetBoundsY2 + ", nationId=" + nationId + ", minZoom=" + minZoom
				+ ", maxZoom=" + maxZoom + "]";
	}
}