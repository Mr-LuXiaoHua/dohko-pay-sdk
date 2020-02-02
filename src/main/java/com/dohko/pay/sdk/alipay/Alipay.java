package com.dohko.pay.sdk.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;

import java.util.Map;

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
     * @param reqData
     * @return
     */
    public String appPay(Map<String, String> reqData) {

        // 支付宝返回的订单信息
        String orderInfo = "";

        AlipayClient alipayClient = getAlipayClient();

        // 构建请求模型
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setTotalAmount(reqData.get("totalAmount"));
        model.setSubject(reqData.get("subject"));
        model.setOutTradeNo(reqData.get("outTradeNo"));
        model.setProductCode(AlipayConst.PRODUCT_CODE_QUICK_MSECURITY_PAY);

        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(reqData.get("notifyUrl"));
        request.setBizModel(model);
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            if (response.isSuccess()) {
                orderInfo = response.getBody();
            } else {
                System.out.println("支付宝-APP支付-错误信息：" + response.getSubMsg());
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return orderInfo;
    }


    /**
     * H5支付
     * @param reqData
     * @return
     */
    public String h5Pay(Map<String, String> reqData) {

        // 支付宝返回的html
        String html = "";

        AlipayClient alipayClient = getAlipayClient();

        // 构建请求模型

        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setTotalAmount(reqData.get("totalAmount"));
        model.setSubject(reqData.get("subject"));
        model.setOutTradeNo(reqData.get("outTradeNo"));
        model.setProductCode(AlipayConst.PRODUCT_CODE_QUICK_WAP_WAY);

        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(reqData.get("noitifyUrl"));
        request.setReturnUrl(reqData.get("returnUrl"));
        request.setBizModel(model);

        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                html = response.getBody();
            } else {
                System.out.println("支付宝-H5支付-错误信息：" + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return html;
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
