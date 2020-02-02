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
        alipayConfig.setAppId("2000000000000000");
        alipayConfig.setCharset(AlipayConst.CHARSET_UTF8);
        alipayConfig.setFormat(AlipayConst.FORMAT_JSON);
        alipayConfig.setServerUrl(AlipayConst.SERVER_URL);
        alipayConfig.setSignType(AlipayConst.SIGN_TYPE_RSA2);
        alipayConfig.setAlipayPublicKey("MIIBIjANBgkqhki...");
        alipayConfig.setPrivateKey("MIIEvQIBADANB...");

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
        alipayConfig.setAppId("2000000000000000");
        alipayConfig.setCharset(AlipayConst.CHARSET_UTF8);
        alipayConfig.setFormat(AlipayConst.FORMAT_JSON);
        alipayConfig.setServerUrl(AlipayConst.SERVER_URL);
        alipayConfig.setSignType(AlipayConst.SIGN_TYPE_RSA2);
        alipayConfig.setAlipayPublicKey("MIIBIjANBgkqhki...");
        alipayConfig.setPrivateKey("MIIEvQIBADANB...");

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
}
