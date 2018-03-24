package cn.yhq.dialog.builder;

import android.content.Context;

import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogType;

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

    protected ProgressDialogBuilder(Context context, int dialogType) {
        super(context, dialogType);
    }

    public static ProgressDialogBuilder getProgressDialogBuilder1(Context context) {
        return new ProgressDialogBuilder(context, DialogType.DIALOG_PROGRESS);
    }

    public OnProgressListener getOnProgressListener() {
        return onProgressListener;
    }

    public ProgressDialogBuilder setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
        return self();
    }

    public ProgressDialogBuilder progressHandler(ProgressHandler progressHandler) {
        progressHandler.listener = new OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                onProgressListener.onProgress(progress);
            }
        };
        return self();
    }

}
