package com.barclays.test.view;

import com.barclays.test.dto.CountryDto;
import com.barclays.test.service.VatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VatPrinter implements CommandLineRunner {

    @Autowired
    private VatService vatService;

    @Override
    public void run(String... args) throws Exception {
        List<CountryDto> countriesWithLowestStandardVAT = vatService.getCountriesWithLowestStandardVAT(3);
        List<CountryDto> countriesWithHighestStandardVAT = vatService.getCountriesWithHighestStandardVAT(3);

        System.out.println("\n");

        System.out.println("Three countries with lowest standard VAT:\n");
        countriesWithLowestStandardVAT.forEach(country ->
                System.out.println(String.format("%-20s Standard VAT: %.2f", country.getName(), country.getStandardVAT()))
        );

        System.out.println("\n");

        System.out.println("Three countries with highest standard VAT:\n");
        countriesWithHighestStandardVAT.forEach(country ->
                System.out.println(String.format("%-20s Standard VAT: %.2f", country.getName(), country.getStandardVAT()))
        );


    }
}
