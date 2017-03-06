package cn.yhq.dialog.core;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;

import java.util.List;

import cn.yhq.dialog.R;


/**
 * 对话框builder
 * 
 * @author Yanghuiqiang
 * 
 *         2015-11-23
 */
public final class DialogBuilder {

  private Context context;
  private String title;
  private String message;
  private String positiveButtonText;
  private String negativeButtonText;
  private boolean cancelable;
  private int choiceType = TYPE_CHOICE_NORMAL;

  private Object[] choiceItems;
  private ListAdapter choiceAdapter;
  private int checkedItem;
  private int[] checkedItems;
  private View contentView;
  private int contentViewResId;
  private FrameLayout.LayoutParams layoutParams;
  private DialogInterface.OnClickListener onPositiveButtonClickListener;
  private DialogInterface.OnClickListener onNegativeButtonClickListener;
  private DialogInterface.OnClickListener onChoiceClickListener;
  private DialogInterface.OnCancelListener onCancelListener;
  private DialogInterface.OnDismissListener onDismissListener;
  private DialogInterface.OnShowListener onShowListener;
  private OnEditTextDialogListener onEditTextDialogListener;
  private DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener;
  private OnChoiceListener onChoiceListener;
  private OnMultiChoiceListener onMultiChoiceListener;
  private OnProgressListener onProgressListener;
  private OnStateChangeListener onStateChangeListener;

  public final static int TYPE_CHOICE_NORMAL = 0;
  public final static int TYPE_CHOICE_SINGLE = 1;
  public final static int TYPE_CHOICE_MULTI = 2;

  private int dialogType = DIALOG_OTHER;

  public final static int DIALOG_OTHER = -1;
  public final static int DIALOG_MESSAGE = 0;
  public final static int DIALOG_ALERT = 1;
  public final static int DIALOG_LOADING0 = 2;
  public final static int DIALOG_LIST = 3;
  public final static int DIALOG_EDIT_TEXT = 4;
  public final static int DIALOG_CIRCLE_PROGRESS = 5;
  public final static int DIALOG_BOTTOM_SHEET = 6;
  public final static int DIALOG_PROGRESS = 7;
  public final static int DIALOG_LOADING1 = 8;
  public final static int DIALOG_LOADING2 = 9;

  public static DialogBuilder builder(Context context, int dialogType) {
    return new DialogBuilder(context, dialogType);
  }

  public static DialogBuilder progressDialog(Context context) {
    return builder(context, DIALOG_PROGRESS);
  }

  public static DialogBuilder progressCircleDialog(Context context) {
    return builder(context, DIALOG_CIRCLE_PROGRESS);
  }

  public static DialogBuilder messageDialog(Context context) {
    return builder(context, DIALOG_MESSAGE);
  }

  public static DialogBuilder bottomSheetDialog(Context context) {
    return builder(context, DIALOG_BOTTOM_SHEET);
  }

  public static DialogBuilder alertDialog(Context context) {
    return builder(context, DIALOG_ALERT);
  }

  public static DialogBuilder listDialog(Context context) {
    return builder(context, DIALOG_LIST);
  }

  @Deprecated
  public static DialogBuilder loadingDialog(Context context) {
    return loadingDialog0(context);
  }

  public static DialogBuilder loadingDialog0(Context context) {
    return builder(context, DIALOG_LOADING0);
  }

  public static DialogBuilder loadingDialog1(Context context) {
    return builder(context, DIALOG_LOADING1);
  }

  public static DialogBuilder loadingDialog2(Context context) {
    return builder(context, DIALOG_LOADING2);
  }

  public static DialogBuilder editTextDialog(Context context) {
    return builder(context, DIALOG_EDIT_TEXT);
  }

  public static DialogBuilder otherDialog(Context context) {
    return builder(context, DIALOG_OTHER);
  }

  public interface OnStateChangeListener {
    void onSaveInstanceState(IDialog dialog, Bundle bundle);

    void onRestoreInstanceState(IDialog dialog, Bundle bundle);
  }

  public interface OnProgressListener {
    void onProgress(int progress);
  }

  public static class ProgressHandler {
    OnProgressListener listener;

    public void setProgress(int progress) {
      listener.onProgress(progress);
    }
  }

  public interface OnChoiceListener {
    void onChoiceItem(int index, Object item);
  }

  public interface OnMultiChoiceListener {
    void onMultiChoiceItems(List<Integer> indexs, Object[] items);
  }

  public interface OnEditTextDialogListener {
    void onEditTextCreated(EditText editText);

    /**
     * 点击确定按钮的时候回调方法，返回true不会关闭对话框，返回false会关闭对话框
     *
     * @param editText
     * @param text
     * @return
     */
    boolean onEditTextSelected(EditText editText, String text);
  }

