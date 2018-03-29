package com.imooc.classloder;

/**
 * 封装加载类的信息
 */
public class LoaderInfo {
    //自定义的类加载
    private MyClassLoader myloader;
    //记录要加载的类的时间戳--》记录的加载时间
    private long loadTime;
    private BaseManager manager;

    public LoaderInfo(MyClassLoader myloader, long loadTime) {
        this.myloader = myloader;
        this.loadTime = loadTime;
    }

    public BaseManager getManager() {
        return manager;
    }

    public void setManager(BaseManager manager) {
        this.manager = manager;
    }

    public long getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(long loadTime) {
        this.loadTime = loadTime;
    }
}
