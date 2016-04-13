package pw.bits.weisper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * Created by rzh on 16/3/27.
 */
public class LongPictureView extends RecyclerView {
    private static final int maxTextureSize = getMaxTextureSize();

    public LongPictureView(Context context) {
        super(context);
        init();
    }

    public LongPictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LongPictureView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setPicture(Bitmap bitmap) {
        List<Bitmap> bitmapList = new ArrayList<>();
        int height = 0;
        while (height < bitmap.getHeight()) {
            bitmapList.add(Bitmap.createBitmap(bitmap, 0, height, bitmap.getWidth(), Math.min(bitmap.getHeight() - height, maxTextureSize)));
            height += maxTextureSize;
        }
        setAdapter(new PictureAdapter(bitmapList));
    }

    public class PictureAdapter extends Adapter<PictureViewHolder> {
        List<Bitmap> bitmapList;

        public PictureAdapter(List<Bitmap> bitmapList) {
            this.bitmapList = bitmapList;
        }

        @Override
        public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setAdjustViewBounds(true);
            imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            return new PictureViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(PictureViewHolder holder, int position) {
            holder.bindView(bitmapList.get(position));
        }

        @Override
        public int getItemCount() {
            return bitmapList.size();
        }
    }

    public static class PictureViewHolder extends ViewHolder {
        public PictureViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(Bitmap bitmap) {
            ((ImageView) itemView).setImageBitmap(bitmap);
            itemView.setOnClickListener(listener);
        }
    }

    private static OnClickListener listener;

    public void setOnItemClickListener(OnClickListener listener) {
        LongPictureView.listener = listener;
    }

    private static int getMaxTextureSize() {
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        int[] version = new int[2];
        egl.eglInitialize(display, version);
        int[] totalConfigurations = new int[1];
        egl.eglGetConfigs(display, null, 0, totalConfigurations);
        EGLConfig[] configurationsList = new EGLConfig[totalConfigurations[0]];
        egl.eglGetConfigs(display, configurationsList, totalConfigurations[0], totalConfigurations);
        int[] textureSize = new int[1];
        int maximumTextureSize = 0;
        for (int i = 0; i < totalConfigurations[0]; i++) {
            egl.eglGetConfigAttrib(display, configurationsList[i], EGL10.EGL_MAX_PBUFFER_WIDTH, textureSize);
            if (maximumTextureSize < textureSize[0]) {
                maximumTextureSize = textureSize[0];
            }
        }
        egl.eglTerminate(display);
        return maximumTextureSize;
    }
}
