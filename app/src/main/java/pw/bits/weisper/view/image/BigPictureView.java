package pw.bits.weisper.view.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

import pw.bits.weisper.R;

/**
 * Created by rzh on 16/3/23.
 */
public class BigPictureView extends FrameLayout {
    public BigPictureView(Context context) {
        super(context);
    }

    public BigPictureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BigPictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPicture(String url) {
        final ImageView image = (ImageView) findViewById(R.id.item_picture_image);
        Glide.with(getContext())
                .load(url)
                .transform(new BitmapTransformation(getContext()) {
                    @Override
                    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                        if (toTransform.getHeight() > maximumTextureSize) {
                            Bitmap scaled = Bitmap.createScaledBitmap(toTransform, (int) ((maximumTextureSize / (float) toTransform.getHeight()) * toTransform.getWidth()), maximumTextureSize, true);
                            toTransform.recycle();
                            return scaled;
                        }
                        return toTransform;
                    }

                    @Override
                    public String getId() {
                        return "scale";
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.picture_error)
                .into(image);
    }

    private static int maximumTextureSize = getMaximumTextureSize();

    private static int getMaximumTextureSize() {
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

        // Initialise
        int[] version = new int[2];
        egl.eglInitialize(display, version);

        // Query total number of configurations
        int[] totalConfigurations = new int[1];
        egl.eglGetConfigs(display, null, 0, totalConfigurations);

        // Query actual list configurations
        EGLConfig[] configurationsList = new EGLConfig[totalConfigurations[0]];
        egl.eglGetConfigs(display, configurationsList, totalConfigurations[0], totalConfigurations);

        int[] textureSize = new int[1];
        int maximumTextureSize = 0;

        // Iterate through all the configurations to located the maximum texture size
        for (int i = 0; i < totalConfigurations[0]; i++) {
            // Only need to check for width since opengl textures are always squared
            egl.eglGetConfigAttrib(display, configurationsList[i], EGL10.EGL_MAX_PBUFFER_WIDTH, textureSize);

            // Keep track of the maximum texture size
            if (maximumTextureSize < textureSize[0]) {
                maximumTextureSize = textureSize[0];
            }
        }

        // Release
        egl.eglTerminate(display);

        return maximumTextureSize;
    }
}
