package pw.bits.weisper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

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
        webView.loadUrl(getOauthLoginPage());
    }

    private void handleRedirectedUrl(String url) {
        Matcher token = Pattern.compile("(?<=access_token=)[^&]+").matcher(url);
        Matcher expires_in = Pattern.compile("(?<=expires_in=)[^&]+").matcher(url);
        if (token.find() && expires_in.find()) {
            Hawk.put("access-token", token.group());
            Toast.makeText(this, String.format("登录有效期 %d 天", Long.valueOf(expires_in.group()) / 60 / 60 / 24), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

    public String getOauthLoginPage() {
        String client_id = getString(R.string.client_id);
        String redirect_uri = getString(R.string.redirect_uri);
        String key_hash = getString(R.string.key_hash);
        String package_name = getString(R.string.package_name);
        String scope = getString(R.string.scope);
        return String.format("https://api.weibo.com/oauth2/authorize?client_id=%s&response_type=token&redirect_uri=%s&key_hash=%s&packagename=%s&display=mobile&scope=%s", client_id, redirect_uri, key_hash, package_name, scope);
    }
}
