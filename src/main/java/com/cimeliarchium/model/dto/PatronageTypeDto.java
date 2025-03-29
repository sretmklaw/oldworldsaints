package com.cimeliarchium.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Transient;

import com.cimeliarchium.model.BaseEntity;
import com.cimeliarchium.model.dao.PatronageSubtype;
import com.cimeliarchium.model.dao.PatronageType;

@Entity
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "PatronageTypeMappingQuery", 
		procedureName = "select_patronage_subtypes_by_type", 
		resultClasses = { PatronageTypeDto.class })
	})
public class PatronageTypeDto extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patronage_type_dto_id")
	private Long patronageTypeDtoId;

	@Column(name = "patronage_type_id")
	private Long patronageTypeId;

	@Column(name = "patronage_type_name")
	private String patronageTypeName;

	@Column(name = "patronage_subtype_id")
	private Long patronageSubtypeId;

	@Column(name = "patronage_subtype_name")
	private String patronageSubtypeName;

	@Transient
	private PatronageType patronageType;

	@Transient
	private PatronageSubtype patronageSubtype;

	public PatronageType getPatronageType() {
		return new PatronageType.Builder()
				.withPatronageTypeId(patronageTypeId)
				.withPatronageTypeName(patronageTypeName)
				.build();
	}

	public PatronageSubtype getPatronageSubtype() {
		return new PatronageSubtype.Builder()
				.withPatronageSubtypeId(patronageSubtypeId)
				.withPatronageTypeId(patronageTypeId)
				.withPatronageSubtypeName(patronageSubtypeName)
				.build();
	}
}
