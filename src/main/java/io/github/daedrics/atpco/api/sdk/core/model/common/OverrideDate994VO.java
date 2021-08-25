package io.github.daedrics.atpco.api.sdk.core.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OverrideDate994VO {

    private String tblNum;

    // Format: YYYYMMDD => Example: "20210616"
    private String tvlEff;

    private String tvlDisc;

    private String tktEff;

    private String tktDisc;

    private String rsvEff;

    private String rsvDisc;

    private APIMessage msgs;

}