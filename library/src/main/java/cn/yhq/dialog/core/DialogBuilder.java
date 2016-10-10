package cn.yhq.dialog.core;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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

  private CharSequence[] choiceItems;
  private ListAdapter choiceAdapter;
  private int choiceItem;
  private int[] checkedItems;
  private View contentView;
  private int contentViewResId;
  private DialogInterface.OnClickListener onPositiveButtonClickListener;
  private DialogInterface.OnClickListener onNegativeButtonClickListener;
  private DialogInterface.OnClickListener onChoiceClickListener;
  private DialogInterface.OnCancelListener onCancelListener;
  private DialogInterface.OnDismissListener onDismissListener;
  private OnEditTextDialogListener onEditTextDialogListener;
  private DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener;
  private OnChoiceListener onChoiceListener;
  private OnMultiChoiceListener onMultiChoiceListener;
  private OnProgressListener onProgressListener;

  public final static int TYPE_CHOICE_NORMAL = 0;
  public final static int TYPE_CHOICE_SINGLE = 1;
  public final static int TYPE_CHOICE_MULTI = 2;

  private int dialogType = DIALOG_OTHER;

  public final static int DIALOG_OTHER = -1;
  public final static int DIALOG_MESSAGE = 0;
  public final static int DIALOG_ALERT = 1;
  public final static int DIALOG_LOADING = 2;
  public final static int DIALOG_LIST = 3;
  public final static int DIALOG_EDIT_TEXT = 4;
  public final static int DIALOG_PROGRESS = 5;

  public static DialogBuilder builder(Context context, int dialogType) {
    return new DialogBuilder(context, dialogType);
  }

  public static DialogBuilder progressDialog(Context context) {
    return builder(context, DIALOG_PROGRESS);
  }

  public static DialogBuilder messageDialog(Context context) {
    return builder(context, DIALOG_MESSAGE);
  }

  public static DialogBuilder alertDialog(Context context) {
    return builder(context, DIALOG_ALERT);
  }

  public static DialogBuilder listDialog(Context context) {
    return builder(context, DIALOG_LIST);
  }

  public static DialogBuilder loadingDialog(Context context) {
    return builder(context, DIALOG_LOADING);
  }

  public static DialogBuilder editTextDialog(Context context) {
    return builder(context, DIALOG_EDIT_TEXT);
  }

  public static DialogBuilder otherDialog(Context context) {
    return builder(context, DIALOG_OTHER);
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
    void onChoiceItem(Object item);
  }

  public interface OnMultiChoiceListener {
    void onMultiChoiceItems(Object[] items);
  }

  public interface OnEditTextDialogListener {
    void onEditTextCreated(EditText editText, CheckBox checkbox);

    /**
     * 点击确定按钮的时候回调方法，返回true不会关闭对话框，返回false会关闭对话框
     *
     * @param editText
     * @param text
     * @param checkbox
     * @param checked
     * @return
     */
    boolean onEditTextSelected(EditText editText, String text, CheckBox checkbox, boolean checked);
  }

  private DialogBuilder(Context context, int dialogType) {
    this.setContext(context);
    this.setNegativeButtonText(context.getString(R.string.cancel));
    this.setPositiveButtonText(context.getString(R.string.okay));
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

  public View getContentView() {
    return contentView;
  }

  public DialogBuilder setContentView(View contentView) {
    this.contentView = contentView;
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

  public int getChoiceItem() {
    return choiceItem;
  }

  public DialogBuilder setChoiceItem(int choiceItem) {
    this.choiceItem = choiceItem;
    return this;
  }

  public DialogBuilder setChoiceItems(List<CharSequence> choiceItems) {
    CharSequence[] items = new CharSequence[choiceItems.size()];
    for (int i = 0; i < choiceItems.size(); i++) {
      items[i] = choiceItems.get(i);
    }
    this.choiceItems = items;
    return this;
  }

  public DialogBuilder setChoiceItems(CharSequence... choiceItems) {
    this.choiceItems = choiceItems;
    return this;
  }

  public CharSequence[] getChoiceItems() {
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
    if (TextUtils.isEmpty(title)) {
      return context.getString(R.string.dialog_title);
    }
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
    if (this.dialogType == DIALOG_MESSAGE) {
      this.setNegativeButtonText(0);
    }
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
