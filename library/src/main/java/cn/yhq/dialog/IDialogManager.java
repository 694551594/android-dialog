package cn.yhq.dialog;

public interface IDialogManager {
  void showDialog(DialogBuilder builder);

  void dismissDialogFragment(int id);

  void showDialogFragment(int id);

  void showDialogFragment(int id, DialogBuilder.DialogBundle bundle);
}
