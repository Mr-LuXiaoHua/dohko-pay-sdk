package com.dohko.pay;

import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.dohko.pay.sdk.alipay.Alipay;
import com.dohko.pay.sdk.alipay.AlipayConfig;
import com.dohko.pay.sdk.alipay.AlipayConst;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

/**
 * @author
 * @desc 支付宝支付测试
 */
public class AlipayTest {

    /**
     * 支付宝-APP支付
     */
    @Test
    public void testAppPay() {

        // 设置支付宝配置
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId("应用id");
        alipayConfig.setCharset(AlipayConst.CHARSET_UTF8);
        alipayConfig.setFormat(AlipayConst.FORMAT_JSON);
        alipayConfig.setServerUrl(AlipayConst.SERVER_URL);
        alipayConfig.setSignType(AlipayConst.SIGN_TYPE_RSA2);
        alipayConfig.setAlipayPublicKey("支付宝公钥");
        alipayConfig.setPrivateKey("应用私钥");

        // 构建请求
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setTotalAmount("0.01");
        model.setSubject("测试商品");
        model.setOutTradeNo("2020020222460001");
        model.setProductCode(AlipayConst.PRODUCT_CODE_QUICK_MSECURITY_PAY);

        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl("https://127.0.0.1/notify/alipay/pay-result");
        request.setBizModel(model);

        Alipay alipay = new Alipay(alipayConfig);
        AlipayTradeAppPayResponse response = alipay.appPay(request);

        if (response.isSuccess()) {
            // 获取APP支付所需信息
            System.out.println(response.getBody());
        }

    }

    /**
     * 支付宝-H5支付
     */
    @Test
    public void testH5Pay() {
        // 设置支付宝配置
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId("应用id");
        alipayConfig.setCharset(AlipayConst.CHARSET_UTF8);
        alipayConfig.setFormat(AlipayConst.FORMAT_JSON);
        alipayConfig.setServerUrl(AlipayConst.SERVER_URL);
        alipayConfig.setSignType(AlipayConst.SIGN_TYPE_RSA2);
        alipayConfig.setAlipayPublicKey("支付宝公钥");
        alipayConfig.setPrivateKey("应用私钥");

        // 构建请求
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setTotalAmount("0.01");
        model.setSubject("测试商品");
        model.setOutTradeNo("2020020222460002");
        model.setProductCode(AlipayConst.PRODUCT_CODE_QUICK_WAP_WAY);

        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl("https://127.0.0.1/notify/alipay/pay-result");
        request.setReturnUrl("https://127.0.0.1/pay-result.html");
        request.setBizModel(model);

        Alipay alipay = new Alipay(alipayConfig);
        AlipayTradeWapPayResponse response = alipay.h5Pay(request);
        System.out.println("H5支付所需html:" + response.getBody());
    }

    /**
     * 支付宝-退款申请
     */
    @Test
    public void testRefund() {

        // 设置支付宝配置
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId("应用id");
        alipayConfig.setCharset(AlipayConst.CHARSET_UTF8);
        alipayConfig.setFormat(AlipayConst.FORMAT_JSON);
        alipayConfig.setServerUrl(AlipayConst.SERVER_URL);
        alipayConfig.setSignType(AlipayConst.SIGN_TYPE_RSA2);
        alipayConfig.setAlipayPublicKey("支付宝公钥");
        alipayConfig.setPrivateKey("应用私钥");

        // 构建请求模型
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo("2020020222460002");
        model.setRefundAmount("0.01");
        model.setRefundReason("商品无货");
        model.setOutRequestNo(RandomStringUtils.randomNumeric(16));

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);

        Alipay alipay = new Alipay(alipayConfig);
        AlipayTradeRefundResponse response = alipay.refund(request);

    }



    /**
     * 支付宝-交易查询
     */
    @Test
    public void testQueryTrade() {

        // 设置支付宝配置
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId("应用id");
        alipayConfig.setCharset(AlipayConst.CHARSET_UTF8);
        alipayConfig.setFormat(AlipayConst.FORMAT_JSON);
        alipayConfig.setServerUrl(AlipayConst.SERVER_URL);
        alipayConfig.setSignType(AlipayConst.SIGN_TYPE_RSA2);
        alipayConfig.setAlipayPublicKey("支付宝公钥");
        alipayConfig.setPrivateKey("应用私钥");

        // 构建请求模型
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo("2020020222460002");

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);

        Alipay alipay = new Alipay(alipayConfig);
        AlipayTradeQueryResponse response = alipay.queryTrade(request);
        System.out.println("响应数据:" + response.getBody());
    }

    /**
     * 支付宝-退款查询
     */
    @Test
    public void testQueryRefund() {

        // 设置支付宝配置
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId("应用id");
        alipayConfig.setCharset(AlipayConst.CHARSET_UTF8);
        alipayConfig.setFormat(AlipayConst.FORMAT_JSON);
        alipayConfig.setServerUrl(AlipayConst.SERVER_URL);
        alipayConfig.setSignType(AlipayConst.SIGN_TYPE_RSA2);
        alipayConfig.setAlipayPublicKey("支付宝公钥");
        alipayConfig.setPrivateKey("应用私钥");

        // 构建请求模型
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutTradeNo("202002021293409988");
        model.setOutRequestNo(RandomStringUtils.randomNumeric(16));

        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest ();
        request.setBizModel(model);

        Alipay alipay = new Alipay(alipayConfig);
        AlipayTradeFastpayRefundQueryResponse response = alipay.queryRefund(request);
    }


    /**
     * 支付宝-获取对账单下载地址
     */
    @Test
    public void testGetBillDownloadUrl() {

        // 设置支付宝配置
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId("应用id");
        alipayConfig.setCharset(AlipayConst.CHARSET_UTF8);
        alipayConfig.setFormat(AlipayConst.FORMAT_JSON);
        alipayConfig.setServerUrl(AlipayConst.SERVER_URL);
        alipayConfig.setSignType(AlipayConst.SIGN_TYPE_RSA2);
        alipayConfig.setAlipayPublicKey("支付宝公钥");
        alipayConfig.setPrivateKey("应用私钥");

        AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
        model.setBillType(AlipayConst.BILL_TYPE_TRADE);
        model.setBillDate("2019-12-08");

        // 构建请求模型
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizModel(model);

        Alipay alipay = new Alipay(alipayConfig);
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipay.getBillDownloadUrl(request);
        System.out.println("对账单下载地址：" + response.getBillDownloadUrl());
    }



}
