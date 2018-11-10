package com.barclays.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CountryDto {

    private String name;

    private String code;

    private double standardVAT;


}
