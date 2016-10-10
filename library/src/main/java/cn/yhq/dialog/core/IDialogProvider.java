package cn.yhq.dialog.core;


public interface IDialogProvider {
  IDialog createDialog(DialogBuilder dialogBuilder);
}
