package in.co.vsys.myssksamaj;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class PrivacyActivity extends AppCompatActivity {

    WebView webview1;
    ProgressBar progressbar_webview;
    private float m_downX;

    String webviewtype;

    //http://app.myssksamaj.com/privacy-policy.aspx
    //http://app.myssksamaj.com/Contactus.aspx
    //http://app.myssksamaj.com/AboutUs.aspx

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            webviewtype = bundle.getString("webviewtag");
        }

        webview1=(WebView)findViewById(R.id.webview1);
        progressbar_webview=(ProgressBar) findViewById(R.id.progressbar_webview);




        initWebView();

        Log.d("mytag", "onCreate: "+webviewtype);
        if(webviewtype.equals("aboutus")){
            webview1.loadUrl("http://app.myssksamaj.com/AboutUs.aspx");
        }else if(webviewtype.equals("privacypolicy")){
            webview1.loadUrl("http://app.myssksamaj.com/privacy-policy.aspx");
        }else if(webviewtype.equals("contactus")){
            webview1.loadUrl("http://app.myssksamaj.com/Contactus.aspx");
        }


    }

    private void initWebView() {
        webview1.setWebChromeClient(new MyWebChromeClient(this));
        webview1.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressbar_webview.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webview1.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar_webview.setVisibility(View.GONE);
                invalidateOptionsMenu();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressbar_webview.setVisibility(View.GONE);
                invalidateOptionsMenu();
            }
        });
        webview1.clearCache(true);
        webview1.clearHistory();
        webview1.getSettings().setJavaScriptEnabled(true);
        webview1.setHorizontalScrollBarEnabled(false);
        webview1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // save the x
                        m_downX = event.getX();
                    }
                    break;

                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        // set x so that it doesn't move
                        event.setLocation(m_downX, event.getY());
                    }
                    break;
                }

                return false;
            }
        });
    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;
        public MyWebChromeClient(PrivacyActivity privacyActivity) {

            super();
            this.context = privacyActivity;
        }
    }
}
