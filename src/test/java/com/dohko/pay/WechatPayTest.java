package com.dohko.pay;

import com.dohko.pay.enums.SignTypeEnum;
import com.dohko.pay.sdk.wechat.WechatPay;
import com.dohko.pay.sdk.wechat.WechatPayConfig;
import com.dohko.pay.sdk.wechat.WechatPayConst;
import com.dohko.pay.sdk.wechat.WechatPayMchTypeEnum;
import com.dohko.pay.util.IoUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @desc 微信支付测试
 */
public class WechatPayTest {

    /**
     * 将证书转换成base64字符串
     */
    @Test
    public void testCert2Base64String() {
        String filePath = "E:/certs/apiclient_cert.p12";
        String base64String = IoUtils.file2Base64String(filePath);
        System.out.println("证书内容："+ base64String);
    }

    /**
     * 测试统一下单
     */
    @Test
    public void testUnifiedOrder() {
        // 设置配置信息
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        // 设置普通商户模式
        wechatPayConfig.setWechatPayMchTypeEnum(WechatPayMchTypeEnum.NORMAL);
        wechatPayConfig.setAppId("应用id");
        wechatPayConfig.setApiKey("应用API-KEY");
        wechatPayConfig.setMchId("商户id");
        wechatPayConfig.setSignTypeEnum(SignTypeEnum.MD5);

        // 组装请求数据
        Map<String, String> reqData = new HashMap<>();
        reqData.put("nonce_str", RandomStringUtils.randomAlphabetic(20));
        reqData.put("body", "测试商品");
        reqData.put("out_trade_no", "202002011246508001");
        reqData.put("total_fee", "1");
        reqData.put("spbill_create_ip", "127.0.0.1");
        reqData.put("notify_url", "https://127.0.0.1/notify/wechat/pay-result");
        reqData.put("trade_type", WechatPayConst.TradeType.JSAPI);
        reqData.put("openid", "oJF384wBwxcrgh_AzSao6qXdew");

        WechatPay wechatPay = new WechatPay(wechatPayConfig);
        Map<String, String> resultMap = wechatPay.unifiedOrder(reqData);


    }


    /**
     * 测试查询订单
     */
    @Test
    public void testQueryOrder() {
        // 设置配置信息
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        wechatPayConfig.setWechatPayMchTypeEnum(WechatPayMchTypeEnum.NORMAL);
        wechatPayConfig.setAppId("应用id");
        wechatPayConfig.setApiKey("应用API-KEY");
        wechatPayConfig.setMchId("商户id");
        wechatPayConfig.setSignTypeEnum(SignTypeEnum.MD5);

        Map<String, String> reqData = new HashMap<>();
        reqData.put("nonce_str", RandomStringUtils.randomAlphabetic(20));
        reqData.put("out_trade_no", "202002011246508001");

        WechatPay wechatPay = new WechatPay(wechatPayConfig);
        Map<String, String> resultMap = wechatPay.queryOrder(reqData);


    }


    /**
     * 测试退款
     */
    @Test
    public void testRefund() {
        // 设置配置信息
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        wechatPayConfig.setWechatPayMchTypeEnum(WechatPayMchTypeEnum.NORMAL);
        wechatPayConfig.setAppId("应用id");
        wechatPayConfig.setApiKey("应用API-KEY");
        wechatPayConfig.setMchId("商户id");
        wechatPayConfig.setCertPassword("1512345678");

        /**
         * 证书设置
         * wechatPayConfig.setCertPath 和 wechatPayConfig.setCertBase64Content
         * 两种方式选其中一种方式即可
         */
        // 方式一：设置证书所在路径
        // wechatPayConfig.setCertPath("E:/certs/apiclient_cert.p12");
        // 方式二：设置证书内容
        wechatPayConfig.setCertBase64Content("Base64字符串格式证书内容");

        wechatPayConfig.setSignTypeEnum(SignTypeEnum.MD5);

        Map<String, String> reqData = new HashMap<>();
        reqData.put("nonce_str", RandomStringUtils.randomAlphabetic(20));
        reqData.put("out_trade_no", "202002011246508001");
        reqData.put("out_refund_no", "102002011246508001");
        reqData.put("total_fee", "1");
        reqData.put("refund_fee", "1");
        reqData.put("refund_desc", "商品已售完");

        WechatPay wechatPay = new WechatPay(wechatPayConfig);
        Map<String, String> resultMap = wechatPay.refund(reqData);

        Assert.assertEquals(WechatPayConst.Resp.SUCCESS, resultMap.get("result_code"));

    }


    /**
     * 测试退款查询
     */
    @Test
    public void testRefundQuery() {
        // 设置配置信息
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        wechatPayConfig.setWechatPayMchTypeEnum(WechatPayMchTypeEnum.NORMAL);
        wechatPayConfig.setAppId("应用id");
        wechatPayConfig.setApiKey("应用API-KEY");
        wechatPayConfig.setMchId("商户id");
        wechatPayConfig.setSignTypeEnum(SignTypeEnum.MD5);

        Map<String, String> reqData = new HashMap<>();
        reqData.put("nonce_str", RandomStringUtils.randomAlphabetic(20));
        reqData.put("out_refund_no", "102002011246508001");

        WechatPay wechatPay = new WechatPay(wechatPayConfig);
        Map<String, String> resultMap = wechatPay.refundQuery(reqData);

        Assert.assertEquals(WechatPayConst.Resp.SUCCESS, resultMap.get("result_code"));

    }

    /**
     * 测试对账单下单
     */
    @Test
    public void testDownloadBill() {
        // 设置配置信息
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        wechatPayConfig.setWechatPayMchTypeEnum(WechatPayMchTypeEnum.NORMAL);
        wechatPayConfig.setAppId("应用id");
        wechatPayConfig.setApiKey("应用API-KEY");
        wechatPayConfig.setMchId("商户id");
        wechatPayConfig.setSignTypeEnum(SignTypeEnum.MD5);

        Map<String, String> reqData = new HashMap<>();
        reqData.put("nonce_str", RandomStringUtils.randomAlphabetic(20));
        reqData.put("bill_date", "20191230");
        reqData.put("bill_type", "ALL");

        WechatPay wechatPay = new WechatPay(wechatPayConfig);
        String result = wechatPay.downloadBill(reqData);


    }
}
