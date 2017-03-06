package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;

import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogProvider;

/**
 * Created by Yanghuiqiang on 2017/3/6.
 */

public class MessageDialogProvider extends DialogProvider {

  @Override
  public Dialog createInnerDialog(DialogBuilder dialogBuilder) {
    dialogBuilder.defaultButtonText();
    return new AlertDialog.Builder(dialogBuilder.getContext()).setTitle(dialogBuilder.getTitle())
        .setMessage(dialogBuilder.getMessage())
        .setPositiveButton(dialogBuilder.getPositiveButtonText(),
            dialogBuilder.getOnPositiveButtonClickListener())
        .create();
  }

}
