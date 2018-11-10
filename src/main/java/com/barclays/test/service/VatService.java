package com.barclays.test.service;

import com.barclays.test.dto.CountryDto;

import java.util.List;

public interface VatService {

    /**
     * It returns all countries.
     *
     * @return all countries
     */
    List<CountryDto> getAllCountries();

    /**
     * It returns countries with the lowest standard VAT, number of countries returned is limited by {@code numberOfCountries}.
     *
     * @param numberOfCountries number of countries in the result
     * @return countries with the lowest standard VAT
     */
    List<CountryDto> getCountriesWithLowestStandardVAT(int numberOfCountries);

    /**
     * It returns countries with the highest standard VAT, number of countries returned is limited by {@code numberOfCountries}.
     *
     * @param numberOfCountries number of countries in the result
     * @return countries with the highest standard VAT
     */
    List<CountryDto> getCountriesWithHighestStandardVAT(int numberOfCountries);
}
