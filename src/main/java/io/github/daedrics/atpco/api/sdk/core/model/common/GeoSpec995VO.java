package io.github.daedrics.atpco.api.sdk.core.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeoSpec995VO {

    private String tblNum;

    private String loc1;
    private String type;
    private String loc2;
    private String tsi;

    private APIMessage msgs;

}