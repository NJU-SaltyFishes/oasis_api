package nju.oasis.api.provider;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SecondProvider extends Provider {

    private Integer param;

    @Override
    public boolean provide(ConcurrentHashMap<String, Object> contextDataMap) {
        contextDataMap.put("second", this.param);
        return true;
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {
        try{
            if (contextDataMap.get("second") == null){
                return false;
            }
            this.param = (Integer) contextDataMap.get("second");
        }catch (Exception ex){
            log.warn("[SecondProvide] error: " + ex.getMessage());
            return false;
        }
        return true;
    }

    public String toString(){
        return "[SecondProvider]";
    }
}
