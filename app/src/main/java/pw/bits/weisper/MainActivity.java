package pw.bits.weisper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import pw.bits.weisper.event.CloseUserEvent;
import pw.bits.weisper.event.OpenPictureEvent;
import pw.bits.weisper.event.OpenTopicEvent;
import pw.bits.weisper.event.OpenUserEvent;
import pw.bits.weisper.fragment.PictureFlowFragment;
import pw.bits.weisper.fragment.PictureFragment;
import pw.bits.weisper.fragment.StatusFlowFragment;
import pw.bits.weisper.fragment.TopicFragment;
import pw.bits.weisper.fragment.UserFragment;
import pw.bits.weisper.library.StatusData;
import pw.bits.weisper.library.bean.User;
import pw.bits.weisper.store.FlowStatusStore;
import rx.Observable;
import rx.Subscriber;

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

    @Bind(R.id.toolbar_avatar)
    ImageView toolbar_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(themes[current_theme]);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupView();
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();
        if (Hawk.get("access-token", null) != null) {
            EventBus.getDefault().register(this);
            fm.beginTransaction()
                    .add(R.id.main_layout, statusFlowFragment)
                    .add(R.id.main_layout, pictureFlowFragment)
                    .hide(pictureFlowFragment)
                    .commit();
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build();
            Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
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
                        .show(statusFlowFragment)
                        .hide(pictureFlowFragment)
                        .commit();
                return true;
            }
            case R.id.action_switch_picture: {
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out, R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out)
                        .show(pictureFlowFragment)
                        .hide(statusFlowFragment)
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
        Fragment userFragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("screen_name", event.getScreenName());
        userFragment.setArguments(bundle);
        fm.beginTransaction()
                .setCustomAnimations(R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out, R.anim.grow_fade_in_from_bottom, R.anim.fast_fade_out)
                .add(R.id.main_layout, userFragment)
                .addToBackStack(null)
                .commit();
        setAvatar(StatusData.usersShow(event.getScreenName()));
    }

    @Subscribe
    public void onEvent(CloseUserEvent event) {
        setAvatar(StatusData.usersShow(Hawk.get("uid", 0L)));
    }

    private void setAvatar(Observable<User> observable) {
        observable.subscribe(new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(User user) {
                Glide.with(getBaseContext()).load(user.profile_image_url).dontTransform().into(toolbar_avatar);
            }
        });
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
                .addToBackStack(null)
                .commit();
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

    @Override
    public void onResume() {
        super.onResume();
        FlowStatusStore.instance.start();
        setAvatar(StatusData.usersShow(Hawk.get("uid", 0L)));
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
