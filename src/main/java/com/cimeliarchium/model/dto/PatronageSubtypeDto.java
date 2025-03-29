package com.cimeliarchium.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

import com.cimeliarchium.model.BaseEntity;
import com.cimeliarchium.model.dao.Patronage;
import com.cimeliarchium.model.dao.PatronageSubtype;

@Entity
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "PatronageSubtypeMappingQuery", 
		procedureName = "select_patronages_by_subtype", 
		resultClasses = { PatronageSubtypeDto.class })
	})
public class PatronageSubtypeDto extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patronage_subtype_dto_id")
	private Long patronageSubtypeDtoId;

	@Column(name = "patronage_subtype_id")
	private Long patronageSubtypeId;

	@Column(name = "patronage_type_id")
	private Long patronageTypeId;

	@Column(name = "patronage_subtype_name")
	private String patronageSubtypeName;

	@Column(name = "patronage_id")
	private Long patronageId;

	@Column(name = "patronage_name")
	private String patronageName;

	@Column(name = "related_search_link")
	private String relatedSearchLink;

	@Column(name = "patronage_point_x")
	private Double patronagePointX;

	@Column(name = "patronage_point_y")
	private Double patronagePointY;

	@Column(name = "_all_commemoration_count")
	private Long allCommemorationCount;

	@Column(name = "_catholic_commemoration_count")
	private Long catholicCommemorationCount;

	@Column(name = "_protestant_commemoration_count")
	private Long protestantCommemorationCount;

	@Column(name = "_orthodox_commemoration_count")
	private Long orthodoxCommemorationCount;

	@Column(name = "_sunni_commemoration_count")
	private Long sunniCommemorationCount;

	@Column(name = "_shiite_commemoration_count")
	private Long shiiteCommemorationCount;

	@Column(name = "_jewish_commemoration_count")
	private Long jewishCommemorationCount;

	public PatronageSubtype getPatronageSubtype() {
		return new PatronageSubtype.Builder()
				.withPatronageSubtypeId(patronageSubtypeId)
				.withPatronageTypeId(patronageTypeId)
				.withPatronageSubtypeName(patronageSubtypeName)
				.build();
	}

	public Patronage getPatronage() {
		return new Patronage.Builder()
				.withPatronageId(patronageId)
				.withPatronageSubtypeId(patronageSubtypeId)
				.withPatronageName(patronageName)
				.withPatronageSubtypeName(patronageSubtypeName)
				.withPatronageTypeId(patronageTypeId)
				.withRelatedSearchLink(relatedSearchLink)
				.withPointX(patronagePointX)
				.withPointY(patronagePointY)
				.withAllCommemorationCount(allCommemorationCount)
				.withCatholicCommemorationCount(catholicCommemorationCount)
				.withProtestantCommemorationCount(protestantCommemorationCount)
				.withOrthodoxCommemorationCount(orthodoxCommemorationCount)
				.withSunniCommemorationCount(sunniCommemorationCount)
				.withShiiteCommemorationCount(shiiteCommemorationCount)
				.withJewishCommemorationCount(jewishCommemorationCount)
				.build();
	}
}
