### dohko-pay-sdk 支付sdk,支持微信、支付宝
> 基于微信官方SDK重构封装、支付宝SDK封装
---
### 项目进度
+ 微信支付

| 微信接口           | 描述      | 对应方法             |
| --------           | --------  | ---------------------|
| /pay/unifiedorder  | 统一下单  |  WechatPay.unifiedOrder()|
| /pay/orderquery    | 查询订单  |  WechatPay.queryOrder()|
| /secapi/pay/refund | 申请退款  |  WechatPay.refund()|
| /pay/refundquery   | 退款查询  |  WechatPay.refundQuery()|
| /pay/downloadbill  | 下载对账单|  WechatPay.downloadBill()|

微信支付有些接口需要证书才能请求，建议将证书转换成base64字符串，便于储存和容器化部署

dohko-pay-sdk 支持两种方式配置微信支付证书：文件路径方式 和 Base64字符串方式
```java
String filePath = "E:/certs/apiclient_cert.p12";
String base64String = IoUtils.file2Base64String(filePath);
System.out.println("证书内容："+ base64String);
```

+ 支付宝  
    暂无
    
    

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
     * 测试统一下单
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
     * 测试查询订单
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
     * 测试退款
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
        wechatPayConfig.setCertBase64Content("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");
             
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
     * 测试对账单下单
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

    暂无