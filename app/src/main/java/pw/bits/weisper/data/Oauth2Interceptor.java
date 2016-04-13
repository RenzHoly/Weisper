package pw.bits.weisper.data;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rzh on 16/4/12.
 */
public class Oauth2Interceptor implements Interceptor {
    private final String accessToken;

    public Oauth2Interceptor(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (accessToken != null) {
            builder.header("Authorization", String.format("OAuth2 %s", accessToken));
        }
        return chain.proceed(builder.build());
    }
}
