package cn.yhq.dialog.core;

/**
 * Created by Yanghuiqiang on 2016/10/9.
 */

class Dialog implements IDialog {
  private android.app.Dialog dialog;

  Dialog(android.app.Dialog dialog) {
    this.dialog = dialog;
  }

  @Override
  public void dismiss() {
    dialog.dismiss();
  }

  @Override
  public IDialog show() {
    if (!dialog.isShowing()) {
      dialog.show();
    }
    return this;
  }

  @Override
  public android.app.Dialog getInnerDialog() {
    return this.dialog;
  }
}
