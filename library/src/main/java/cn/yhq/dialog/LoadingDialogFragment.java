package cn.yhq.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Window;

/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class LoadingDialogFragment extends BaseDialogFragment {

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = new AlertDialog.Builder(this.mDialogBuilder.getContext())
        .setView(R.layout.comm_dialog_loading).create();
    Window window = dialog.getWindow();
    window.setBackgroundDrawableResource(android.R.color.transparent);
    return dialog;
  }

}
