package io.github.daedrics.atpco.api.sdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Redion Muraj
 * @since 30 Jun 2021
 */
@Data
@ConfigurationProperties(prefix = "atpco")
public class AtpcoConfigurationProperties {

    private String baseUri;

    private String accessTokenUri;

    private AtpcoCatConfig cat;

}
