package pw.bits.weisper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import pw.bits.weisper.data.LoginManager;
import pw.bits.weisper.data.Oauth2Interceptor;
import pw.bits.weisper.data.WeiboModel;
import pw.bits.weisper.data.WeiboService;
import pw.bits.weisper.event.ClosePictureEvent;
import pw.bits.weisper.event.CloseUserEvent;
import pw.bits.weisper.event.OpenEditorEvent;
import pw.bits.weisper.event.OpenLinkEvent;
import pw.bits.weisper.event.OpenPictureEvent;
import pw.bits.weisper.event.OpenTopicEvent;
import pw.bits.weisper.event.OpenUserEvent;
import pw.bits.weisper.fragment.EditorFragment;
import pw.bits.weisper.fragment.PictureFlowFragment;
import pw.bits.weisper.fragment.PictureFragment;
import pw.bits.weisper.fragment.StatusFlowFragment;
import pw.bits.weisper.fragment.TopicFragment;
import pw.bits.weisper.fragment.UserFragment;
import pw.bits.weisper.store.FlowStatusStore;
import pw.bits.weisper.view.widget.Toolbar;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm = getSupportFragmentManager();
    private Fragment statusFlowFragment = new StatusFlowFragment();
    private Fragment pictureFlowFragment = new PictureFlowFragment();
    private static int[] themes = new int[]{R.style.Theme1, R.style.Theme2, R.style.Theme3};
    private static int current_theme = 0;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(themes[current_theme]);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupView();
        if (LoginManager.with(this).getAccessToken() != null) {
            init();
            fm.beginTransaction()
                    .setCustomAnimations(R.anim.slow_fade_in, R.anim.fast_fade_out, R.anim.slow_fade_in, R.anim.fast_fade_out)
                    .replace(R.id.main_layout, statusFlowFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void init() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
        Logger.init().logLevel(LogLevel.FULL);
        EventBus.getDefault().register(this);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Oauth2Interceptor(LoginManager.with(this).getAccessToken()))
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("EEE MMM dd HH:mm:ss z yyyy")
                        .create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .baseUrl("https://api.weibo.com/2/")
                .build();
        WeiboModel.init(retrofit.create(WeiboService.class));
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }

    private void setupView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, 0, 0);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch_status: {
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out, R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out)
                        .replace(R.id.main_layout, statusFlowFragment)
                        .commit();
                return true;
            }
            case R.id.action_switch_picture: {
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out, R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out)
                        .replace(R.id.main_layout, pictureFlowFragment)
                        .commit();
                return true;
            }
            case R.id.action_change_theme: {
                current_theme++;
                current_theme = current_theme % themes.length;
                recreate();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Subscribe
    public void onEvent(OpenUserEvent event) {
        for (Fragment fragment : fm.getFragments()) {
            String currentUser;
            try {
                currentUser = fragment.getArguments().getString("screen_name");
            } catch (NullPointerException ignored) {
                continue;
            }
            if (fragment instanceof UserFragment && event.getScreenName().equals(currentUser)) {
                return;
            }
        }
        Fragment userFragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("screen_name", event.getScreenName());
        userFragment.setArguments(bundle);
        fm.beginTransaction()
                .setCustomAnimations(R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out, R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out)
                .add(R.id.main_layout, userFragment)
                .addToBackStack(null)
                .commit();
        toolbar.setAvatar(WeiboModel.usersShow(event.getScreenName()));
    }

    @Subscribe
    public void onEvent(CloseUserEvent event) {
        toolbar.setAvatar(WeiboModel.usersShow(LoginManager.with(this).getUID()));
    }

    @Subscribe
    public void onEvent(OpenPictureEvent event) {
        Fragment pictureFragment = new PictureFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("pictures", event.getPictureUrls());
        bundle.putInt("position", event.getPosition());
        pictureFragment.setArguments(bundle);
        fm.beginTransaction()
                .setCustomAnimations(R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out, R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out)
                .add(R.id.main_layout, pictureFragment)
                .addToBackStack("picture")
                .commit();
    }

    @Subscribe
    public void onEvent(ClosePictureEvent event) {
        fm.popBackStackImmediate("picture", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Subscribe
    public void onEvent(OpenTopicEvent event) {
        Fragment topicFragment = new TopicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("topic", event.getTopic());
        topicFragment.setArguments(bundle);
        fm.beginTransaction()
                .setCustomAnimations(R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out, R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out)
                .add(R.id.main_layout, topicFragment)
                .addToBackStack(null)
                .commit();
    }

    @Subscribe
    public void onEvent(OpenEditorEvent event) {
        Fragment editorFragment = new EditorFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", event.getStatus().getId());
        bundle.putString("text", event.getStatus().getText());
        bundle.putString("user", event.getStatus().getScreenName());
        editorFragment.setArguments(bundle);
        fm.beginTransaction()
                .setCustomAnimations(R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out, R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out)
                .add(R.id.main_layout, editorFragment)
                .addToBackStack(null)
                .commit();
    }

    @Subscribe
    public void onEvent(OpenLinkEvent event) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getValue()));
        startActivity(browserIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        FlowStatusStore.instance.start();
        toolbar.setAvatar(WeiboModel.usersShow(LoginManager.with(this).getUID()));
    }

    @Override
    public void onPause() {
        super.onPause();
        FlowStatusStore.instance.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
