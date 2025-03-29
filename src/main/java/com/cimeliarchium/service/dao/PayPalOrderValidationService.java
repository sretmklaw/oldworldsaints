package com.cimeliarchium.service.dao;

import java.text.DecimalFormat;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cimeliarchium.enums.ValidationEnum;
import com.cimeliarchium.model.dao.PayPalOrder;
import com.cimeliarchium.service.helper.ValidationHelperService;

@Service
public class PayPalOrderValidationService 
		extends ValidationHelperService
		implements Validator {

	private static final DecimalFormat CURRENCY_FORMATTER = new DecimalFormat("0.00");
	private static final String NUMERIC_DOUBLE_REGEX = "[0-9]*.[0-9]{0,2}";

	@Override
	public boolean supports(Class<?> aClass) {
		return PayPalOrder.class.equals(aClass);
	}

	@Override
	
	public void validate(Object target, Errors errors) {
		final PayPalOrder paypalOrder = (PayPalOrder) target;
		if (paypalOrder == null) {
			return;
		}
		final String amount = paypalOrder.getAmount();
		if (paypalOrder.getAmount() != null) {
			if (!amount.matches(NUMERIC_DOUBLE_REGEX)) {
				errors.rejectValue("amount", ValidationEnum.IMPROPER_FORMAT.getValue());
			} else {
				// Trim leading zeros and right-pad zeros on cents
				String formattedAmount = CURRENCY_FORMATTER.format(Double.valueOf(amount));
				paypalOrder.setAmount(formattedAmount);
			}
		}
	}

	public Boolean beforeProcessHasErrors(Object target, Errors errors) {
		this.validate(target, errors);
		return errors.hasErrors();
	}
}
