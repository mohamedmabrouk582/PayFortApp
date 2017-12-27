package com.example.mohamed.payfortapp.PayFortBuilder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.payfort.fort.android.sdk.base.FortSdk;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.sdk.android.dependancies.base.FortInterfaces;
import com.payfort.sdk.android.dependancies.models.FortRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 18/12/2017.  time :11:14
 */

public class PayFort {

    //Request key for response
    public static final int RESPONSE_GET_TOKEN = 111;
    public static final int RESPONSE_PURCHASE = 222;
    public static final int RESPONSE_PURCHASE_CANCEL = 333;
    public static final int RESPONSE_PURCHASE_SUCCESS = 444;
    public static final int RESPONSE_PURCHASE_FAILURE = 555;



    //WS params
    private  static String KEY_MERCHANT_IDENTIFIER = "merchant_identifier";
    private final static String KEY_SERVICE_COMMAND = "service_command";
    private final static String KEY_DEVICE_ID = "device_id";
    private final static String KEY_LANGUAGE = "language";
    private final static String KEY_ACCESS_CODE = "access_code";
    private final static String KEY_SIGNATURE = "signature";
    private final static String KEY_PAYMENT_OPTION="payment_option";
    private final static String KEY_ECI="eci";
    private final static String KEY_ORDER_DESCRIPTION="order_description";
    private final static String KEY_CUSTOMER_IP="customer_ip";
    private final static String KEY_CUSTOMER_NAME="customer_name";
    private final static String KEY_PHONE_NUMBER="phone_number";
    private final static String KEY_SETTLEMENT_REFERANCE="settlement_reference";
    private final static String KEY_MERCHANT_EXTRA="merchant_ extra";

    //Commands
    public final static String AUTHORIZATION = "AUTHORIZATION";
    public final static String PURCHASE = "PURCHASE";
    private final static String SDK_TOKEN = "SDK_TOKEN";

    //Test token url
    private final static String TEST_TOKEN_URL = "https://sbpaymentservices.payfort.com/FortAPI/paymentApi";
    //Live token url
    public final static String LIVE_TOKEN_URL = "https://paymentservices.payfort.com/FortAPI/paymentApi";
    //Make a change for live or test token url from WS_GET_TOKEN variable
    private final static String WS_GET_TOKEN = TEST_TOKEN_URL;

    public final static String LANGUAGE_TYPE = "en";//Arabic - ar //English - en
    private final static String SHA_TYPE = "SHA-256";



    public interface IPaymentRequestCallBack {
        void onPaymentRequestResponse(int responseType, PayFortData responseData);
    }
    private Activity activity;
    private PayFortData payFortData;
    private String merchantIdentifier;
    private String accessCode;
    private String currencyType;
    private String paymentOption;
    private String eci;
    private String orderDescription;
    private String customerIp;
    private String customerName;
    private String phoneNumber;
    private String settlementReference;
    private String merchantExtra;
    private String sdkToken;
    private String SHA_REQUEST_PHRASE;
    private String SHA_RESPONSE_PHRASE;


    public String getSHA_REQUEST_PHRASE() {
        return SHA_REQUEST_PHRASE;
    }

    public String getSHA_RESPONSE_PHRASE() {
        return SHA_RESPONSE_PHRASE;
    }

    public Activity getActivity() {
        return activity;
    }

    public PayFortData getPayFortData() {
        return payFortData;
    }

