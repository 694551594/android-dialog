package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;

import cn.yhq.dialog.R;
import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.IDialogProvider;
import cn.yhq.dialog.utils.DisplayUtils;


/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class ContentViewDialogProvider implements IDialogProvider {

  @Override
  public Dialog createDialog(DialogBuilder dialogBuilder) {
    AlertDialog.Builder builder = new AlertDialog.Builder(dialogBuilder.getContext())
        .setTitle(dialogBuilder.getTitle()).setMessage(dialogBuilder.getMessage())
        .setNegativeButton(dialogBuilder.getNegativeButtonText(),
            dialogBuilder.getOnNegativeButtonClickListener())
        .setPositiveButton(dialogBuilder.getPositiveButtonText(),
            dialogBuilder.getOnPositiveButtonClickListener())
        .setOnCancelListener(dialogBuilder.getOnCancelListener())
        .setOnDismissListener(dialogBuilder.getOnDismissListener());

    TypedValue typedValue = new TypedValue();
    dialogBuilder.getContext().getTheme().resolveAttribute(R.attr.dialogPreferredPadding,
        typedValue, true);

    int DIALOG_SPACING_TOP = DisplayUtils.dp2Px(dialogBuilder.getContext(), 20);
    int DIALOG_SPACING_BOTTOM = DisplayUtils.dp2Px(dialogBuilder.getContext(), 10);
    int DIALOG_SPACING_LEFT =
        (int) dialogBuilder.getContext().getResources().getDimension(typedValue.resourceId);
    int DIALOG_SPACING_RIGHT = DIALOG_SPACING_LEFT;

    if (dialogBuilder.getContentView() != null) {
      builder.setView(dialogBuilder.getContentView(), DIALOG_SPACING_LEFT, DIALOG_SPACING_TOP,
          DIALOG_SPACING_RIGHT, DIALOG_SPACING_BOTTOM).setRecycleOnMeasureEnabled(true);
    } else if (dialogBuilder.getContentViewResId() != 0) {
      builder.setView(dialogBuilder.getContentViewResId()).setRecycleOnMeasureEnabled(true);
    }

    return builder.create();
  }
}
