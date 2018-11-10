package com.barclays.test.service;

import com.barclays.test.dto.CountryDto;

import java.util.List;

public interface VatService {

    public List<CountryDto> getAllCountries();

    public List<CountryDto> getCountriesWithLowestStandardVAT(int numberOfCountries);

    public List<CountryDto> getCountriesWithHighestStandardVAT(int numberOfCountries);
}
