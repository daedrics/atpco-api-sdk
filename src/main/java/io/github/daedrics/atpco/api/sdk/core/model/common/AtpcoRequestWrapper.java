package io.github.daedrics.atpco.api.sdk.core.model.common;

import io.github.daedrics.atpco.api.sdk.config.AtpcoCredentials;
import lombok.Builder;
import lombok.Data;

/**
 * @author Redion Muraj
 * @since 23 Jul 2021
 */
@Data
@Builder
public class AtpcoRequestWrapper <T> {

    private AtpcoCredentials credentials;

    private T body;

}
