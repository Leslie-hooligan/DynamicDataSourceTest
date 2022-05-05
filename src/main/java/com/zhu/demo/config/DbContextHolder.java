package com.zhu.demo.config;

/**
 * @author 河南张国荣
 * @create 2022-03-14 22:32
 */
public class DbContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /**
     * 取得当前数据源
     *
     * @return
     */
    public static String getDbType() {
        return (String) contextHolder.get();
    }

    /**
     * 设置数据源
     *
     * @param dbTypeEnum
     */
    public static void setDbType(DBTypeEnum dbTypeEnum) {
        contextHolder.set(dbTypeEnum.getValue());
    }
}
