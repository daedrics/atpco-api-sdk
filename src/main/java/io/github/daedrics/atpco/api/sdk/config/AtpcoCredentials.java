package io.github.daedrics.atpco.api.sdk.config;

import lombok.Builder;
import lombok.Data;

/**
 * @author Redion Muraj (U197326)
 * @since 30 Jun 2021
 */
@Data
@Builder
public class AtpcoCredentials {

    private String clientSecret;

    private String clientId;

}
