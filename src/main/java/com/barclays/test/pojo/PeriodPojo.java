package com.barclays.test.pojo;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "effective_from",
        "rates"
})
public class PeriodPojo {

    @JsonProperty("effective_from")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate effectiveFrom;
    @JsonProperty("rates")
    private RatesPojo rates;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("effective_from")
    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    @JsonProperty("effective_from")
    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    @JsonProperty("rates")
    public RatesPojo getRates() {
        return rates;
    }

    @JsonProperty("rates")
    public void setRates(RatesPojo rates) {
        this.rates = rates;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
