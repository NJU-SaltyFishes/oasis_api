package nju.oasis.api.provider;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.concurrent.*;

@Slf4j
public class Pipeline{

    private ConcurrentLinkedQueue<ConcurrentLinkedQueue<Provider> > providerGroupList;
    private ConcurrentHashMap<String, Object> contextDataMap;

    public Pipeline(){
        this.providerGroupList = new ConcurrentLinkedQueue<>();
        this.contextDataMap = new ConcurrentHashMap<>();
    }

    public void addProviderAsGroup(Provider... args) {
        if (args.length == 0){
            return;
        }
        ConcurrentLinkedQueue<Provider> providerGroup = new ConcurrentLinkedQueue<>();
        Collections.addAll(providerGroup, args);
        providerGroupList.add(providerGroup);
    }

    public void setParamToContextData(String key, Object value){
        if (key == null || "".equals(key)){
            return;
        }
        this.contextDataMap.put(key, value);
    }

    public ConcurrentHashMap<String, Object> getContextData(){
        return this.contextDataMap;
    }

    public void doPipeline() {
        for (ConcurrentLinkedQueue<Provider> providerGroup: providerGroupList){
            if (providerGroup.isEmpty()){
                continue;
            }
            ExecutorService threadPool = Executors.newCachedThreadPool();
            for (Provider provider: providerGroup){
                if (!provider.parseParams(contextDataMap)){
                    log.warn(provider.toString() + " invalid params");
                    continue;
                }
                Runnable r = () -> {
                    if (provider.provide(contextDataMap)){
                        log.info(provider.toString() + " finished");
                    }else {
                        log.error(provider.toString() + " failed");
                    }
                };
                threadPool.submit(r);
            }
            threadPool.shutdown();
            try{ // 让所有的任务执行结束
                threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
