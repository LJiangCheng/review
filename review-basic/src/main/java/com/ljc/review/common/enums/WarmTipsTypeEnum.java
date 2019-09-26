package com.ljc.review.common.enums;

public enum WarmTipsTypeEnum {
    //首页
    homePage("homePage", "首页"),
    //商品详情页
    productDetail("productDetail", "商品详情页"),
    //个人中心
    personalCenter("personalCenter", "个人中心"),
    //商品列表页
    productList("productList", "商品列表页"),
    //顶部通栏
    top("top", "顶部通栏"),
    //底部通栏
    bottom("bottom", "底部通栏"),;

    private String code;
    private String message;

    WarmTipsTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
