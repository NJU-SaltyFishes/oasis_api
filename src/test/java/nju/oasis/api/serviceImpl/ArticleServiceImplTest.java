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
    void getReferenceTitleByQueryUrl() {
        String expected = "Control of upper limb prostheses: Terminology and proportional myoelectric control—A review";
        String refUrl = "https://scholar.google.com/scholar?as_q=Control+of+upper+limb+prostheses%3A%0ATerminology+and+proportional+myoelectric+control%E2%80%94A+review&as_occt=title&hl=en&as_sdt=0%2C31";
        String ref = articleService.getReferenceTitleByQueryUrl(refUrl, "as_q");
        Assertions.assertEquals(expected, ref);
    }
}