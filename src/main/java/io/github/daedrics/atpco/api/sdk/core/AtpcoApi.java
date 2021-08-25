package io.github.daedrics.atpco.api.sdk.core;

import io.github.daedrics.atpco.api.sdk.config.AtpcoCredentials;
import io.github.daedrics.atpco.api.sdk.core.cat14.Cat14Api;
import lombok.Builder;

/**
 * @author Redion Muraj
 * @since 23 Jul 2021
 */
@Builder
public class AtpcoApi {

    private final AtpcoApiRestTemplate atpcoApiRestTemplate;

    private final String clientId;

    private final String clientSecret;


    public Cat14Api.Cat14ApiBuilder cat14() {
        AtpcoCredentials atpcoCredentials = AtpcoCredentials.builder()
                .clientId(clientId)
                .clientSecret(clientSecret).build();

        return Cat14Api.builder()
                .atpcoApiRestTemplate(atpcoApiRestTemplate)
                .atpcoCredentials(atpcoCredentials);
    }

}
