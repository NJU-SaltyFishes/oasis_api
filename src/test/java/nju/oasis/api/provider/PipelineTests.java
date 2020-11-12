package nju.oasis.api.provider;


import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;

public class PipelineTests {

    @Test
    void testPipeline(){
        Pipeline pipeline  = new Pipeline();
        pipeline.setParamToContextData("start", true);

        pipeline.addProviderAsGroup(new ZeroProvider(), new FirstProvider());

        pipeline.addProviderAsGroup(new SecondProvider(), new ThirdProvider());
        pipeline.doPipeline();

        ConcurrentHashMap<String, Object> map = pipeline.getContextData();

        for (String key: map.keySet()){
            System.out.println(key + " " + map.get(key));
        }

    }
}
