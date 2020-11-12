package nju.oasis.api.provider;

import java.util.concurrent.ConcurrentHashMap;

public class FirstProvider extends Provider{

    @Override
    public boolean provide(ConcurrentHashMap<String, Object> contextDataMap) {
        contextDataMap.put("first", 100);
        contextDataMap.put("second", 200);
        contextDataMap.put("third", "1000");
        return true;
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {
        return true;
    }


    public String toString(){
        return "[FirstProvider]";
    }


}
