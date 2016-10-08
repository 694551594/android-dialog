package cn.yhq.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 对话框builder
 * 
 * @author Yanghuiqiang
 * 
 *         2015-11-23
 */
public final class DialogBuilder {

  private Context context;
  private DialogBundle dialogBundle;
  private FragmentManager fragmentManager;
  private String title;
  private String message;
  private String positiveButtonText;
  private String negativeButtonText;
  private Class<? extends BaseDialogFragment> dialogClass;
  private int id;
  private boolean cancelable;
  private boolean removed;
  private int choiceType = TYPE_CHOICE_NORMAL;

  private CharSequence[] choiceItems;
  private ListAdapter choiceAdapter;
  private int choiceItem;
  private View contentView;
  private int contentViewResId;
  private DialogInterface.OnClickListener onPositiveButtonClickListener;
  private DialogInterface.OnClickListener onNegativeButtonClickListener;
  private DialogInterface.OnClickListener onChoiceClickListener;
  private DialogInterface.OnCancelListener onCancelListener;
  private DialogInterface.OnDismissListener onDismissListener;
  private OnEditTextDialogListener onEditTextDialogListener;

  public final static String KEY_DIALOG_REMOVED = "removed";
  public final static String KEY_DIALOG_CANCELABLE = "cancelable";
  public final static String KEY_DIALOG_ID = "id";
  public final static String KEY_DIALOG_TITLE = "title";
  public final static String KEY_DIALOG_MESSAGE = "message";
  public final static String KEY_DIALOG_PBUTTON_TEXT = "positiveButtonText";
  public final static String KEY_DIALOG_NBUTTON_TEXT = "negativeButtonText";
  public final static String KEY_DIALOG_BUILDER = "dialogBuilder";
  public final static String KEY_DIALOG_CHOICE_TYPE = "itemChoiceType";
  public final static String KEY_DIALOG_TYPE = "dialogType";

  public final static int TYPE_CHOICE_NORMAL = 0;
  public final static int TYPE_CHOICE_SINGLE = 1;
  public final static int TYPE_CHOICE_MULTI = 2;

  private IDialogFactory mDialogFactory;

  public final static int DIALOG_OTHER = -1;
  public final static int DIALOG_MESSAGE = 0;
  public final static int DIALOG_ALERT = 1;
  public final static int DIALOG_LOADING = 2;
  public final static int DIALOG_LIST = 3;
  public final static int DIALOG_EDIT_TEXT = 4;

  private int dialogType = DIALOG_OTHER;

  private final static Map<Integer, Class<? extends BaseDialogFragment>> DEFAULT_DIALOG =
      new HashMap<>();

  static {
    register(DIALOG_OTHER, ContentViewDialogFragment.class);
    register(DIALOG_MESSAGE, AlertDialogFragment.class);
    register(DIALOG_ALERT, AlertDialogFragment.class);
    register(DIALOG_LOADING, LoadingDialogFragment.class);
    register(DIALOG_LIST, ListDialogFragment.class);
    register(DIALOG_EDIT_TEXT, EditTextDialogFragment.class);
  }

  public static DialogBuilder builder(Context context) {
    return new DialogBuilder(context);
  }

  public static void register(int dialogType, Class<? extends BaseDialogFragment> dialogClass) {
    DEFAULT_DIALOG.put(dialogType, dialogClass);
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
    boolean onEditTextSelected(EditText editText, String text, CheckBox checkbox,
                                      boolean checked);
  }

  public static class DialogBundle {
    private Bundle bundle;

    public static DialogBundle create() {
      DialogBundle bundle = new DialogBundle();
      return bundle;
    }

    DialogBundle() {
      bundle = new Bundle();
    }

    public int getIntArg(String key) {
      return bundle.getInt(key);
    }

    public String getStringArg(String key) {
      return bundle.getString(key);
    }

    public boolean containsKey(String key) {
      return bundle.containsKey(key);
    }

    public Serializable getSerializableArg(String key) {
      return this.bundle.getSerializable(key);
    }

    public DialogBundle putArg(String key, Serializable serializableArgs) {
      this.bundle.putSerializable(key, serializableArgs);
      return this;
    }

    public DialogBundle putArg(String key, String stringArgs) {
      this.bundle.putString(key, stringArgs);
      return this;
    }

    public DialogBundle putArg(String key, int intArgs) {
      this.bundle.putInt(key, intArgs);
      return this;
    }

    /**
     * @param args the args to set
     */
    public DialogBundle putArg(Bundle args) {
      this.bundle.putAll(args);
      return this;
    }
  }

