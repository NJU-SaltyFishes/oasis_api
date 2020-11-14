package nju.oasis.api.serviceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleServiceImplTest {

    @Autowired
    private ArticleServiceImpl articleService;

    @Test
    void getReferenceTitleByQueryUrlACM() {
        String expected = "Control of upper limb prostheses: Terminology and proportional myoelectric control—A review";
        String refUrl = "https://scholar.google.com/scholar?as_q=Control+of+upper+limb+prostheses%3A%0ATerminology+and+proportional+myoelectric+control%E2%80%94A+review&as_occt=title&hl=en&as_sdt=0%2C31";
        String ref = articleService.getReferenceTitleByQueryUrl(refUrl, "as_q");
        Assertions.assertEquals(expected, ref);
    }

    @Test
    void getReferenceTitleByQueryUrlIEEE() {
        String expected = "Lo, C., Chan, P., Wong, Y., Rad, A. and Cheung, K., Fuzzy-genetic algorithm for automatic fault detection in hvac systems. Applied Soft Computing. v7 i2. 554-560. 10.1016/j.asoc.2006.06.003";
        String refUrl = "http://scholar.google.com/scholar?hl=en&q=Lo%2C+C.%2C+Chan%2C+P.%2C+Wong%2C+Y.%2C+Rad%2C+A.+and+Cheung%2C+K.%2C+Fuzzy-genetic+algorithm+for+automatic+fault+detection+in+hvac+systems.+Applied+Soft+Computing.+v7+i2.+554-560.+10.1016%2Fj.asoc.2006.06.003+";
        String ref = articleService.getReferenceTitleByQueryUrl(refUrl, "q");
        System.out.println(ref);
        Assertions.assertEquals(expected, ref);
    }
}