package io.github.daedrics.atpco.api.sdk.core.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelRestriction14VO {

    private String tvlDtComm;

    private String tvlDtExp;

    private String tvlDtAppl;

    private String tvlDtCommComp;

    private String tvlDtTm;

    private String tblNum;

    private GeoSpec995VO geoSpec995;

    private OverrideDate994VO ovrrdDt994;

    private APIMessage msgs;

    @JsonIgnore
    public List<String> getErrorMessages() {
        List<String> allErrors = new ArrayList<>();
        if (this.msgs != null) {
            List<String> errors = this.msgs.getErrors();
            List<String> warnings = this.msgs.getWarnings();
            Optional.ofNullable(errors).ifPresent(allErrors::addAll);
            Optional.ofNullable(warnings).ifPresent(allErrors::addAll);
        }
        if (this.geoSpec995.getMsgs() != null) {
            List<String> errors = this.geoSpec995.getMsgs().getErrors();
            List<String> warnings = this.geoSpec995.getMsgs().getWarnings();

            Optional.ofNullable(errors).ifPresent(allErrors::addAll);
            Optional.ofNullable(warnings).ifPresent(allErrors::addAll);
        }
        return allErrors;
    }

}