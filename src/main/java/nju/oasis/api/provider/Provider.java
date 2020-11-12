package nju.oasis.api.provider;

import java.util.concurrent.ConcurrentHashMap;

public abstract class Provider {
    /**
     * 运行provide获取数据存入 contextDataMap
     * @param contextDataMap
     * @return
     */
    public abstract boolean provide(ConcurrentHashMap<String,Object> contextDataMap);

    /**
     * 准备provide需要的参数
     * return true 表示参数准备成功
     * return false 表示参数准备失败
     * @param contextDataMap
     * @return
     */
    public abstract boolean parseParams(ConcurrentHashMap<String,Object> contextDataMap);
}
