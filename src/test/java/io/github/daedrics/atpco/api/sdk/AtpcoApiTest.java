package io.github.daedrics.atpco.api.sdk;

import io.github.daedrics.atpco.api.sdk.core.AtpcoApi;
import io.github.daedrics.atpco.api.sdk.core.AtpcoApiRestTemplate;
import io.github.daedrics.atpco.api.sdk.core.model.TravelRestriction14;
import org.junit.Test;

/**
 * @author Redion Muraj
 * @since 23 Jul 2021
 */
public class AtpcoApiTest {

    private AtpcoApiRestTemplate atpcoApiRestTemplate;

    @Test
    public void test_Ok() {
        AtpcoApi atpcoApi = AtpcoApi.builder()
                .atpcoApiRestTemplate(atpcoApiRestTemplate)
                .clientId("fad")
                .clientSecret("fadd")
                .build();

        TravelRestriction14 travelRestriction14 = TravelRestriction14.builder().build();


        TravelRestriction14 footNote = atpcoApi.cat14()
                .footnote(travelRestriction14)
                .build().createFootnote();

    }

}
