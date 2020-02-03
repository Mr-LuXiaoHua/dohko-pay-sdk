package com.dohko.pay.sdk.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

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
     * 交易查询
     * @param reqData
     * @return
     */
    public String queryTrade(Map<String, String> reqData) {

        // 支付宝返回的交易状态
        String tradeStatus = "";

        AlipayClient alipayClient = getAlipayClient();

        // 构建请求模型
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(reqData.get("outTradeNo"));

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);

        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                tradeStatus = response.getTradeStatus();
            } else {
                System.out.println("支付宝-交易查询-错误信息：" + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return tradeStatus;
    }


    /**
     * 退款申请
     * @param reqData
     * @return
     */
    public String refund(Map<String, String> reqData) {
        // 支付宝返回的退款交易号
        String tradeNo = "";
        AlipayClient alipayClient = getAlipayClient();
        // 构建请求模型
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(reqData.get("outTradeNo"));
        model.setRefundAmount(reqData.get("refundAmount"));
        if (StringUtils.isNotBlank(reqData.get("refundReason"))) {
            model.setRefundReason(reqData.get("refundReason"));
        }
        model.setOutRequestNo(RandomStringUtils.randomAlphanumeric(16));

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);

        try {
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                tradeNo = response.getTradeNo();
                // 可通过response获取其他响应数据
            } else {
                System.out.println("支付宝-申请退款-错误信息：" + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return tradeNo;
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
