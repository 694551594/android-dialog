package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;

import cn.yhq.dialog.builder.OtherDialogBuilder;
import cn.yhq.dialog.core.DialogProvider;


/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class ContentViewDialogProvider<T extends OtherDialogBuilder<T>> extends DialogProvider<T> {

    @Override
    public Dialog createInnerDialog(OtherDialogBuilder dialogBuilder) {
        dialogBuilder.setDefaultButtonText();

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogBuilder.getContext())
                .setTitle(dialogBuilder.getTitle()).setMessage(dialogBuilder.getMessage())
                .setNegativeButton(dialogBuilder.getNegativeButtonText(),
                        dialogBuilder.getOnNegativeButtonClickListener())
                .setPositiveButton(dialogBuilder.getPositiveButtonText(),
                        dialogBuilder.getOnPositiveButtonClickListener());

        FrameLayout frameLayout = new FrameLayout(dialogBuilder.getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        if (dialogBuilder.getContentViewResId() != 0) {
            dialogBuilder.setContentView(
                    View.inflate(dialogBuilder.getContext(), dialogBuilder.getContentViewResId(), null));
        }

        if (dialogBuilder.getContentView() != null) {
            FrameLayout.LayoutParams layoutParams = dialogBuilder.getLayoutParams();

            if (layoutParams == null) {
                layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
            }
            frameLayout.addView(dialogBuilder.getContentView(), layoutParams);
        }

        OtherDialogBuilder.OnContentViewListener onContentViewListener = dialogBuilder.getOnContentViewListener();
        if (onContentViewListener != null) {
            onContentViewListener.onContentViewCreated(frameLayout);
        }

        builder.setView(frameLayout);

        return builder.create();
    }
}
