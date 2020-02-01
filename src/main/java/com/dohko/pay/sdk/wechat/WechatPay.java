package com.dohko.pay.sdk.wechat;

import com.dohko.pay.enums.SignTypeEnum;
import com.dohko.pay.exception.PayException;
import com.dohko.pay.util.HttpUtils;
import com.dohko.pay.util.SignUtils;
import com.dohko.pay.util.XmlUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.util.Map;

/**
 * @author luxiaohua
 * @desc 微信支付
 */
public class WechatPay {

    /**
     * 微信支付配置
     */
    private WechatPayConfig wechatPayConfig;

    public WechatPay(WechatPayConfig wechatPayConfig) {
        this.wechatPayConfig = wechatPayConfig;
    }

    /**
     *  统一下单
     * @param reqData
     * @return
     */
    public Map<String, String> unifiedOrder(Map<String, String> reqData) {
        return requestWechat(WechatPayConst.Url.UNIFIED_ORDER_URL, reqData, false);
    }


    /**
     * 查询订单
     * @param reqData
     * @return
     */
    public Map<String, String> queryOrder(Map<String, String> reqData) {
        return requestWechat(WechatPayConst.Url.QUERY_ORDER_URL, reqData, false);
    }

    /**
     * 申请退款
     * @param reqData
     * @return
     */
    public Map<String, String> refund(Map<String, String> reqData) {
        return requestWechat(WechatPayConst.Url.REFUND_URL, reqData, true);
    }

    /**
     * 退款查询
     * @param reqData
     * @return
     */
    public Map<String, String> refundQuery(Map<String, String> reqData) {
        return requestWechat(WechatPayConst.Url.REFUND_QUERY_URL, reqData, false);
    }

    /**
     * 下单对账单
     * @param reqData
     * @return
     */
    public String downloadBill(Map<String, String> reqData) {
        return requestWechatDownloadBill(WechatPayConst.Url.DOWNLOAD_BILL_URL, reqData);
    }




    /**
     * 组装请求参数
     * @param reqData
     */
    private void buildReqData(Map<String, String> reqData) {
        // 组装微信统一下单参数
        reqData.put("appid", wechatPayConfig.getAppId());
        reqData.put("mch_id", wechatPayConfig.getMchId());
        if (WechatPayMchTypeEnum.SERVICE_PROVIDER == wechatPayConfig.getWechatPayMchTypeEnum()) {
            // 服务商模式
            reqData.put("sub_appid", wechatPayConfig.getSubAppId());
            reqData.put("sub_mch_id", wechatPayConfig.getSubMchId());
        }
        reqData.put("sign_type", wechatPayConfig.getSignTypeEnum().getType());
        String text = SignUtils.buildSortContent(reqData) + "&key=" + wechatPayConfig.getApiKey();
        if (SignTypeEnum.MD5.getType().equals(wechatPayConfig.getSignTypeEnum().getType())) {
            reqData.put("sign", SignUtils.md5(text).toUpperCase());
        } else if (SignTypeEnum.HMAC_SHA256.getType().equals(wechatPayConfig.getSignTypeEnum().getType())){
            reqData.put("sign", SignUtils.hmacSha256(text, wechatPayConfig.getApiKey()).toUpperCase());
        }

    }

    /**
     * 请求微信接口
     * @param url
     * @param reqData
     * @param useCert 是否使用证书：true-是；false-否
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读超时时间
     * @return
     */
    private Map<String, String> requestWechat(String url, Map<String, String> reqData,  boolean useCert, int connectTimeout, int readTimeout) {
        buildReqData(reqData);
        String xmlReqData = XmlUtils.mapToXml(reqData);
        System.out.println("微信支付 -- 请求url:" + url + " ,请求参数:" + xmlReqData);
        String result;
        if (!useCert) {
            result = HttpUtils.post(url, xmlReqData, connectTimeout, readTimeout);
        } else {
            SSLContext sslContext = getSSLContext(wechatPayConfig.getCertBase64Content(), wechatPayConfig.getCertPassword());
            result = HttpUtils.post(url, xmlReqData, sslContext,connectTimeout, readTimeout);
        }
        System.out.println("微信支付 -- 响应数据:" + result);
        if (StringUtils.isNotBlank(result)) {
            Map<String, String> resultMap = XmlUtils.xmlToMap(result);
            checkResposeSign(resultMap);
            return resultMap;
        } else {
            throw new PayException("微信支付渠道响应异常");
        }
    }

    /**
     * 请求微信接口
     * @param url
     * @param reqData
     * @param useCert 是否使用证书：true-是；false-否
     * @return
     */
    private Map<String, String> requestWechat(String url, Map<String, String> reqData,  boolean useCert) {
      return requestWechat(url, reqData, useCert, 1000, 2000);
    }

    /**
     * 请求微信下载对账单
     * @param url
     * @param reqData
     * @param connectTimeout 连接超时时间 毫秒
     * @param readTimeout 读超时时间 毫秒
     * @return
     */
    private String requestWechatDownloadBill(String url, Map<String, String> reqData, int connectTimeout, int readTimeout) {
        buildReqData(reqData);
        String xmlReqData = XmlUtils.mapToXml(reqData);
        System.out.println("微信支付 -- 请求url:" + url + " ,请求参数:" + xmlReqData);
        String result = HttpUtils.post(url, xmlReqData, connectTimeout, readTimeout);
        System.out.println("微信支付 -- 响应数据:" + result);
        return result;
    }

    /**
     * 请求微信下载对账单
     * @param url
     * @param reqData
     * @return
     */
    private String requestWechatDownloadBill(String url, Map<String, String> reqData) {
        return requestWechatDownloadBill(url, reqData, 1000, 10000);
    }

    /**
     * 检查响应报文签名
     * @param resultMap
     */
    private void checkResposeSign(Map<String, String> resultMap) {
        String sign = resultMap.get("sign");
        if (StringUtils.isBlank(sign)) {
            throw new IllegalArgumentException("微信支付响应参数非法");
        }
        resultMap.remove("sign");

        String text = SignUtils.buildSortContent(resultMap) + "&key=" + wechatPayConfig.getApiKey();

        String signValue = "";
        if (SignTypeEnum.MD5.getType().equals(wechatPayConfig.getSignTypeEnum().getType())) {
            signValue = SignUtils.md5(text).toUpperCase();
        } else if (SignTypeEnum.HMAC_SHA256.getType().equals(wechatPayConfig.getSignTypeEnum().getType())){
            signValue = SignUtils.hmacSha256(text, wechatPayConfig.getApiKey()).toUpperCase();
        }

        if (!sign.toUpperCase().equals(signValue)) {
            throw new IllegalArgumentException("微信支付响应参数非法");
        }
    }


    private SSLContext getSSLContext(String base64Content, String certPassword) {
        SSLContext sslContext = null;
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byte[] bytes = Base64.decodeBase64(base64Content);
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            keyStore.load(byteArrayInputStream, certPassword.toCharArray());
            sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, certPassword.toCharArray()).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sslContext;
    }

}
