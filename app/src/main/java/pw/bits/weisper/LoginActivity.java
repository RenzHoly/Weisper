package pw.bits.weisper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rzh on 16/3/17.
 */
public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.login_webview)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupView();
    }

    private void setupView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(getString(R.string.redirect_uri))) {
                    view.stopLoading();
                    handleRedirectedUrl(url);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        webView.loadUrl(getOauthLoginUrl());
    }

    private void handleRedirectedUrl(String url) {
        Matcher token = Pattern.compile("(?<=access_token=)[^&]+").matcher(url);
        Matcher expires_in = Pattern.compile("(?<=expires_in=)[^&]+").matcher(url);
        Matcher uid = Pattern.compile("(?<=uid=)[^&]+").matcher(url);
        CharSequence message;
        if (token.find() && expires_in.find() && uid.find()) {
            Hawk.put("access-token", token.group());
            Hawk.put("uid", Long.parseLong(uid.group()));
            startActivity(new Intent(this, MainActivity.class));
            message = String.format(Locale.getDefault(), getString(R.string.login_success), Long.valueOf(expires_in.group()) / 60 / 60 / 24);
        } else {
            message = getString(R.string.login_failed);
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    private String getOauthLoginUrl() {
        return String.format(
                getString(R.string.oauth_url),
                getString(R.string.client_id),
                getString(R.string.redirect_uri),
                getString(R.string.key_hash),
                getString(R.string.package_name),
                getString(R.string.scope)
        );
    }
}
