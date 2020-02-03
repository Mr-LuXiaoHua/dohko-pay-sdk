### dohko-pay-sdk 支付sdk,支持微信、支付宝
> 基于微信官方SDK重构封装、支付宝SDK封装


### 项目进度
+ 微信支付
    + 微信支付文档 [https://pay.weixin.qq.com/wiki/doc/api/index.html]

| 微信接口           | 描述      | 对应方法             |
| --------           | --------  | ---------------------|
| /pay/unifiedorder  | 统一下单  |  WechatPay.unifiedOrder()|
| /pay/orderquery    | 查询订单  |  WechatPay.queryOrder()|
| /secapi/pay/refund | 申请退款  |  WechatPay.refund()|
| /pay/refundquery   | 退款查询  |  WechatPay.refundQuery()|
| /pay/downloadbill  | 下载对账单|  WechatPay.downloadBill()|

微信支付有些接口需要证书才能请求，建议将证书转换成base64字符串，便于储存和容器化部署

dohko-pay-sdk 支持两种方式配置微信支付证书：文件路径方式 和 Base64字符串方式




+ 支付宝  
    + 支付宝文档 
        + [https://docs.open.alipay.com/api_1/]
        + [https://docs.open.alipay.com/54/106370]
        
 | 支付宝接口           | 描述      | 对应方法             |
 | --------             | --------  | ---------------------|
 | alipay.trade.app.pay | APP支付   |  Alipay.appPay()     |
 | alipay.trade.wap.pay | H5支付  |  Alipay.h5Pay()      |
 | alipay.trade.query | 交易查询  |  Alipay.queryTrade()      |
 | alipay.trade.refund | 退款申请  |  Alipay.refund()      |
 | alipay.trade.fastpay.refund.query | 退款查询  |        |

    
    
    

### 使用案例
#### 微信支付
> 以下案例商户id、商户号、密钥均为虚构，使用时请替换
```java
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
     * 微信支付-统一下单
     */
    @Test
    public void testUnifiedOrder() {
        // 设置配置信息
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        wechatPayConfig.setWechatPayMchTypeEnum(WechatPayMchTypeEnum.NORMAL);
        wechatPayConfig.setAppId("wxabc1234567884");
        wechatPayConfig.setApiKey("Ltsdskddeee111111111ddsds0000000");
        wechatPayConfig.setMchId("1512345678");
        wechatPayConfig.setSignTypeEnum(SignTypeEnum.MD5);

        // 组装请求数据
        Map<String, String> reqData = new HashMap<>();
        reqData.put("nonce_str", RandomStringUtils.randomAlphabetic(20));
        reqData.put("body", "测试商品");
        reqData.put("out_trade_no", "202002011246508001");
        reqData.put("total_fee", "1");
        reqData.put("spbill_create_ip", "127.0.0.1");
        reqData.put("notify_url", "https://127.0.0.1/notify/wechat/pay-result");
        reqData.put("trade_type", "JSAPI");
        reqData.put("openid", "oJw3de443wwB1XavFhnW_12345678");

        WechatPay wechatPay = new WechatPay(wechatPayConfig);
        Map<String, String> resultMap = wechatPay.unifiedOrder(reqData);


    }


    /**
     * 微信支付-查询订单
     */
    @Test
    public void testQueryOrder() {
        // 设置配置信息
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        wechatPayConfig.setWechatPayMchTypeEnum(WechatPayMchTypeEnum.NORMAL);
        wechatPayConfig.setAppId("wxabc1234567884");
        wechatPayConfig.setApiKey("Ltsdskddeee111111111ddsds0000000");
        wechatPayConfig.setMchId("1512345678");
        wechatPayConfig.setSignTypeEnum(SignTypeEnum.MD5);

        Map<String, String> reqData = new HashMap<>();
        reqData.put("nonce_str", RandomStringUtils.randomAlphabetic(20));
        reqData.put("out_trade_no", "202002011246508001");

        WechatPay wechatPay = new WechatPay(wechatPayConfig);
        Map<String, String> resultMap = wechatPay.queryOrder(reqData);


    }


    /**
     * 微信支付-退款
     */
    @Test
    public void testRefund() {
        // 设置配置信息
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        wechatPayConfig.setWechatPayMchTypeEnum(WechatPayMchTypeEnum.NORMAL);
        wechatPayConfig.setAppId("wxabc1234567884");
        wechatPayConfig.setApiKey("Ltsdskddeee111111111ddsds0000000");
        wechatPayConfig.setMchId("1512345678");
        wechatPayConfig.setCertPassword("1512345678");
        /**
         * 证书设置
         * wechatPayConfig.setCertPath 和 wechatPayConfig.setCertBase64Content 
         * 两种方式选其中一种方式即可
         */
        // 方式一：设置证书所在路径
        // wechatPayConfig.setCertPath("E:/certs/apiclient_cert.p12");
        // 方式二：设置证书内容
        wechatPayConfig.setCertBase64Content("MIIKmgIBAzCCCmQGCSq...");
             
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
     * 微信支付-退款查询
     */
    @Test
    public void testRefundQuery() {
        // 设置配置信息
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        wechatPayConfig.setWechatPayMchTypeEnum(WechatPayMchTypeEnum.NORMAL);
        wechatPayConfig.setAppId("wxabc1234567884");
        wechatPayConfig.setApiKey("Ltsdskddeee111111111ddsds0000000");
        wechatPayConfig.setMchId("1512345678");
        wechatPayConfig.setSignTypeEnum(SignTypeEnum.MD5);

        Map<String, String> reqData = new HashMap<>();
        reqData.put("nonce_str", RandomStringUtils.randomAlphabetic(20));
        reqData.put("out_refund_no", "102002011246508001");

        WechatPay wechatPay = new WechatPay(wechatPayConfig);
        Map<String, String> resultMap = wechatPay.refundQuery(reqData);

        Assert.assertEquals(WechatPayConst.Resp.SUCCESS, resultMap.get("result_code"));

    }

    /**
     * 微信支付-对账单下单
     */
    @Test
    public void testDownloadBill() {
        // 设置配置信息
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        wechatPayConfig.setWechatPayMchTypeEnum(WechatPayMchTypeEnum.NORMAL);
        wechatPayConfig.setAppId("wxabc1234567884");
        wechatPayConfig.setApiKey("Ltsdskddeee111111111ddsds0000000");
        wechatPayConfig.setMchId("1512345678");
        wechatPayConfig.setSignTypeEnum(SignTypeEnum.MD5);

        Map<String, String> reqData = new HashMap<>();
        reqData.put("nonce_str", RandomStringUtils.randomAlphabetic(20));
        reqData.put("bill_date", "20191230");
        reqData.put("bill_type", "ALL");

        WechatPay wechatPay = new WechatPay(wechatPayConfig);
        String result = wechatPay.downloadBill(reqData);


    }
```
#### 支付宝
> 以下的appid、公私钥为虚构，使用时请替换
```java
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
```