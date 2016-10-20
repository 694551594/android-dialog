package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.Window;

import cn.yhq.dialog.R;
import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogProvider;

/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class LoadingDialogProvider extends DialogProvider {

  @Override
  public Dialog createInnerDialog(DialogBuilder dialogBuilder) {
    Dialog dialog =
        new AlertDialog.Builder(dialogBuilder.getContext()).setView(R.layout.comm_dialog_loading)
            .create();
    Window window = dialog.getWindow();
    window.setBackgroundDrawableResource(android.R.color.transparent);
    return dialog;
  }

}
