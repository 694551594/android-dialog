package cn.yhq.dialog;

public interface IDialogProvider {
  public IDialogProvider showAlertDialog();
  public IDialogProvider showMessageDialog();
  public IDialogProvider showLoadingDialog();
  public IDialogProvider showListDialog();
  public IDialogProvider showEditTextDialog();
  public IDialogProvider dismissDialog();
  public IDialogProvider showDialog();
}
