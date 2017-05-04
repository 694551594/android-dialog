package cn.yhq.dialog.builder;

import android.content.Context;

import cn.yhq.dialog.core.DialogBuilder;

/**
 * Created by Yanghuiqiang on 2017/5/4.
 */

public class ProgressDialogBuilder extends DialogBuilder<ProgressDialogBuilder> {
    private OnProgressListener onProgressListener;

    public interface OnProgressListener {
        void onProgress(int progress);
    }

    public static class ProgressHandler {
        OnProgressListener listener;

        public void setProgress(int progress) {
            listener.onProgress(progress);
        }
    }

    public ProgressDialogBuilder(Context context, int dialogType) {
        super(context, dialogType);
    }

    public static ProgressDialogBuilder getProgressDialogBuilder1(Context context) {
        return new ProgressDialogBuilder(context, DialogType.DIALOG_PROGRESS);
    }

    public static ProgressDialogBuilder getProgressDialogBuilder2(Context context) {
        return new ProgressDialogBuilder(context, DialogType.DIALOG_CIRCLE_PROGRESS);
    }

    public OnProgressListener getOnProgressListener() {
        return onProgressListener;
    }

    public ProgressDialogBuilder setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
        return this;
    }

    public ProgressDialogBuilder progressHandler(ProgressHandler progressHandler) {
        progressHandler.listener = new OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                onProgressListener.onProgress(progress);
            }
        };
        return this;
    }

}
