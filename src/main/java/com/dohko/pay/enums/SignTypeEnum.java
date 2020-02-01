package com.dohko.pay.enums;

/**
 * @author luxiaohua
 * @desc 签名算法
 */
public enum SignTypeEnum {

    MD5("MD5"), HMAC_SHA256("HMAC-SHA256");

    private String type;

    SignTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }}
