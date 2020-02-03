package com.dohko.pay.sdk.wechat;

/**
 * @desc 微信支付常量定义
 * @author luxiaohua
 */
public class WechatPayConst {

    /**
     * url常量
     */
    public class Url {
        /**
         * 微信支付正式环境域名
         */
        private static final String HOST = "https://api.mch.weixin.qq.com";
        /**
         * 统一下单URL
         */
        public static final String UNIFIED_ORDER_URL = HOST + "/pay/unifiedorder";

        /**
         * 查询订单URL
         */
        public static final String QUERY_ORDER_URL = HOST + "/pay/orderquery";

        /**
         * 申请退款URL
         */
        public static final String REFUND_URL = HOST + "/secapi/pay/refund";

        /**
         * 退款查询URL
         */
        public static final String REFUND_QUERY_URL = HOST + "/pay/refundquery";

        /**
         * 下载对账单
         */
        public static final String DOWNLOAD_BILL_URL = HOST + "/pay/downloadbill";



    }

    /**
     * 响应常量
     */
    public class Resp {
        /**
         * SUCCESS
         */
        public static final String SUCCESS = "SUCCESS";
    }


    /**
     * 支付方式
     */
    public class TradeType {
        /**
         * 小程序、JS 支付
         */
        public static final String JSAPI = "JSAPI";

        /**
         * NATIVE-扫码支付
         */
        public static final String NATIVE = "NATIVE";

        /**
         * APP支付
         */
        public static final String APP = "APP";
    }







}