    public String getMerchantIdentifier() {
        return merchantIdentifier;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public String getEci() {
        return eci;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public String getCustomerIp() {
        return customerIp;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSettlementReference() {
        return settlementReference;
    }

    public String getMerchantExtra() {
        return merchantExtra;
    }

    public String getSdkToken() {
        return sdkToken;
    }

    private PayFort(PayFortBuilder builder){
        this.activity=builder.activity;
        this.payFortData=builder.payFortData;
        this.merchantIdentifier=builder.merchantIdentifier;
        this.accessCode=builder.accessCode;
        this.currencyType=builder.currencyType;
        this.paymentOption=builder.paymentOption;
        this.eci=builder.eci;
        this.orderDescription=builder.orderDescription;
        this.customerIp=builder.customerIp;
        this.customerName=builder.customerName;
        this.phoneNumber=builder.phoneNumber;
        this.settlementReference=builder.settlementReference;
        this.merchantExtra=builder.merchantExtra;
        this.sdkToken=builder.sdkToken;
        this.SHA_REQUEST_PHRASE=builder.SHA_REQUEST_PHRASE;
        this.SHA_RESPONSE_PHRASE=builder.SHA_RESPONSE_PHRASE;
    }

    /**
     * builder
     */
    public static class PayFortBuilder{
        private Activity activity;
        private PayFortData payFortData;
        private String merchantIdentifier;
        private String accessCode;
        private String currencyType;
        private String paymentOption;
        private String eci;
        private String orderDescription;
        private String customerIp;
        private String customerName;
        private String phoneNumber;
        private String settlementReference;
        private String merchantExtra;
        private String sdkToken;
        private String SHA_REQUEST_PHRASE="asdadfered";
        private String SHA_RESPONSE_PHRASE="asdacsderr";
        private FortCallBackManager fortCallBackManager;
        private IPaymentRequestCallBack iPaymentRequestCallBack;
        private  Gson gson;
        private ProgressDialog mProgressDialog;


        /**
         *
         * @param payFortData
         * @param merchantIdentifier
         * @param accessCode
         * @param currencyType
         */

        public PayFortBuilder(Activity activity,FortCallBackManager fortCallBackManager,IPaymentRequestCallBack iPaymentRequestCallBack,PayFortData payFortData, String merchantIdentifier, String accessCode, String currencyType) {
            this.activity=activity;
            this.payFortData = payFortData;
            this.merchantIdentifier = merchantIdentifier;
            this.accessCode = accessCode;
            this.currencyType = currencyType;
            this.fortCallBackManager=fortCallBackManager;
            this.iPaymentRequestCallBack=iPaymentRequestCallBack;
        }

        public PayFortBuilder setPaymentOption(String paymentOption) {
            this.paymentOption = paymentOption;
            return this;
        }

        public PayFortBuilder setEci(String eci) {
            this.eci = eci;
            return this;
        }

        public PayFortBuilder setOrderDescription(String orderDescription) {
            this.orderDescription = orderDescription;
            return this;
        }

        public PayFortBuilder setCustomerIp(String customerIp) {
            this.customerIp = customerIp;
            return this;
         }

        public PayFortBuilder setCustomerName(String customerName) {
            this.customerName = customerName;
          return this;
        }

        public PayFortBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PayFortBuilder setSettlementReference(String settlementReference) {
            this.settlementReference = settlementReference;
            return this;
        }

        public PayFortBuilder setMerchantExtra(String merchantExtra) {
            this.merchantExtra = merchantExtra;
            return this;
        }

        public PayFortBuilder setSHA_REQUEST_PHRASE(String SHA_REQUEST_PHRASE) {
            this.SHA_REQUEST_PHRASE = SHA_REQUEST_PHRASE;
            return this;
        }

        public PayFortBuilder setSHA_RESPONSE_PHRASE(String SHA_RESPONSE_PHRASE) {
            this.SHA_RESPONSE_PHRASE = SHA_RESPONSE_PHRASE;
            return this;
        }


        private void requestPurchase() {
            try {
                FortSdk.getInstance().registerCallback(activity, getPurchaseFortRequest(), FortSdk.ENVIRONMENT.TEST, RESPONSE_PURCHASE,
                        fortCallBackManager,true, new FortInterfaces.OnTnxProcessed() {
                            @Override
                            public void onCancel(Map<String, String> requestParamsMap, Map<String,
                                    String> responseMap) {
                                JSONObject response = new JSONObject(responseMap);
                                PayFortData payFortData = gson.fromJson(response.toString(), PayFortData.class);
                                payFortData.paymentResponse = response.toString();
                                Log.e("Cancel Response", response.toString());
                                if (iPaymentRequestCallBack != null) {
                                    iPaymentRequestCallBack.onPaymentRequestResponse(RESPONSE_PURCHASE_CANCEL, payFortData);
                                }
                            }

                            @Override
                            public void onSuccess(Map<String, String> requestParamsMap, Map<String,
                                    String> fortResponseMap) {
                                JSONObject response = new JSONObject(fortResponseMap);
                                PayFortData payFortData = gson.fromJson(response.toString(), PayFortData.class);
                                payFortData.paymentResponse = response.toString();
                                Log.e("Success Response", response.toString());
                                if (iPaymentRequestCallBack != null) {
                                    iPaymentRequestCallBack.onPaymentRequestResponse(RESPONSE_PURCHASE_SUCCESS, payFortData);
                                }
                            }

                            @Override
                            public void onFailure(Map<String, String> requestParamsMap, Map<String,
                                    String> fortResponseMap) {
                                JSONObject response = new JSONObject(fortResponseMap);
                                PayFortData payFortData = gson.fromJson(response.toString(), PayFortData.class);
                                payFortData.paymentResponse = response.toString();
                                Log.e("Failure Response", response.toString());
                                if (iPaymentRequestCallBack != null) {
                                    iPaymentRequestCallBack.onPaymentRequestResponse(RESPONSE_PURCHASE_FAILURE, payFortData);
                                }
                            }
                        });
            } catch (Exception e) {
                Log.d("error", e.getMessage() + "");
                e.printStackTrace();
            }
        }

        private FortRequest getPurchaseFortRequest() {
            FortRequest fortRequest = new FortRequest();
            if (payFortData != null) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("amount", String.valueOf(payFortData.amount));
                parameters.put("command", payFortData.command);
                parameters.put("currency", payFortData.currency);
                parameters.put("customer_email", payFortData.customerEmail);
                parameters.put("language", payFortData.language);
                parameters.put("merchant_reference", payFortData.merchantReference);
                parameters.put("sdk_token", sdkToken);
                addAdditionalData(parameters);

                fortRequest.setRequestMap(parameters);
            }
            return fortRequest;
        }

        private void addAdditionalData(HashMap<String,String> parameters){
            if (paymentOption!=null){
                parameters.put(KEY_PAYMENT_OPTION,paymentOption);
            }

            if (eci !=null){
                parameters.put(KEY_ECI,eci);
            }

            if (orderDescription !=null){
                parameters.put(KEY_ORDER_DESCRIPTION,orderDescription);
            }


            if (customerIp !=null){
                parameters.put(KEY_CUSTOMER_IP,customerIp);
            }

            if (customerName !=null){
                parameters.put(KEY_CUSTOMER_NAME,customerName);
            }

            if (settlementReference !=null){
                parameters.put(KEY_SETTLEMENT_REFERANCE,settlementReference);
            }

            if (merchantExtra !=null){
                parameters.put(KEY_MERCHANT_EXTRA,merchantExtra);
            }

            Log.d("datadddd", parameters.toString() + "");

        }


        private class GetTokenFromServer extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                 mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... postParams) {
                String response = "";
                try {
                    HttpURLConnection conn;
                    URL url = new URL(postParams[0].replace(" ", "%20"));
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("content-type", "application/json");

                    String str = getTokenParams();
                    byte[] outputInBytes = str.getBytes("UTF-8");
                    OutputStream os = conn.getOutputStream();
                    os.write(outputInBytes);
                    os.close();
                    conn.connect();

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = conn.getInputStream();
                        response = convertStreamToString(inputStream);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return response;
            }


            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                mProgressDialog.hide();
                Log.d("Response", response + "");
                try {
                    PayFortData payFortData = gson.fromJson(response, PayFortData.class);
                    if (!TextUtils.isEmpty(payFortData.sdkToken)) {
                        sdkToken = payFortData.sdkToken;
                        requestPurchase();
                    } else {
                        payFortData.paymentResponse = response;
                        iPaymentRequestCallBack.onPaymentRequestResponse(RESPONSE_GET_TOKEN, payFortData);
                    }
                } catch (Exception e) {
                    Log.d("ddddd", response + "");

                    e.printStackTrace();
                }
            }
        }


        public String getTokenParams() {
            JSONObject jsonObject = new JSONObject();
            try {
                String device_id = FortSdk.getDeviceId(activity);
                String concatenatedString = SHA_REQUEST_PHRASE +
                        KEY_ACCESS_CODE + "=" + accessCode +
                        KEY_DEVICE_ID + "=" + device_id +
                        KEY_LANGUAGE + "=" + LANGUAGE_TYPE +
                        KEY_MERCHANT_IDENTIFIER + "=" + merchantIdentifier +
                        KEY_SERVICE_COMMAND + "=" + SDK_TOKEN +
                        SHA_REQUEST_PHRASE;

                 Log.d("ss", concatenatedString + "");

                jsonObject.put(KEY_SERVICE_COMMAND, SDK_TOKEN);
                jsonObject.put(KEY_MERCHANT_IDENTIFIER, merchantIdentifier);
                jsonObject.put(KEY_ACCESS_CODE, accessCode);
                String signature = getSignatureSHA256(concatenatedString);
                jsonObject.put(KEY_SIGNATURE, signature);
                jsonObject.put(KEY_DEVICE_ID, device_id);
                jsonObject.put(KEY_LANGUAGE, LANGUAGE_TYPE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return String.valueOf(jsonObject);
        }


        private static String convertStreamToString(InputStream inputStream) {
            if (inputStream == null)
                return null;
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream), 1024);
                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        private static String getSignatureSHA256(String s) {
            try {
                // Create MD5 Hash
                MessageDigest digest = MessageDigest.getInstance(SHA_TYPE);
                digest.update(s.getBytes());
                byte messageDigest[] = digest.digest();

                return String.format("%0" + (messageDigest.length * 2) + 'x', new BigInteger(1, messageDigest));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return "";
        }

        private void setPayFortServices(PayFort payFort){
            mProgressDialog=new ProgressDialog(activity);
            mProgressDialog.setMessage("Processing your payment\nPlease wait...");
            mProgressDialog.setCancelable(false);
            gson = new Gson();
            sdkToken="";
            this.payFortData = payFort.getPayFortData();
            try {
                new GetTokenFromServer().execute(WS_GET_TOKEN);

            }catch ( Exception e){

            }
        }

        public PayFort build(){
            PayFort payFort=new PayFort(this);
            setPayFortServices(payFort);
            return payFort;
        }
    }
}
