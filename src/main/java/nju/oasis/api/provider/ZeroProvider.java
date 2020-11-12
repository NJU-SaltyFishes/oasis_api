package nju.oasis.api.provider;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ZeroProvider extends Provider {

    @Override
    public boolean provide(ConcurrentHashMap<String, Object> contextDataMap) {
        contextDataMap.put("zero", 100);
        return true;
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {
        return true;
    }

    public String toString(){
        return "[ZeroProvider]";
    }
}
