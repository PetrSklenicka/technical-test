package com.barclays.test;

import com.barclays.test.dto.CountryDto;
import com.barclays.test.service.VatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrinterRunner implements CommandLineRunner {

    private static final int COUNTRIES_COUNT = 3;

    @Autowired
    private VatService vatService;

    @Override
    public void run(String... args) {
        try {
            printCountriesWithHighestStandardVAT();
            printCountriesWithLowestStandardVAT();
        } catch (Exception e) {
            System.err.println("Cannot print countries. " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void printCountriesWithLowestStandardVAT() {
        System.out.println(String.format("\n%d countries with lowest standard VAT:\n", COUNTRIES_COUNT));

        List<CountryDto> countriesWithLowestStandardVAT = vatService.getCountriesWithLowestStandardVAT(COUNTRIES_COUNT);
        printWarningIfNotEnoughCountries(countriesWithLowestStandardVAT, COUNTRIES_COUNT);

        countriesWithLowestStandardVAT.forEach(country ->
                System.out.println(String.format("%-20s Standard VAT: %.2f", country.getName(), country.getStandardVAT()))
        );
    }

    private void printCountriesWithHighestStandardVAT() {
        System.out.println(String.format("\n%d countries with highest standard VAT:\n", COUNTRIES_COUNT));

        List<CountryDto> countriesWithHighestStandardVAT = vatService.getCountriesWithHighestStandardVAT(COUNTRIES_COUNT);
        printWarningIfNotEnoughCountries(countriesWithHighestStandardVAT, COUNTRIES_COUNT);

        countriesWithHighestStandardVAT.forEach(country ->
                System.out.println(String.format("%-20s Standard VAT: %.2f", country.getName(), country.getStandardVAT()))
        );
    }

    private void printWarningIfNotEnoughCountries(List<CountryDto> countries, int requestedCount) {
        if (countries.size() < requestedCount) {
            System.out.println(String.format("--- cannot print %d countries, only %d are available ---", requestedCount, countries.size()));
        }
    }

}
