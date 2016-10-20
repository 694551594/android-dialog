package cn.yhq.dialog.core;

/**
 * Created by Yanghuiqiang on 2016/10/10.
 */

public abstract class DialogProvider implements IDialogProvider {

  public abstract android.app.Dialog createInnerDialog(DialogBuilder dialogBuilder);

  @Override
  public final IDialog createDialog(DialogBuilder dialogBuilder) {
    android.app.Dialog innerDialog = this.createInnerDialog(dialogBuilder);
    innerDialog.setOnCancelListener(dialogBuilder.getOnCancelListener());
    innerDialog.setOnDismissListener(dialogBuilder.getOnDismissListener());
    innerDialog.setOnShowListener(dialogBuilder.getOnShowListener());
    Dialog dialog = new Dialog(innerDialog);
    dialog.setDialogBuilder(dialogBuilder);
    return dialog;
  }
}
