package com.zhu.demo.config;

/**
 * @author 河南张国荣
 * @create 2022-03-14 22:30
 */
public enum DBTypeEnum {

    first("first"), second("second");
    private String value;

    DBTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