  private DialogBuilder(Context context) {
    this.setContext(context);
    this.dialogBundle = DialogBundle.create();
    this.setNegativeButtonText(context.getString(R.string.cancel));
    this.setPositiveButtonText(context.getString(R.string.okay));
    this.setCancelable(true);
    this.setRemoved(true);
  }

  public DialogBuilder from(DialogBuilder builder) {
    this.dialogBundle = builder.dialogBundle;
    this.cancelable = builder.cancelable;
    this.context = builder.context;
    this.dialogClass = builder.dialogClass;
    this.fragmentManager = builder.fragmentManager;
    this.id = builder.id;
    this.message = builder.message;
    this.onNegativeButtonClickListener = builder.onNegativeButtonClickListener;
    this.negativeButtonText = builder.negativeButtonText;
    this.onPositiveButtonClickListener = builder.onPositiveButtonClickListener;
    this.positiveButtonText = builder.positiveButtonText;
    this.onCancelListener = builder.onCancelListener;
    this.onDismissListener = builder.onDismissListener;
    this.removed = builder.removed;
    this.title = builder.title;
    this.choiceItems = builder.choiceItems;
    this.choiceAdapter = builder.choiceAdapter;
    this.onChoiceClickListener = builder.onChoiceClickListener;
    this.choiceType = builder.choiceType;
    this.contentView = builder.contentView;
    this.contentViewResId = builder.contentViewResId;
    this.onEditTextDialogListener = builder.onEditTextDialogListener;
    this.dialogType = builder.dialogType;
    return this;
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
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public DialogBuilder setId(int id) {
    this.id = id;
    return this;
  }

  /**
   * @return the removed
   */
  public boolean isRemoved() {
    return removed;
  }

  /**
   * @param removed the removed to set
   */
  public DialogBuilder setRemoved(boolean removed) {
    this.removed = removed;
    return this;
  }

  /**
   * @return the dialogClass
   */
  public Class<? extends BaseDialogFragment> getDialogClass() {
    if (dialogClass == null) {
      return DEFAULT_DIALOG.get(DIALOG_OTHER);
    }
    return dialogClass;
  }

  /**
   * @param dialogClass the dialogClass to set
   */
  public DialogBuilder setDialogClass(Class<? extends BaseDialogFragment> dialogClass) {
    this.dialogClass = dialogClass;
    return this;
  }

  /**
   * @return the fragmentManager
   */
  public FragmentManager getFragmentManager() {
    return fragmentManager;
  }

  /**
   * @param fragmentManager the fragmentManager to set
   */
  public DialogBuilder setFragmentManager(FragmentManager fragmentManager) {
    this.fragmentManager = fragmentManager;
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

  /**
   * @return the args
   */
  public Bundle getArgs() {
    return this.dialogBundle.bundle;
  }

  public int getIntArg(String key) {
    return dialogBundle.getIntArg(key);
  }

  public String getStringArg(String key) {
    return dialogBundle.getStringArg(key);
  }

  public <T extends Serializable> T getSerializableArg(String key) {
    return (T) this.dialogBundle.getSerializableArg(key);
  }

  public DialogBuilder putArg(String key, Serializable serializableArgs) {
    this.dialogBundle.putArg(key, serializableArgs);
    return this;
  }

  public DialogBuilder putArg(String key, String stringArgs) {
    this.dialogBundle.putArg(key, stringArgs);
    return this;
  }

  public DialogBuilder putArg(String key, int intArgs) {
    this.dialogBundle.putArg(key, intArgs);
    return this;
  }

  /**
   * @param args the args to set
   */
  public DialogBuilder setArgs(Bundle args) {
    this.dialogBundle.putArg(args);
    return this;
  }

  public DialogBuilder setDialogBundle(DialogBundle dialogBundle) {
    this.dialogBundle = dialogBundle;
    return this;
  }

  public DialogBundle getDialogBundle() {
    return this.dialogBundle;
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

  public DialogInterface.OnClickListener getOnChoiceClickListener() {
    return onChoiceClickListener;
  }

  public DialogBuilder setOnChoiceClickListener(DialogInterface.OnClickListener onChoiceClickListener) {
    this.onChoiceClickListener = onChoiceClickListener;
    return this;
  }

  public DialogBuilder setDialogFactory(IDialogFactory dialogFactory) {
    this.mDialogFactory = dialogFactory;
    return this;
  }

  public DialogBuilder setDialogType(int dialogType) {
    this.dialogType = dialogType;
    if (this.dialogType == DIALOG_MESSAGE) {
      this.setNegativeButtonText(0);
    }
    this.setDialogClass(DEFAULT_DIALOG.get(this.dialogType));
    return this;
  }

  public int getDialogType() {
    return this.dialogType;
  }

  public Bundle toBundle() {
    Bundle bundle = new Bundle();
    bundle.putInt(KEY_DIALOG_ID, id);
    bundle.putString(KEY_DIALOG_TITLE, title);
    bundle.putString(KEY_DIALOG_MESSAGE, message);
    bundle.putString(KEY_DIALOG_PBUTTON_TEXT, positiveButtonText);
    bundle.putString(KEY_DIALOG_NBUTTON_TEXT, negativeButtonText);
    bundle.putBoolean(KEY_DIALOG_CANCELABLE, cancelable);
    bundle.putBoolean(KEY_DIALOG_REMOVED, removed);
    bundle.putInt(KEY_DIALOG_CHOICE_TYPE, choiceType);
    bundle.putInt(KEY_DIALOG_TYPE, dialogType);
    bundle.putAll(dialogBundle.bundle);
    return bundle;
  }

  public static DialogBuilder fromBundle(Context context, Bundle bundle) {
    return DialogBuilder.builder(context).setTitle(bundle.getString(KEY_DIALOG_TITLE))
        .setMessage(bundle.getString(KEY_DIALOG_MESSAGE))
        .setNegativeButtonText(bundle.getString(KEY_DIALOG_NBUTTON_TEXT))
        .setPositiveButtonText(bundle.getString(KEY_DIALOG_PBUTTON_TEXT)).setArgs(bundle)
        .setId(bundle.getInt(KEY_DIALOG_ID)).setCancelable(bundle.getBoolean(KEY_DIALOG_CANCELABLE))
        .setChoiceType(bundle.getInt(KEY_DIALOG_CHOICE_TYPE))
        .setRemoved(bundle.getBoolean(KEY_DIALOG_REMOVED))
        .setDialogType(bundle.getInt(KEY_DIALOG_TYPE));
  }

  DialogBuilder restoreDialogFragmentState(Bundle state) {
    Bundle bundle = state.getBundle(KEY_DIALOG_BUILDER);
    return fromBundle(context, bundle);
  }

  DialogBuilder savedDialogFragmentState(Bundle state) {
    state.putBundle(KEY_DIALOG_BUILDER, toBundle());
    return this;
  }

  void _build() {
    // 如果参数里面包含了对话框的基本的参数，则设置
    String title = this.dialogBundle.getStringArg(DialogBuilder.KEY_DIALOG_TITLE);
    if (!TextUtils.isEmpty(title)) {
      this.setTitle(title);
    }
    String message = this.dialogBundle.getStringArg(DialogBuilder.KEY_DIALOG_MESSAGE);
    if (!TextUtils.isEmpty(message)) {
      this.setMessage(message);
    }
    String nButtonText = this.dialogBundle.getStringArg(DialogBuilder.KEY_DIALOG_NBUTTON_TEXT);
    if (!TextUtils.isEmpty(nButtonText)) {
      this.setNegativeButtonText(nButtonText);
    }
    String pButtonText = this.dialogBundle.getStringArg(DialogBuilder.KEY_DIALOG_PBUTTON_TEXT);
    if (!TextUtils.isEmpty(pButtonText)) {
      this.setPositiveButtonText(pButtonText);
    }
    int titleId = this.dialogBundle.getIntArg(DialogBuilder.KEY_DIALOG_TITLE);
    if (titleId != 0) {
      this.setTitle(titleId);
    }
    int messageId = this.dialogBundle.getIntArg(DialogBuilder.KEY_DIALOG_MESSAGE);
    if (messageId != 0) {
      this.setMessage(messageId);
    }
    int nButtonTextId = this.dialogBundle.getIntArg(DialogBuilder.KEY_DIALOG_NBUTTON_TEXT);
    if (nButtonTextId != 0) {
      this.setNegativeButtonText(nButtonText);
    }
    int pButtonTextId = this.dialogBundle.getIntArg(DialogBuilder.KEY_DIALOG_PBUTTON_TEXT);
    if (pButtonTextId != 0) {
      this.setPositiveButtonText(pButtonText);
    }
  }

  public IDialogProvider create() {
    _build();
    DialogProvider dialogProvider = new DialogProvider(this, mDialogFactory);
    return dialogProvider;
  }

  public IDialogProvider show() {
    if (this.dialogClass == null) {
      throw new NullPointerException("未指定要显示的dialogClass或者dialogType");
    } else {
      return create().showDialog();
    }

  }

}
