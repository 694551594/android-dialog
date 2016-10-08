package cn.yhq.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;

import cn.yhq.dialog.utils.DisplayUtils;


/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class ContentViewDialogFragment extends BaseDialogFragment {

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this.mDialogBuilder.getContext())
        .setTitle(this.mDialogBuilder.getTitle()).setMessage(this.mDialogBuilder.getMessage())
        .setNegativeButton(this.mDialogBuilder.getNegativeButtonText(),
            this.mDialogBuilder.getOnNegativeButtonClickListener())
        .setPositiveButton(this.mDialogBuilder.getPositiveButtonText(),
            this.mDialogBuilder.getOnPositiveButtonClickListener());

    TypedValue typedValue = new TypedValue();
    mDialogBuilder.getContext().getTheme().resolveAttribute(R.attr.dialogPreferredPadding,
        typedValue, true);

    int DIALOG_SPACING_TOP = DisplayUtils.dp2Px(this.mDialogBuilder.getContext(), 20);
    int DIALOG_SPACING_BOTTOM = DisplayUtils.dp2Px(this.mDialogBuilder.getContext(), 10);
    int DIALOG_SPACING_LEFT =
        (int) mDialogBuilder.getContext().getResources().getDimension(typedValue.resourceId);
    int DIALOG_SPACING_RIGHT = DIALOG_SPACING_LEFT;

    if (this.mDialogBuilder.getContentView() != null) {
      builder.setView(this.mDialogBuilder.getContentView(), DIALOG_SPACING_LEFT, DIALOG_SPACING_TOP,
          DIALOG_SPACING_RIGHT, DIALOG_SPACING_BOTTOM).setRecycleOnMeasureEnabled(true);
    } else if (this.mDialogBuilder.getContentViewResId() != 0) {
      builder.setView(this.mDialogBuilder.getContentViewResId()).setRecycleOnMeasureEnabled(true);
    }

    return builder.create();
  }
}
