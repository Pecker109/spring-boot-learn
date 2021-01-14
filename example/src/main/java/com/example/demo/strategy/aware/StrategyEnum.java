package com.example.demo.strategy.aware;

/**
 * @author Pecker
 * @Description 策略枚举
 * @since 2020-11-19
 */
public enum StrategyEnum {

    Strategy1(1,"策略 1"),
    Strategy2(2, "策略 2");
    private Integer type;
    private String tag;

    StrategyEnum(Integer type, String tag) {
        this.type = type;
        this.tag = tag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
