package cn.yhq.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;


/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class AlertDialogFragment extends BaseDialogFragment {

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return new AlertDialog.Builder(this.mDialogBuilder.getContext())
        .setTitle(this.mDialogBuilder.getTitle()).setMessage(this.mDialogBuilder.getMessage())
        .setNegativeButton(this.mDialogBuilder.getNegativeButtonText(),
            this.mDialogBuilder.getOnNegativeButtonClickListener())
        .setPositiveButton(this.mDialogBuilder.getPositiveButtonText(),
            this.mDialogBuilder.getOnPositiveButtonClickListener())
        .create();
  }
}
