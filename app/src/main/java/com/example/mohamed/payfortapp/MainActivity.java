package com.example.mohamed.payfortapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mohamed.payfortapp.PayFortBuilder.PayFort;
import com.example.mohamed.payfortapp.PayFortBuilder.PayFortData;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;

public class MainActivity extends AppCompatActivity implements PayFort.IPaymentRequestCallBack{
     FortCallBackManager backManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backManager=FortCallBackManager.Factory.create();
    }

    public void payFort(View view) {
        PayFort payFort=new PayFort.PayFortBuilder(this,backManager,this,setDATA("SAR"),
                "oIHIRavn",
                "sIAyTmmWvU9Ib2PFhSpy",
                "SAR" )
                .build();

        Log.d("payfort", payFort.getPayFortData().toString() + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        backManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onPaymentRequestResponse(int responseType, PayFortData responseData) {
        Toast.makeText(this, responseData.responseMessage, Toast.LENGTH_SHORT).show();
    }
    private PayFortData setDATA(String CURRENCY_TYPE){
        PayFortData payFortData=new PayFortData();
        payFortData.amount = String.valueOf("1010");// Multiplying with 100, bcz amount should not be in decimal format
        payFortData.command = PayFort.PURCHASE;
        payFortData.currency =CURRENCY_TYPE;
        payFortData.customerEmail = "mohamed@gmail.com" ;
        payFortData.language = "en";
        payFortData.merchantReference = "MSNo-124-" + String.valueOf(System.currentTimeMillis());
        return payFortData;
    }

}
