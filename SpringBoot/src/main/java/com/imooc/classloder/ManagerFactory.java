package com.imooc.classloder;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static jdk.nashorn.internal.objects.Global.load;

/**
 * 加载manager的工厂
 */
public class ManagerFactory {
    //记录热加载类的加载信息
    private static final Map<String,LoaderInfo> loadTimeMap = new HashMap<String,LoaderInfo>();
    //要加载的类的classpath 需要加载文件
    public static final String CLASS_PATH = "../";
    //实现热加载的类的全名称(包名+类名)
    public static final String MY_MANAGER = "com.imooc.classloder";

    public static BaseManager getManager(String className){
        File loadFile = new File(CLASS_PATH+className.replaceAll("\\.","/")+".class");
        long lastModified = loadFile.lastModified();
        //loadTimeMap不包含className为key的LoadInfo信息。证明这个类没有被加载，那么需要加载这个类到JVM
        if(loadTimeMap.get(className)==null){
            load(className,lastModified);
        }//加载类的时间戳变化了，我们同哟啊重新加载这个类JVM
        else if(loadTimeMap.get(className).getLoadTime()!=lastModified){
            load(className,lastModified);
        }
        return loadTimeMap.get(className).getManager();
    }
    private  static void load(String className,long lastModified){
        MyClassLoader myClassLoader= new MyClassLoader(CLASS_PATH);
        Class<?> loadClass = null;
        //加载loadClass方法
        try {
            loadClass = myClassLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        BaseManager manager = newInstance(loadClass);
        LoaderInfo loadInfo = new LoaderInfo(myClassLoader,lastModified);
        loadInfo.setManager(manager);
        loadTimeMap.put(className,loadInfo);
    };
    //以反射的方式创建BaseManagere子类对象
    private static BaseManager newInstance(Class<?> loadClass){
        try {
            return (BaseManager)loadClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }finally{
            return null;
        }
    };
}
