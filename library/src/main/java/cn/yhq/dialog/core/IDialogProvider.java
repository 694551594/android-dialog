package cn.yhq.dialog.core;


public interface IDialogProvider<T extends DialogBuilder<T>> {
    IDialog createDialog(T dialogBuilder);
}