  private DialogBuilder(Context context, int dialogType) {
    this.setContext(context);
    this.setCancelable(true);
    this.setDialogType(dialogType);
  }

  public OnEditTextDialogListener getOnEditTextDialogListener() {
    return onEditTextDialogListener;
  }

  public DialogBuilder setOnEditTextDialogListener(
      OnEditTextDialogListener onEditTextDialogListener) {
    this.onEditTextDialogListener = onEditTextDialogListener;
    return this;
  }

  public int getContentViewResId() {
    return contentViewResId;
  }

  public DialogBuilder setContentViewResId(int contentViewResId) {
    this.contentViewResId = contentViewResId;
    return this;
  }

  public DialogBuilder setContentViewResId(int contentViewResId,
      FrameLayout.LayoutParams layoutParams) {
    this.contentViewResId = contentViewResId;
    this.layoutParams = layoutParams;
    return this;
  }

  public View getContentView() {
    return contentView;
  }

  public DialogBuilder setContentView(View contentView) {
    this.contentView = contentView;
    return this;
  }

  public DialogBuilder setContentView(View contentView, FrameLayout.LayoutParams layoutParams) {
    this.contentView = contentView;
    this.layoutParams = layoutParams;
    return this;
  }

  public int getChoiceType() {
    return choiceType;
  }

  public DialogBuilder setChoiceType(int choiceType) {
    this.choiceType = choiceType;
    return this;
  }

  public ListAdapter getChoiceAdapter() {
    return choiceAdapter;
  }

  public DialogBuilder setChoiceAdapter(ListAdapter choiceAdapter) {
    this.choiceAdapter = choiceAdapter;
    if (this.getChoiceType() == TYPE_CHOICE_MULTI) {
      throw new IllegalArgumentException("多选对话框不可以设置adapter");
    }
    return this;
  }

  public OnStateChangeListener getOnStateChangeListener() {
    return onStateChangeListener;
  }

