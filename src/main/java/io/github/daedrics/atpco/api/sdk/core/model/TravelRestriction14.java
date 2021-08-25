package io.github.daedrics.atpco.api.sdk.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.daedrics.atpco.api.sdk.core.model.common.TravelRestriction14VO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TravelRestriction14 {

    private String allowEdot;

    private String carrier;

    private String errorOvrrdblWarnings;

    private String allow1stTktDt;

    private String tariff;

    private String msg;

    @JsonProperty(defaultValue = "Y")
    private String rtnTblNumsOnly;

    private TravelRestriction14VO tvlRstrxn14;

}