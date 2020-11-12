package nju.oasis.api.provider;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ThirdProvider extends Provider {

    private Integer param;

    @Override
    public boolean provide(ConcurrentHashMap<String, Object> contextDataMap) {
        contextDataMap.put("new_third", this.param);
        return true;
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {
        try {
            if (contextDataMap.get("third") == null){
                return false;
            }
            this.param = (Integer) contextDataMap.get("third");
        } catch (Exception ex){
            log.warn("[ThirdProvider] error: " + ex.getMessage());
            return false;
        }
        return true;
    }

    public String toString(){
        return "[ThirdProvider], param=" + this.param;
    }
}
