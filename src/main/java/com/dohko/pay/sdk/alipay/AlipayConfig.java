package com.dohko.pay.sdk.alipay;

import lombok.Data;

/**
 * @desc 支付宝配置
 */
@Data
public class AlipayConfig {
    /**
     * 支付宝服务器url
     */
    private String serverUrl;
    /**
     * 应用id
     */
    private String appId;
    /**
     * 应用私钥
     */
    private String privateKey;
    /**
     * 数据格式
     */
    private String format;
    /**
     * 请求使用的编码格式：UTF-8、GBK
     */
    private String charset;
    /**
     * 支付宝公钥
     */
    private String alipayPublicKey;
    /**
     * 商户生成签名字符串所使用的签名算法类型，
     * 目前支持RSA2和RSA，推荐使用RSA2
     */
    private String signType;
}
