package io.github.daedrics.atpco.api.sdk.core.cat14;

import io.github.daedrics.atpco.api.sdk.config.AtpcoCredentials;
import io.github.daedrics.atpco.api.sdk.core.AtpcoApiRestTemplate;
import io.github.daedrics.atpco.api.sdk.core.model.TravelRestriction14;
import lombok.Builder;

/**
 * @author Redion Muraj
 * @since 23 Jul 2021
 */
@Builder
public class Cat14Api {

    private final AtpcoApiRestTemplate atpcoApiRestTemplate;

    private final AtpcoCredentials atpcoCredentials;

    private final TravelRestriction14 footnote;

    public TravelRestriction14 createFootnote() {

        return atpcoApiRestTemplate.postForEntity(
                atpcoCredentials,
                TravelRestriction14.class,
                this.atpcoApiRestTemplate.getAtpcoConfiguration().getCat().getC14().getFootnote(),
                footnote);
    }


}
