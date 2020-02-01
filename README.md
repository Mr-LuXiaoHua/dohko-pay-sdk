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