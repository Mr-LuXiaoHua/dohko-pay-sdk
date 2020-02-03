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
 | alipay.trade.fastpay.refund.query | 退款查询  | Alipay.queryRefund()       |
 | alipay.data.dataservice.bill.downloadurl.query | 获取对账单下载地址 |   Alipay.getBillDownloadUrl()     |

    
    
    
---
### 使用案例
#### 微信支付

```java
       public class WechatPayTest {
       
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
            * 微信支付-统一下单 （小程序支付、JS支付、APP支付、扫码支付）
            */
           @Test
           public void testUnifiedOrder() {
               // 设置配置信息
               WechatPayConfig wechatPayConfig = new WechatPayConfig();
               // 设置普通商户模式
               wechatPayConfig.setWechatPayMchTypeEnum(WechatPayMchTypeEnum.NORMAL);
               wechatPayConfig.setAppId("应用id");
               wechatPayConfig.setApiKey("应用API-KEY");
               wechatPayConfig.setMchId("商户id");
               wechatPayConfig.setSignTypeEnum(SignTypeEnum.MD5);
       
               // 组装请求数据
               Map<String, String> reqData = new HashMap<>();
               reqData.put("nonce_str", RandomStringUtils.randomAlphabetic(20));
               reqData.put("body", "测试商品");
               reqData.put("out_trade_no", "202002011246508001");
               reqData.put("total_fee", "1");
               reqData.put("spbill_create_ip", "127.0.0.1");
               reqData.put("notify_url", "https://127.0.0.1/notify/wechat/pay-result");
               reqData.put("trade_type", WechatPayConst.TradeType.JSAPI);
               reqData.put("openid", "oJF384wBwxcrgh_AzSao6qXdew");
       
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
               wechatPayConfig.setAppId("应用id");
               wechatPayConfig.setApiKey("应用API-KEY");
               wechatPayConfig.setMchId("商户id");
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
               wechatPayConfig.setAppId("应用id");
               wechatPayConfig.setApiKey("应用API-KEY");
               wechatPayConfig.setMchId("商户id");
               wechatPayConfig.setCertPassword("1512345678");
       
               /**
                * 证书设置
                * wechatPayConfig.setCertPath 和 wechatPayConfig.setCertBase64Content
                * 两种方式选其中一种方式即可
                */
               // 方式一：设置证书所在路径
               // wechatPayConfig.setCertPath("E:/certs/apiclient_cert.p12");
               // 方式二：设置证书内容
               wechatPayConfig.setCertBase64Content("Base64字符串格式证书内容");
       
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
               wechatPayConfig.setAppId("应用id");
               wechatPayConfig.setApiKey("应用API-KEY");
               wechatPayConfig.setMchId("商户id");
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
               wechatPayConfig.setAppId("应用id");
               wechatPayConfig.setApiKey("应用API-KEY");
               wechatPayConfig.setMchId("商户id");
               wechatPayConfig.setSignTypeEnum(SignTypeEnum.MD5);
       
               Map<String, String> reqData = new HashMap<>();
               reqData.put("nonce_str", RandomStringUtils.randomAlphabetic(20));
               reqData.put("bill_date", "20191230");
               reqData.put("bill_type", "ALL");
       
               WechatPay wechatPay = new WechatPay(wechatPayConfig);
               String result = wechatPay.downloadBill(reqData);
       
       
           }
       }
```
#### 支付宝

```java
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
```