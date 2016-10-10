package cn.yhq.dialog.core;

/**
 * Created by Yanghuiqiang on 2016/10/10.
 */

public abstract class DialogProvider implements IDialogProvider {

  public abstract android.app.Dialog createInnerDialog(DialogBuilder dialogBuilder);

  @Override
  public final IDialog createDialog(DialogBuilder dialogBuilder) {
    return new Dialog(this.createInnerDialog(dialogBuilder));
  }
}
