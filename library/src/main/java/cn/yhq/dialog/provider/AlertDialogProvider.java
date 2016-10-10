package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;

import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogProvider;


/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class AlertDialogProvider extends DialogProvider {

  @Override
  public Dialog createInnerDialog(DialogBuilder dialogBuilder) {
    return new AlertDialog.Builder(dialogBuilder.getContext()).setTitle(dialogBuilder.getTitle())
        .setMessage(dialogBuilder.getMessage())
        .setNegativeButton(dialogBuilder.getNegativeButtonText(),
            dialogBuilder.getOnNegativeButtonClickListener())
        .setPositiveButton(dialogBuilder.getPositiveButtonText(),
            dialogBuilder.getOnPositiveButtonClickListener())
        .setOnCancelListener(dialogBuilder.getOnCancelListener())
        .setOnDismissListener(dialogBuilder.getOnDismissListener()).create();
  }
}
