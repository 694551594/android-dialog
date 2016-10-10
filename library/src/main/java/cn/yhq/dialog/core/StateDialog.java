package cn.yhq.dialog.core;

import android.app.Dialog;
import android.support.v4.app.FragmentManager;

/**
 * Created by Administrator on 2016/10/10.
 */

class StateDialog implements IDialog {
  DialogFragment dialogFragment;
  FragmentManager fragmentManager;

  StateDialog(FragmentManager fragmentManager, DialogFragment dialogFragment) {
    this.fragmentManager = fragmentManager;
    this.dialogFragment = dialogFragment;
  }

  @Override
  public void dismiss() {
    dialogFragment.dismissAllowingStateLoss();
  }

  @Override
  public IDialog show() {
    dialogFragment.show(fragmentManager, dialogFragment.getClass().getName());
    return this;
  }

  @Override
  public Dialog getInnerDialog() {
    return dialogFragment.getDialog();
  }
}
