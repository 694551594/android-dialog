package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import cn.yhq.dialog.R;
import cn.yhq.dialog.builder.OtherDialogBuilder;
import cn.yhq.dialog.core.DialogProvider;
import cn.yhq.utils.DisplayUtils;


/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class ContentViewDialogProvider extends DialogProvider<OtherDialogBuilder> {

    @Override
    public Dialog createInnerDialog(OtherDialogBuilder dialogBuilder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(dialogBuilder.getContext())
                .setTitle(dialogBuilder.getTitle()).setMessage(dialogBuilder.getMessage())
                .setNegativeButton(dialogBuilder.getNegativeButtonText(),
                        dialogBuilder.getOnNegativeButtonClickListener())
                .setPositiveButton(dialogBuilder.getPositiveButtonText(),
                        dialogBuilder.getOnPositiveButtonClickListener());

        TypedValue typedValue = new TypedValue();
        dialogBuilder.getContext().getTheme().resolveAttribute(R.attr.dialogPreferredPadding,
                typedValue, true);

        int DIALOG_SPACING_TOP = DisplayUtils.dp2Px(dialogBuilder.getContext(), 20);
        int DIALOG_SPACING_BOTTOM = DisplayUtils.dp2Px(dialogBuilder.getContext(), 10);
        int DIALOG_SPACING_LEFT =
                (int) dialogBuilder.getContext().getResources().getDimension(typedValue.resourceId);
        int DIALOG_SPACING_RIGHT = DIALOG_SPACING_LEFT;

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

        builder.setView(frameLayout, DIALOG_SPACING_LEFT, DIALOG_SPACING_TOP, DIALOG_SPACING_RIGHT,
                DIALOG_SPACING_BOTTOM).setRecycleOnMeasureEnabled(true);

        return builder.create();
    }
}
