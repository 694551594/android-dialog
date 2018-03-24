package cn.yhq.dialog.core;

/**
 * Created by Yanghuiqiang on 2016/10/9.
 */

class Dialog<T extends DialogBuilder<T>> implements IDialog {
    private android.app.Dialog dialog;
    private DialogBuilder<T> dialogBuilder;

    Dialog(DialogBuilder<T> dialogBuilder, android.app.Dialog dialog) {
        this.dialog = dialog;
        this.dialogBuilder = dialogBuilder;
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

    DialogBuilder<T> getDialogBuilder() {
        return dialogBuilder;
    }
}
