package com.barclays.test.service;

import com.barclays.test.dto.CountryDto;
import com.barclays.test.pojo.CountriesVATsPojo;
import com.barclays.test.pojo.PeriodPojo;
import com.barclays.test.pojo.RatePojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class VatServiceImpl implements VatService {

    @Value("${api.vat.url}")
    private String apiUrl;

    private RestTemplate restTemplate;

    public VatServiceImpl(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    public List<CountryDto> getAllCountries() {
        CountriesVATsPojo countriesVATsPojo = restTemplate.getForObject(apiUrl, CountriesVATsPojo.class);
        return countriesVATsPojo
                .getRates()
                .stream()
                .map(this::mapRateToCountryDto)
                .collect(Collectors.toList());
    }

    public List<CountryDto> getCountriesWithLowestStandardVAT(int numberOfCountries) {
        return getSortedCountries(Comparator.comparing(CountryDto::getStandardVAT))
                .stream()
                .limit(numberOfCountries).collect(Collectors.toList());
    }

    public List<CountryDto> getCountriesWithHighestStandardVAT(int numberOfCountries) {
        return getSortedCountries(Comparator.comparing(CountryDto::getStandardVAT).reversed())
                .stream()
                .limit(numberOfCountries).collect(Collectors.toList());
    }

    private List<CountryDto> getSortedCountries(Comparator<CountryDto> comparator) {
        return getAllCountries()
                .stream()
                .sorted(comparator)
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
