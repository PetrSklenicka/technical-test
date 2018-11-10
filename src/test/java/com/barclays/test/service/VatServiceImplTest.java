package com.barclays.test.service;

import com.barclays.test.dto.CountryDto;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(VatServiceImpl.class)
@RunWith(SpringRunner.class)
public class VatServiceImplTest {

    @Autowired
    private VatServiceImpl vatService;

    @Mock
    private VatServiceImpl vatServiceMock;

    @Autowired
    private MockRestServiceServer mockServer;

    @Before
    public void setUp() throws IOException {
        prepareMockServer();
        prepareMockVatService();
    }

    @Test
    public void getAllCountriesTest() {
        List<CountryDto> countries = vatService.getAllCountries();

        assertThat(countries, hasSize(1));
        assertEquals("Spain", countries.get(0).getName());
        assertEquals("ES", countries.get(0).getCode());
        assertEquals(35, countries.get(0).getStandardVAT(), 0);
    }

    @Test
    public void getCountriesWithLowestStandardVATTest() {
        List<CountryDto> countries = vatServiceMock.getCountriesWithLowestStandardVAT(2);

        assertThat(countries, hasSize(2));
        assertEquals("CZ", countries.get(0).getCode());
        assertEquals("PL", countries.get(1).getCode());
    }

    @Test
    public void getCountriesWithHighestStandardVATTest() {
        List<CountryDto> countries = vatServiceMock.getCountriesWithHighestStandardVAT(2);

        assertThat(countries, hasSize(2));
        assertEquals("ES", countries.get(0).getCode());
        assertEquals("HU", countries.get(1).getCode());
    }

    private void prepareMockVatService() {
        when(vatServiceMock.getAllCountries()).thenReturn(mockData());
        when(vatServiceMock.getCountriesWithHighestStandardVAT(anyInt())).thenCallRealMethod();
        when(vatServiceMock.getCountriesWithLowestStandardVAT(anyInt())).thenCallRealMethod();
    }

    private void prepareMockServer() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/vatRates.json");
        String response = IOUtils.toString(is, "UTF-8");
        mockServer
                .expect(requestTo("http://jsonvat.com/"))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
    }

    private List<CountryDto> mockData() {
        CountryDto spain = new CountryDto("Spain", "ES", 20);
        CountryDto hungary = new CountryDto("Hungary", "HU", 18);
        CountryDto latvia = new CountryDto("Latvia", "LV", 16);
        CountryDto poland = new CountryDto("Poland", "PL", 14);
        CountryDto czech = new CountryDto("Czech Republic", "CZ", 12);
        return Arrays.asList(spain, hungary, latvia, poland, czech);
    }

}
