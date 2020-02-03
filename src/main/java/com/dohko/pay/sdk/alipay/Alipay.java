package com.dohko.pay.sdk.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;

/**
 * @desc 支付宝
 */
public class Alipay {

    private AlipayConfig alipayConfig;

    public Alipay(AlipayConfig alipayConfig) {
        this.alipayConfig = alipayConfig;
    }


    /**
     * APP 支付
     * @param request
     * @return
     */
    public AlipayTradeAppPayResponse appPay(AlipayTradeAppPayRequest request) {

        AlipayClient alipayClient = getAlipayClient();

        AlipayTradeAppPayResponse response = null;
        try {
            response = alipayClient.sdkExecute(request);
            System.out.println("支付宝-APP支付-响应数据: " + response.getBody());
            if (!response.isSuccess()) {
                System.out.println("支付宝-APP支付-错误信息：" + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * H5支付
     * @param request
     * @return
     */
    public AlipayTradeWapPayResponse h5Pay(AlipayTradeWapPayRequest request) {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeWapPayResponse response = null;
        try {
            response = alipayClient.pageExecute(request);
            System.out.println("支付宝-H5支付-响应数据: " + response.getBody());
            if (!response.isSuccess()) {
                System.out.println("支付宝-H5支付-错误信息：" + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * 交易查询
     * @param request
     * @return
     */
    public AlipayTradeQueryResponse queryTrade(AlipayTradeQueryRequest request) {

        AlipayClient alipayClient = getAlipayClient();

        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
            System.out.println("支付宝-交易查询-响应数据: " + response.getBody());
            if (!response.isSuccess()) {
                System.out.println("支付宝-交易查询-错误信息：" + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * 退款申请
     * @param request
     * @return
     */
    public AlipayTradeRefundResponse refund(AlipayTradeRefundRequest request) {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
            System.out.println("支付宝-退款-响应数据：" + response.getBody());
            if (!response.isSuccess()) {
                System.out.println("支付宝-申请退款-错误信息：" + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * 退款查询
     * @param request
     * @return
     */
    public AlipayTradeFastpayRefundQueryResponse queryRefund(AlipayTradeFastpayRefundQueryRequest request) {
        AlipayClient alipayClient = getAlipayClient();

        AlipayTradeFastpayRefundQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
            System.out.println("支付宝-退款查询-响应数据：" + response.getBody());
            if (!response.isSuccess()) {
                System.out.println("支付宝-退款查询-错误信息：" + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * 获取对账单下载地址
     * @param request
     * @return
     */
    public AlipayDataDataserviceBillDownloadurlQueryResponse getBillDownloadUrl(AlipayDataDataserviceBillDownloadurlQueryRequest request) {
        AlipayClient alipayClient = getAlipayClient();

        AlipayDataDataserviceBillDownloadurlQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
            System.out.println("支付宝-获取对账单下载地址-响应数据：" + response.getBody());
            if (!response.isSuccess()) {
                System.out.println("支付宝-获取对账单下载地址-错误信息：" + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }



    /**
     * 获取AlipayClient
     * @return
     */
    private AlipayClient getAlipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getServerUrl(), alipayConfig.getAppId(),
                alipayConfig.getPrivateKey(), alipayConfig.getFormat(), alipayConfig.getCharset(),
                alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());
        return alipayClient;
    }
}
