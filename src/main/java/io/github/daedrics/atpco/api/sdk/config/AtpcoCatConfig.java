package io.github.daedrics.atpco.api.sdk.config;

import lombok.Data;

/**
 * @author Redion Muraj
 * @since 16 Jun 2021
 */
@Data
public class AtpcoCatConfig {

    private String c02;

    private String c03;

    private AtpcoCat11Config c11;

    private AtpcoCat14Config c14;

    private AtpcoCat15Config c15;

}
