package com.barclays.test.service;

import com.barclays.test.dto.CountryDto;

import java.util.List;

public interface VatService {

    List<CountryDto> getAllCountries();

    List<CountryDto> getCountriesWithLowestStandardVAT(int numberOfCountries);

    List<CountryDto> getCountriesWithHighestStandardVAT(int numberOfCountries);
}
