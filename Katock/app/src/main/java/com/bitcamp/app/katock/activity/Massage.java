package com.bitcamp.app.katock.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.bitcamp.app.katock.R;

public class Massage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.massage);
        final Context context=Massage.this;
        WebView webView = findViewById(R.id.webView);
        WebSettings settings=webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);//자바스크립트 허용
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new Object(){
            @android.webkit.JavascriptInterface
            public String toString() {
                return "Mybrid";
            }
            @android.webkit.JavascriptInterface
            public void showToast(String message){
                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
            }
            @android.webkit.JavascriptInterface
            public void sendSMS(String phone,String message){
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phone,null,message,null,null);
            }
        },"Mybrid");
//        webView.loadUrl("https://www.naver.com/");
        webView.loadUrl("file:///android_asset/www/Index.html");
    }
}
