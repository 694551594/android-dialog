package cn.yhq.dialog;

import android.content.DialogInterface;

public interface OnDialogDismissListener {
  public void onDismiss(int id, DialogBuilder builder, DialogInterface dialog);
}
