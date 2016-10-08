package cn.yhq.dialog;

/**
 * dialog显示privider，负责各种dialog的显示。
 * 
 * @author Yanghuiqiang 2015-10-19
 * 
 */
public class DialogProvider implements IDialogProvider {
  // dialog的参数
  private DialogBuilder mDialogBuilder;
  // dialog生成工厂
  private IDialogFactory mDialogFactory;

  public DialogProvider(DialogBuilder dialogBuilder, IDialogFactory dialogFactory) {
    this.mDialogBuilder = dialogBuilder;
    this.mDialogFactory = dialogFactory;
    if (this.mDialogFactory == null) {
      this.mDialogFactory = new DialogFactory();
    }
  }

  public DialogProvider(DialogBuilder dialogBuilder) {
    this(dialogBuilder, null);
  }

  @Override
  public IDialogProvider showAlertDialog() {
    mDialogBuilder.setDialogType(DialogBuilder.DIALOG_ALERT);
    this.showDialog();
    return this;
  }

  @Override
  public IDialogProvider showMessageDialog() {
    mDialogBuilder.setDialogType(DialogBuilder.DIALOG_MESSAGE);
    this.showDialog();
    return this;
  }

  @Override
  public IDialogProvider showLoadingDialog() {
    mDialogBuilder.setDialogType(DialogBuilder.DIALOG_LOADING);
    this.showDialog();
    return this;
  }

  @Override
  public IDialogProvider showListDialog() {
    mDialogBuilder.setDialogType(DialogBuilder.DIALOG_LIST);
    this.showDialog();
    return this;
  }

  @Override
  public IDialogProvider dismissDialog() {
    this.mDialogFactory.dismissDialogFragment(mDialogBuilder.getId());
    return this;
  }

  @Override
  public IDialogProvider showDialog() {
    this.mDialogFactory.showDialogFragment(mDialogBuilder);
    return this;
  }

  @Override
  public IDialogProvider showEditTextDialog() {
    mDialogBuilder.setDialogType(DialogBuilder.DIALOG_EDIT_TEXT);
    this.showDialog();
    return this;
  }

}
