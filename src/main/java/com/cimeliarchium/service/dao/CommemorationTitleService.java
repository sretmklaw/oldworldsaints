package com.cimeliarchium.service.dao;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cimeliarchium.enums.CommemorationTitleEnum;
import com.cimeliarchium.model.dao.SearchCriteria;

@Service
public class CommemorationTitleService {

	private static final List<SearchCriteria> CHRISTIAN_TITLES = CommemorationTitleEnum.getChristianTitles().stream()
			.map(e -> new SearchCriteria.Builder()
					.withByText(e.getValue())
					.build())
			.collect(Collectors.toList());

	private static final List<SearchCriteria> ISLAMIC_TITLES = CommemorationTitleEnum.getIslamicTitles().stream()
			.map(e -> new SearchCriteria.Builder()
					.withByText(e.getValue())
					.build())
			.collect(Collectors.toList());

	private static final List<SearchCriteria> JEWISH_TITLES = CommemorationTitleEnum.getJewishTitles().stream()
			.map(e -> new SearchCriteria.Builder()
					.withByText(e.getValue())
					.build())
			.collect(Collectors.toList());

	public static final List<SearchCriteria> ALL_TITLES = Arrays.asList(CommemorationTitleEnum.values()).stream()
			.map(e -> new SearchCriteria.Builder()
					.withByText(((CommemorationTitleEnum) e).getValue())
					.build())
			.sorted((e1,e2) -> e1.getByText().compareTo(e2.getByText()))
			.collect(Collectors.toList());

	public List<SearchCriteria> lookupById(Long creedId) {

		if (creedId == null) {
			return ALL_TITLES;
		} else {
			switch (creedId.intValue()) {
				// Jewish Creed
				case 11 : return JEWISH_TITLES;
				// Islamic Creeds
				case 10 : case 9 : case 8 : return ISLAMIC_TITLES;
				// Christian Creeds
				case 7 : case 6 : case 5 : case 4 : case 3 : return CHRISTIAN_TITLES;
				// All Titles
				default : return ALL_TITLES;
			}
		}
	}
}
