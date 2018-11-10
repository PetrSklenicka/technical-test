package com.barclays.test.service;

import com.barclays.test.pojo.CountriesVATsPojo;
import com.barclays.test.dto.CountryDto;
import com.barclays.test.pojo.PeriodPojo;
import com.barclays.test.pojo.RatePojo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VatServiceImpl implements VatService {

    private RestTemplate restTemplate = new RestTemplate();

    public List<CountryDto> getAllCountries() {
        CountriesVATsPojo countriesVATsPojo = restTemplate.getForObject("http://jsonvat.com/", CountriesVATsPojo.class);
        return countriesVATsPojo
                .getRates()
                .stream()
                .map(this::mapRateToCountryDto)
                .collect(Collectors.toList());
    }

    public List<CountryDto> getCountriesWithLowestStandardVAT(int numberOfCountries) {
        return getAllCountries()
                .stream()
                .sorted(Comparator.comparing(CountryDto::getStandardVAT))
                .limit(numberOfCountries)
                .collect(Collectors.toList());
    }

    public List<CountryDto> getCountriesWithHighestStandardVAT(int numberOfCountries) {
        return getAllCountries()
                .stream()
                .sorted(Comparator.comparing(CountryDto::getStandardVAT).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    private CountryDto mapRateToCountryDto(RatePojo rate) {
        PeriodPojo currentPeriod = getCurrentPeriodFromRate(rate);
        return new CountryDto(rate.getName(), rate.getCode(), currentPeriod.getRates().getStandard());
    }

    private PeriodPojo getCurrentPeriodFromRate(RatePojo rate) {
        return rate
                .getPeriods()
                .stream()
                .max(Comparator.comparing(PeriodPojo::getEffectiveFrom))
                .orElseThrow(() -> new RuntimeException(String.format("Cannot get current period for rate, country %s", rate.getCountryCode())));
    }

}