  public DialogBuilder setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
    this.onStateChangeListener = onStateChangeListener;
    return this;
  }

  /**
   * 使用getCheckedItem替代
   *
   * @return
   */
  @Deprecated
  public int getChoiceItem() {
    return getCheckedItem();
  }

  /**
   * 使用setCheckedItem替代
   *
   * @param checkedItem
   * @return
   */
  @Deprecated
  public DialogBuilder setChoiceItem(int checkedItem) {
    this.setCheckedItem(checkedItem);
    return this;
  }

  public int getCheckedItem() {
    return checkedItem;
  }

  public DialogBuilder setCheckedItem(int checkedItem) {
    this.checkedItem = checkedItem;
    return this;
  }

  public DialogBuilder setChoiceItems(List<Object> choiceItems) {
    Object[] items = new Object[choiceItems.size()];
    for (int i = 0; i < choiceItems.size(); i++) {
      items[i] = choiceItems.get(i);
    }
    this.choiceItems = items;
    return this;
  }

  public DialogBuilder setChoiceItems(Object... choiceItems) {
    this.choiceItems = choiceItems;
    return this;
  }

  public Object[] getChoiceItems() {
    return choiceItems;
  }

  /**
   * @return the onPositiveButtonClickListener
   */
  public DialogInterface.OnClickListener getOnPositiveButtonClickListener() {
    return onPositiveButtonClickListener;
  }

  /**
   * @param onPositiveButtonClickListener the onPositiveButtonClickListener to set
   */
  public DialogBuilder setOnPositiveButtonClickListener(
      DialogInterface.OnClickListener onPositiveButtonClickListener) {
    this.onPositiveButtonClickListener = onPositiveButtonClickListener;
    this.setPositiveButtonText(context.getString(R.string.okay));
    return this;
  }

  /**
   * @return the onNegativeButtonClickListener
   */
  public DialogInterface.OnClickListener getOnNegativeButtonClickListener() {
    return onNegativeButtonClickListener;
  }

  /**
   * @param onNegativeButtonClickListener the onNegativeButtonClickListener to set
   */
  public DialogBuilder setOnNegativeButtonClickListener(
      DialogInterface.OnClickListener onNegativeButtonClickListener) {
    this.onNegativeButtonClickListener = onNegativeButtonClickListener;
    this.setNegativeButtonText(context.getString(R.string.cancel));
    return this;
  }

  /**
   * @return the cancelable
   */
  public boolean isCancelable() {
    return cancelable;
  }

  /**
   * @param cancelable the cancelable to set
   */
  public DialogBuilder setCancelable(boolean cancelable) {
    this.cancelable = cancelable;
    return this;
  }

  /**
   * @return the context
   */
  public Context getContext() {
    return context;
  }

  /**
   * @param context the context to set
   */
  public DialogBuilder setContext(Context context) {
    this.context = context;
    return this;
  }

  public FrameLayout.LayoutParams getLayoutParams() {
    return layoutParams;
  }

  public DialogBuilder setTitle(int title) {
    this.title = context.getString(title);
    return this;
  }

  public DialogBuilder setMessage(int message) {
    this.message = context.getString(message);
    return this;
  }

  public DialogBuilder setPositiveButtonText(int positiveButtonText) {
    this.positiveButtonText =
        positiveButtonText == 0 ? null : context.getString(positiveButtonText);
    return this;
  }

  public DialogBuilder setNegativeButtonText(int negativeButtonText) {
    this.negativeButtonText =
        negativeButtonText == 0 ? null : context.getString(negativeButtonText);
    return this;
  }

  public DialogBuilder setTitle(String title) {
    this.title = title;
    return this;
  }

  public DialogBuilder setMessage(String message) {
    this.message = message;
    return this;
  }

  public DialogBuilder setPositiveButtonText(String positiveButtonText) {
    this.positiveButtonText = positiveButtonText;
    return this;
  }

  public DialogBuilder setNegativeButtonText(String negativeButtonText) {
    this.negativeButtonText = negativeButtonText;
    return this;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return the message
   */
  public CharSequence getMessage() {
    if (TextUtils.isEmpty(message)) {
      return message;
    }
    return Html.fromHtml(message);
  }

  /**
   * @return the positiveButtonText
   */
  public String getPositiveButtonText() {
    return positiveButtonText;
  }

  /**
   * @return the negativeButtonText
   */
  public String getNegativeButtonText() {
    return negativeButtonText;
  }

  public DialogBuilder defaultButtonText() {
    if (TextUtils.isEmpty(this.getNegativeButtonText())) {
      this.setNegativeButtonText(R.string.cancel);
    }
    if (TextUtils.isEmpty(this.getPositiveButtonText())) {
      this.setPositiveButtonText(R.string.okay);
    }
    return this;
  }

  public DialogInterface.OnCancelListener getOnCancelListener() {
    return onCancelListener;
  }

  public DialogBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
    this.onCancelListener = onCancelListener;
    return this;
  }

  public DialogInterface.OnDismissListener getOnDismissListener() {
    return onDismissListener;
  }

  public DialogBuilder setOnShowListener(DialogInterface.OnShowListener onShowListener) {
    this.onShowListener = onShowListener;
    return this;
  }

  public DialogInterface.OnShowListener getOnShowListener() {
    return onShowListener;
  }

  public DialogBuilder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
    this.onDismissListener = onDismissListener;
    return this;
  }

  public DialogInterface.OnMultiChoiceClickListener getOnMultiChoiceClickListener() {
    return onMultiChoiceClickListener;
  }

  public DialogBuilder setOnMultiChoiceClickListener(
      DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
    this.onMultiChoiceClickListener = onMultiChoiceClickListener;
    return this;
  }

  public DialogInterface.OnClickListener getOnChoiceClickListener() {
    return onChoiceClickListener;
  }

  public DialogBuilder setOnChoiceClickListener(
      DialogInterface.OnClickListener onChoiceClickListener) {
    this.onChoiceClickListener = onChoiceClickListener;
    return this;
  }

  public int[] getCheckedItems() {
    return checkedItems;
  }

  public DialogBuilder setCheckedItems(int[] checkedItems) {
    this.checkedItems = checkedItems;
    return this;
  }

  public OnChoiceListener getOnChoiceListener() {
    return onChoiceListener;
  }

  public DialogBuilder setOnChoiceListener(OnChoiceListener onChoiceListener) {
    this.onChoiceListener = onChoiceListener;
    return this;
  }

  public OnMultiChoiceListener getOnMultiChoiceListener() {
    return onMultiChoiceListener;
  }

  public DialogBuilder setOnMultiChoiceListener(OnMultiChoiceListener onMultiChoiceListener) {
    this.onMultiChoiceListener = onMultiChoiceListener;
    return this;
  }

  public OnProgressListener getOnProgressListener() {
    return onProgressListener;
  }

  public void setOnProgressListener(OnProgressListener onProgressListener) {
    this.onProgressListener = onProgressListener;
  }

  public DialogBuilder progressHandler(ProgressHandler progressHandler) {
    progressHandler.listener = new OnProgressListener() {
      @Override
      public void onProgress(int progress) {
        onProgressListener.onProgress(progress);
      }
    };
    return this;
  }

  public DialogBuilder setDialogType(int dialogType) {
    this.dialogType = dialogType;
    return this;
  }

  public int getDialogType() {
    return this.dialogType;
  }

  public IDialog create() {
    return DialogFactory.create(this);
  }

  public IDialog show() {
    return create().show();
  }

}
