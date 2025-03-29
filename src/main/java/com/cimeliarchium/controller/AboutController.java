package com.cimeliarchium.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cimeliarchium.enums.AboutEnum;
import com.cimeliarchium.enums.CalendarRiteEnum;
import com.cimeliarchium.enums.HourTypeEnum;
import com.cimeliarchium.enums.InputFieldEnum;
import com.cimeliarchium.enums.LocationTypeEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.ReferenceTitleEnum;
import com.cimeliarchium.enums.StarTypeEnum;
import com.cimeliarchium.enums.ZodiacSignEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.service.dao.SearchCriteriaValidationService;

@Controller
public class AboutController {

	private SearchCriteriaValidationService searchCriteriaValidationService;

	@Autowired
	public AboutController(SearchCriteriaValidationService searchCriteriaValidationService) {
		this.searchCriteriaValidationService = searchCriteriaValidationService;
	}

	/**
	 * Method used to generate model and view for application overview
	 * 
	 * @param request
	 *            the HttpServletRequest
	 * @param response
	 *            the HttpServletResponse
	 * @return ModelAndView
	 * @throws AppException 
	 */
	@RequestMapping("/about")
	public ModelAndView displayAboutInfo(
			@RequestParam(value = "byText", required = false) String byText,
			HttpServletRequest request, 
			HttpServletResponse response) throws AppException {

		// Initialize the Model and View
		ModelAndView mav = new ModelAndView(ModelAttributeEnum.ABOUT_VIEW.getValue());

		// Determine section to display, where applicable, otherwise default to Paragraph 1
		if (byText != null && searchCriteriaValidationService.isValidText(byText)) {
			mav.addObject("activeParagraph", byText);
		}

		// Add static text fields to Model for display on the View
		mav.addObject("headingMap", AboutEnum.getAboutHeadingMap());
		mav.addObject("paragraphMap", AboutEnum.getAboutParagraphMap());
		mav.addObject("howToUseSubHeadingsMap", AboutEnum.getHowToUseSubHeadingMap());
		mav.addObject("whyWrittenThisWayParagraphList", AboutEnum.getWhyWrittenThisWayParagraphs());
		mav.addObject("howToContributeParagraphList", AboutEnum.getHowtoContributeParagraphs());
		mav.addObject("versionReleaseNotes", AboutEnum.VERSION_RELEASE_NOTES);

		mav.addObject("calendarRiteList", CalendarRiteEnum.getCalendarRites());
		mav.addObject("starTypeList", StarTypeEnum.getStarTypes());
		mav.addObject("standardLocationTypeList", LocationTypeEnum.getStandardLocationTypes());
		mav.addObject("specialLocationTypeList", LocationTypeEnum.getSpecialLocationTypes());
		mav.addObject("registerInputFieldList", InputFieldEnum.getRegisterInputFields());
		mav.addObject("zodiacSignList", ZodiacSignEnum.getZodiacSigns());
		mav.addObject("hourTypeList", HourTypeEnum.getHourTypes());
		mav.addObject("searchInputFieldList", InputFieldEnum.getSearchInputFields());
		mav.addObject("printReferenceList", ReferenceTitleEnum.getPrintReferenceTitles());
		mav.addObject("webReferenceMap", ReferenceTitleEnum.getWebReferences());
		return mav;
	}

}