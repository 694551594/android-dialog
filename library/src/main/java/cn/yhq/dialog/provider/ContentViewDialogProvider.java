package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import cn.yhq.dialog.R;
import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogProvider;
import cn.yhq.dialog.utils.DisplayUtils;


/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class ContentViewDialogProvider extends DialogProvider {

  @Override
  public Dialog createInnerDialog(DialogBuilder dialogBuilder) {
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
    frameLayout.setLayoutParams(dialogBuilder.getLayoutParams());
    if (dialogBuilder.getContentViewResId() != 0) {
      dialogBuilder.setContentView(
          View.inflate(dialogBuilder.getContext(), dialogBuilder.getContentViewResId(), null));
    }

    if (dialogBuilder.getContentView() != null) {
      frameLayout.addView(dialogBuilder.getContentView(), dialogBuilder.getLayoutParams());
    }

    builder.setView(frameLayout, DIALOG_SPACING_LEFT, DIALOG_SPACING_TOP, DIALOG_SPACING_RIGHT,
        DIALOG_SPACING_BOTTOM).setRecycleOnMeasureEnabled(true);

    return builder.create();
  }
}
