package com.cimeliarchium.service.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.ValidationEnum;
import com.cimeliarchium.model.dao.SearchCriteria;
import com.cimeliarchium.service.helper.ValidationHelperService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class SearchCriteriaValidationService extends ValidationHelperService
		implements Validator {

	private static final Integer MIN_LENGTH = 3;
	private static final Integer MAX_LENGTH = 24;
	private static final String ALPHABETIC_LIMITED_SYMBOLS_REGEX = "[a-zA-Z0-9 \\/',.\\-]+";
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
	private static final ZoneId ZONE_ID = ZoneId.of("UTC");
	
	@Override
	public boolean supports(Class<?> aClass) {

		return SearchCriteria.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final SearchCriteria searchCriteria = (SearchCriteria) target;
		if (searchCriteria == null) {
			return;
		}
		if (searchCriteria.getById() != null) {
			this.validateById(searchCriteria, errors);
		}
		else if (searchCriteria.getByValue() != null) {
			this.validateByValue(searchCriteria, errors);
		}
		else if (searchCriteria.getByDate() != null) {
			this.validateByDate(searchCriteria, errors);
		}
		else {
			this.validateByText(searchCriteria, errors);
		}
	}

	public void validateById(SearchCriteria searchCriteria, Errors errors) {
		super.isRejectedAsImproperFormatForNumericIdAndRequired(
				String.valueOf(searchCriteria.getById()), 
				ModelAttributeEnum.BY_ID.getValue(), 
				errors);
	}

	public Boolean isValidId(Long byId) {
		return super.isValidIdInput(byId);
	}

	public void validateByValue(SearchCriteria searchCriteria, Errors errors) {
		final String byValue = String.valueOf(searchCriteria.getByValue());
		if (byValue != null && !byValue.equals("") && !byValue.equals("true") && !byValue.equals("false")) {
			errors.rejectValue(ModelAttributeEnum.BY_VALUE.getValue(), ValidationEnum.IMPROPER_FORMAT.getValue());
		}
	}

	public void validateByDate(SearchCriteria searchCriteria, Errors errors) {
		if (!this.isValidDate(searchCriteria.getByDate())) {
			errors.rejectValue(ModelAttributeEnum.BY_DATE.getValue(), ValidationEnum.IMPROPER_FORMAT.getValue());
		}
	}

	public Boolean isValidDate(Date byDate) {

		final Date startDate = Date.from(LocalDate.parse(dateSearchStartDate, DATE_FORMAT)
				.atStartOfDay(ZONE_ID)
				.toInstant());
		final Date endDate = Date.from(LocalDate.parse(dateSearchEndDate, DATE_FORMAT)
				.atStartOfDay(ZONE_ID)
				.toInstant());
		if (byDate != null && (byDate.before(startDate) || byDate.after(endDate))) {
			return false;
		}
		return true;
	}

	public void validateByText(SearchCriteria searchCriteria, Errors errors) {
		super.isRejectedAsImproperFormatWhereNotNull(
				searchCriteria.getByText(), 
				ModelAttributeEnum.BY_TEXT.getValue(), 
				MIN_LENGTH, 
				MAX_LENGTH, 
				ALPHABETIC_LIMITED_SYMBOLS_REGEX, 
				errors);
	}

	public Boolean isValidText(String byText) {
		return super.isValidInput(
				byText, 
				MIN_LENGTH, 
				MAX_LENGTH, 
				ALPHABETIC_LIMITED_SYMBOLS_REGEX);
	}

	@Value("${datesearch.start.date}")
	private String dateSearchStartDate;

	@Value("${datesearch.end.date}")
	private String dateSearchEndDate;
}
