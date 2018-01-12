package cn.yhq.dialog.builder;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogType;

/**
 * Created by Yanghuiqiang on 2017/5/4.
 */

public class OtherDialogBuilder<T extends OtherDialogBuilder<T>> extends DialogBuilder<T> {
    private View contentView;
    private int contentViewResId;
    private FrameLayout.LayoutParams layoutParams;
    private OnContentViewListener onContentViewListener;

    public interface OnContentViewListener {
        void onContentViewCreated(View contentView);
    }

    public OtherDialogBuilder(Context context) {
        super(context, DialogType.DIALOG_OTHER);
    }

    public OtherDialogBuilder(Context context, int dialogType) {
        super(context, dialogType);
    }

    public int getContentViewResId() {
        return contentViewResId;
    }

    public T setContentViewResId(int contentViewResId) {
        this.contentViewResId = contentViewResId;
        return self();
    }

    public T setContentViewResId(int contentViewResId,
                                 FrameLayout.LayoutParams layoutParams) {
        this.contentViewResId = contentViewResId;
        this.layoutParams = layoutParams;
        return self();
    }

    public View getContentView() {
        return contentView;
    }

    public T setContentView(View contentView) {
        this.contentView = contentView;
        return self();
    }

    public T setContentView(View contentView, FrameLayout.LayoutParams layoutParams) {
        this.contentView = contentView;
        this.layoutParams = layoutParams;
        return self();
    }

    public FrameLayout.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public OnContentViewListener getOnContentViewListener() {
        return onContentViewListener;
    }

    public void setOnContentViewListener(OnContentViewListener onContentViewListener) {
        this.onContentViewListener = onContentViewListener;
    }
}
