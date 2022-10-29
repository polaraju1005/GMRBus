package com.example.gmrbus.gateway.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gmrbus.R;
import com.example.gmrbus.gateway.utility.AvenuesParams;
import com.example.gmrbus.gateway.utility.Constants;
import com.example.gmrbus.gateway.utility.LoadingDialog;
import com.example.gmrbus.gateway.utility.RSAUtility;
import com.example.gmrbus.gateway.utility.ServiceUtility;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {


    Intent mainIntent;
    String html, encVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mainIntent = getIntent();

        //get rsa key method
        get_RSA_key(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE),mainIntent.getStringExtra(AvenuesParams.ORDER_ID));
    }
    /**
     * Async task class to get json by making HTTP call
     * */
    private class RenderView extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            LoadingDialog.showLoadingDialog(WebViewActivity.this, "Loading...");
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance



            if(!ServiceUtility.chkNull(vResponse).equals("")
                    && !ServiceUtility.chkNull(vResponse).toString().contains("ERROR")){
                StringBuilder vEncVal = new StringBuilder("");
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CVV, mainIntent.getStringExtra(AvenuesParams.CVV)));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, mainIntent.getStringExtra(AvenuesParams.AMOUNT)));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, mainIntent.getStringExtra(AvenuesParams.CURRENCY)));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CARD_NUMBER, mainIntent.getStringExtra(AvenuesParams.CARD_NUMBER)));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CUSTOMER_IDENTIFIER, mainIntent.getStringExtra(AvenuesParams.CUSTOMER_IDENTIFIER)));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.EXPIRY_YEAR, mainIntent.getStringExtra(AvenuesParams.EXPIRY_YEAR)));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.EXPIRY_MONTH, mainIntent.getStringExtra(AvenuesParams.EXPIRY_MONTH)));

                encVal = RSAUtility.encrypt(vEncVal.substring(0,vEncVal.length()-1), vResponse);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            LoadingDialog.cancelLoading();

            @SuppressWarnings("unused")
            class MyJavaScriptInterface
            {
                @JavascriptInterface
                public void processHTML(String html)
                {
                    // process the html as needed by the app
                    String status = null;
                    if(html.indexOf("Failure")!=-1){
                        status = "Transaction Declined!";
                    }else if(html.indexOf("Success")!=-1){
                        status = "Transaction Successful!";
                    }else if(html.indexOf("Aborted")!=-1){
                        status = "Transaction Cancelled!";
                    }else{
                        status = "Status Not Known!";
                    }
                    //Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
                    intent.putExtra("transStatus", status);
                    startActivity(intent);
                }
            }

            final WebView webview = (WebView) findViewById(R.id.webview);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
            webview.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(webview, url);
                    LoadingDialog.cancelLoading();
                    if(url.indexOf("/ccavResponseHandler.jsp")!=-1){
                        webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                    }
                }
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    LoadingDialog.showLoadingDialog(WebViewActivity.this, "Loading...");
                }

            });

            /* An instance of this class will be registered as a JavaScript interface */
            StringBuffer params = new StringBuffer();
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ACCESS_CODE,mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_ID,mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ORDER_ID,mainIntent.getStringExtra(AvenuesParams.ORDER_ID)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.REDIRECT_URL,mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.CANCEL_URL,mainIntent.getStringExtra(AvenuesParams.CANCEL_URL)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.LANGUAGE,"EN"));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_NAME,mainIntent.getStringExtra(AvenuesParams.BILLING_NAME)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_ADDRESS,mainIntent.getStringExtra(AvenuesParams.BILLING_ADDRESS)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_CITY,mainIntent.getStringExtra(AvenuesParams.BILLING_CITY)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_STATE,mainIntent.getStringExtra(AvenuesParams.BILLING_STATE)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_ZIP,mainIntent.getStringExtra(AvenuesParams.BILLING_ZIP)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_COUNTRY,mainIntent.getStringExtra(AvenuesParams.BILLING_COUNTRY)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_TEL,mainIntent.getStringExtra(AvenuesParams.BILLING_TEL)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_EMAIL,mainIntent.getStringExtra(AvenuesParams.BILLING_EMAIL)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.DELIVERY_NAME,mainIntent.getStringExtra(AvenuesParams.DELIVERY_NAME)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.DELIVERY_ADDRESS,mainIntent.getStringExtra(AvenuesParams.DELIVERY_ADDRESS)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.DELIVERY_CITY,mainIntent.getStringExtra(AvenuesParams.DELIVERY_CITY)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.DELIVERY_STATE,mainIntent.getStringExtra(AvenuesParams.DELIVERY_STATE)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.DELIVERY_ZIP,mainIntent.getStringExtra(AvenuesParams.DELIVERY_ZIP)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.DELIVERY_COUNTRY,mainIntent.getStringExtra(AvenuesParams.DELIVERY_COUNTRY)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.DELIVERY_TEL,mainIntent.getStringExtra(AvenuesParams.DELIVERY_TEL)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_PARAM1,"additional Info."));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_PARAM2,"additional Info."));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_PARAM3,"additional Info."));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_PARAM4,"additional Info."));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.PAYMENT_OPTION,mainIntent.getStringExtra(AvenuesParams.PAYMENT_OPTION)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.CARD_TYPE,mainIntent.getStringExtra(AvenuesParams.CARD_TYPE)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.CARD_NAME,mainIntent.getStringExtra(AvenuesParams.CARD_NAME)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.DATA_ACCEPTED_AT,mainIntent.getStringExtra(AvenuesParams.DATA_ACCEPTED_AT)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ISSUING_BANK,mainIntent.getStringExtra(AvenuesParams.ISSUING_BANK)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ENC_VAL, URLEncoder.encode(encVal)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.EMI_PLAN_ID,mainIntent.getStringExtra(AvenuesParams.EMI_PLAN_ID)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.EMI_TENURE_ID,mainIntent.getStringExtra(AvenuesParams.EMI_TENURE_ID)));
            if(mainIntent.getStringExtra(AvenuesParams.SAVE_CARD)!=null)
                params.append(ServiceUtility.addToPostParams(AvenuesParams.SAVE_CARD,mainIntent.getStringExtra(AvenuesParams.SAVE_CARD)));

            String vPostParams = params.substring(0,params.length()-1);
            try {
                webview.postUrl(Constants.TRANS_URL,  vPostParams.getBytes());
            } catch (Exception e) {
                showToast("Exception occured while opening webview.");
            }
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
    }


    public void get_RSA_key(final String ac, final String od) {
        LoadingDialog.showLoadingDialog(WebViewActivity.this, "Loading...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, mainIntent.getStringExtra(AvenuesParams.RSA_KEY_URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(WebViewActivity.this,response,Toast.LENGTH_LONG).show();
                        LoadingDialog.cancelLoading();
                        vResponse = response;
                        if (vResponse.contains("!ERROR!")) {
                            show_alert(vResponse);
                        } else {
                            new RenderView().execute();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LoadingDialog.cancelLoading();
                        //Toast.makeText(WebViewActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(AvenuesParams.ACCESS_CODE, ac);
                params.put(AvenuesParams.ORDER_ID, od);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void show_alert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                WebViewActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Error!!!");

        // Setting Dialog Message
        if(msg.contains("\n"))
            msg=msg.replaceAll("\\\n","");
        alertDialog.setMessage(msg);


        // Setting OK Button
        alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    String vResponse;
}