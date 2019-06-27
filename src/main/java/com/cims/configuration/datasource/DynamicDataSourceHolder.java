package com.cims.configuration.datasource;

public class DynamicDataSourceHolder {


    //写库对应的数据源key
    public static final String MASTER = "mysql_write";

    //读库对应的数据源key
    public static final String SLAVE = "mysql_read";

    //使用ThreadLocal记录当前线程的数据源key
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();

    /**
     * 设置数据源key
     * @param key
     */
    public static void putDataSourceKey(String key) {
        holder.set(key);
    }

    /**
     * 获取数据源key
     * @return
     */
    public static String getDataSourceKey() {
        return holder.get();
    }

    /**
     * 标记写库
     */
    public static void markMaster(){
        putDataSourceKey(MASTER);
    }

    /**
     * 标记读库
     */
    public static void markSlave(){
        putDataSourceKey(SLAVE);
    }

    public static void removeDataSource(){
        holder.remove();
    }


}
