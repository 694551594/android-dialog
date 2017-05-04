package cn.yhq.dialog.core;

/**
 * Created by Yanghuiqiang on 2016/10/10.
 */

public abstract class DialogProvider<T extends DialogBuilder<T>>
        implements IDialogProvider<T> {

    public abstract android.app.Dialog createInnerDialog(T dialogBuilder);

    @Override
    public final IDialog createDialog(T dialogBuilder) {
        android.app.Dialog innerDialog = this.createInnerDialog(dialogBuilder);
        innerDialog.setOnCancelListener(dialogBuilder.getOnCancelListener());
        innerDialog.setOnDismissListener(dialogBuilder.getOnDismissListener());
        innerDialog.setOnShowListener(dialogBuilder.getOnShowListener());
        innerDialog.setCanceledOnTouchOutside(dialogBuilder.isCanceledOnTouchOutside());
        Dialog dialog = new Dialog(innerDialog);
        dialog.setDialogBuilder(dialogBuilder);
        return dialog;
    }
}
