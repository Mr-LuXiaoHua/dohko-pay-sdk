package com.dohko.pay;

import com.dohko.pay.sdk.alipay.Alipay;
import com.dohko.pay.sdk.alipay.AlipayConfig;
import com.dohko.pay.sdk.alipay.AlipayConst;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @desc 支付宝支付测试
 */
public class AlipayTest {

    /**
     * 测试APP支付
     */
    @Test
    public void testAppPay() {

        // 设置支付宝配置
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId("200000000000000");
        alipayConfig.setCharset(AlipayConst.CHARSET_UTF8);
        alipayConfig.setFormat(AlipayConst.FORMAT_JSON);
        alipayConfig.setServerUrl(AlipayConst.SERVER_URL);
        alipayConfig.setSignType(AlipayConst.SIGN_TYPE_RSA2);
        alipayConfig.setAlipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8...");
        alipayConfig.setPrivateKey("MIIEvQIBADAN...");

        Map<String, String> reqData = new HashMap<>();
        reqData.put("totalAmount", "0.01");
        reqData.put("subject", "测试支付宝");
        reqData.put("outTradeNo", "2020020222460001");
        reqData.put("notifyUrl", "https://127.0.0.1/notify/alipay/pay-result");

        Alipay alipay = new Alipay(alipayConfig);
        String orderInfo = alipay.appPay(reqData);
        System.out.println("orderInfo:" + orderInfo);
    }

    /**
     * 测试H5支付
     */
    @Test
    public void testH5Pay() {

        // 设置支付宝配置
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId("200000000000000");
        alipayConfig.setCharset(AlipayConst.CHARSET_UTF8);
        alipayConfig.setFormat(AlipayConst.FORMAT_JSON);
        alipayConfig.setServerUrl(AlipayConst.SERVER_URL);
        alipayConfig.setSignType(AlipayConst.SIGN_TYPE_RSA2);
        alipayConfig.setAlipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8...");
        alipayConfig.setPrivateKey("MIIEvQIBADAN...");

        Map<String, String> reqData = new HashMap<>();
        reqData.put("totalAmount", "0.01");
        reqData.put("subject", "测试支付宝");
        reqData.put("outTradeNo", "2020020222460002");
        reqData.put("notifyUrl", "https://127.0.0.1/notify/alipay/pay-result");
        reqData.put("returnUrl", "https://127.0.0.1/pay-result.html");

        Alipay alipay = new Alipay(alipayConfig);
        String html = alipay.h5Pay(reqData);
        System.out.println("html:" + html);
    }

    /**
     * 支付宝-退款申请
     */
    @Test
    public void testRefund() {

        // 设置支付宝配置
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId("200000000000000");
        alipayConfig.setCharset(AlipayConst.CHARSET_UTF8);
        alipayConfig.setFormat(AlipayConst.FORMAT_JSON);
        alipayConfig.setServerUrl(AlipayConst.SERVER_URL);
        alipayConfig.setSignType(AlipayConst.SIGN_TYPE_RSA2);
        alipayConfig.setAlipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8...");
        alipayConfig.setPrivateKey("MIIEvQIBADAN...");

        Map<String, String> reqData = new HashMap<>();
        reqData.put("outTradeNo", "2020020222460002");
        reqData.put("refundAmount", "0.01");
        reqData.put("refundReason", "商品无货退款");

        Alipay alipay = new Alipay(alipayConfig);
        String tradeNo = alipay.refund(reqData);
        System.out.println("tradeNo:" + tradeNo);
    }


    /**
     * 支付查询
     */
    @Test
    public void testQueryTrade() {

        // 设置支付宝配置
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId("200000000000000");
        alipayConfig.setCharset(AlipayConst.CHARSET_UTF8);
        alipayConfig.setFormat(AlipayConst.FORMAT_JSON);
        alipayConfig.setServerUrl(AlipayConst.SERVER_URL);
        alipayConfig.setSignType(AlipayConst.SIGN_TYPE_RSA2);
        alipayConfig.setAlipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8...");
        alipayConfig.setPrivateKey("MIIEvQIBADAN...");

        Map<String, String> reqData = new HashMap<>();
        reqData.put("outTradeNo", "2020020222460002");

        Alipay alipay = new Alipay(alipayConfig);
        String tradeStatus = alipay.queryTrade(reqData);
        System.out.println("tradeStatus:" + tradeStatus);
    }
}
