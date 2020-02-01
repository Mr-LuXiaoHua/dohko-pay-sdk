package com.dohko.pay.sdk.wechat;

import com.dohko.pay.enums.SignTypeEnum;
import lombok.Data;

/**
 * @desc 微信支付通用配置
 * @author luxiaohua
 */
@Data
public class WechatPayConfig {

    /**
     * 商户类型（普通商户或服务商）
     */
    private WechatPayMchTypeEnum wechatPayMchTypeEnum;
    /**
     * 应用id
     */
    private String appId;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 子商户应用id
     */
    private String subAppId;

    /**
     * 子商户号
     */
    private String subMchId;

    /**
     * 用于接口API签名的key
     */
    private String apiKey;

    /**
     * 安全证书存放绝对路径
     * certPath和certBase64Content使用一个即可
     */
    private String certPath;

    /**
     * 将证书内容Base64编码后的内容
     * certPath和certBase64Content使用一个即可
     */
    private String certBase64Content;

    /**
     * 证书密码
     */
    private String certPassword;

    /**
     * 签名算法
     */
    private SignTypeEnum signTypeEnum;




}
