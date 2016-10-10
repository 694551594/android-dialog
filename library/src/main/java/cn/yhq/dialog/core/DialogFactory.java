package cn.yhq.dialog.core;

import android.util.SparseArray;

import cn.yhq.dialog.provider.AlertDialogProvider;
import cn.yhq.dialog.provider.ContentViewDialogProvider;
import cn.yhq.dialog.provider.EditTextDialogProvider;
import cn.yhq.dialog.provider.ListDialogProvider;
import cn.yhq.dialog.provider.LoadingDialogProvider;

import static cn.yhq.dialog.core.DialogBuilder.DIALOG_ALERT;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_EDIT_TEXT;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_LIST;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_LOADING;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_MESSAGE;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_OTHER;

/**
 * 对话框生成工厂
 * 
 * @author Yanghuiqiang 2015-10-16
 * 
 */
public final class DialogFactory {
  private final static SparseArray<IDialogProvider> dialogProviders = new SparseArray<>();

  static {
    register(DIALOG_OTHER, new ContentViewDialogProvider());
    register(DIALOG_MESSAGE, new AlertDialogProvider());
    register(DIALOG_ALERT, new AlertDialogProvider());
    register(DIALOG_LOADING, new LoadingDialogProvider());
    register(DIALOG_LIST, new ListDialogProvider());
    register(DIALOG_EDIT_TEXT, new EditTextDialogProvider());
  }

  public static void register(int dialogType, IDialogProvider dialogProvider) {
    dialogProviders.put(dialogType, dialogProvider);
  }

  public static IDialog create(DialogBuilder dialogBuilder) {
    int dialogType = dialogBuilder.getDialogType();
    IDialogProvider dialogProvider = dialogProviders.get(dialogType);
    return dialogProvider.createDialog(dialogBuilder);
  }

}
