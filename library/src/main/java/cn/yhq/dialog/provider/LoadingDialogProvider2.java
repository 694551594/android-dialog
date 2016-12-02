package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.app.ProgressDialog;

import cn.yhq.dialog.R;
import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogProvider;

/**
 * Created by Yanghuiqiang on 2016/12/2.
 */

public class LoadingDialogProvider2 extends DialogProvider {

  @Override
  public Dialog createInnerDialog(DialogBuilder dialogBuilder) {
    final ProgressDialog dialog = new ProgressDialog(dialogBuilder.getContext());
    dialog.setIndeterminate(false);
    if (dialogBuilder.getMessage() != null) {
      dialog.setMessage(dialogBuilder.getMessage());
    } else {
      dialog.setMessage(dialogBuilder.getContext().getString(R.string.loading));
    }
    dialogBuilder.setOnProgressListener(new DialogBuilder.OnProgressListener() {
      @Override
      public void onProgress(int progress) {
        dialog.setProgress(progress);
      }
    });
    return dialog;
  }


}
