package cn.yhq.dialog.core;

import android.util.SparseArray;

import cn.yhq.dialog.provider.AlertDialogProvider;
import cn.yhq.dialog.provider.BottomSheetDialogProvider;
import cn.yhq.dialog.provider.CircleProgressDialogProvider;
import cn.yhq.dialog.provider.ContentViewDialogProvider;
import cn.yhq.dialog.provider.EditTextDialogProvider;
import cn.yhq.dialog.provider.ListDialogProvider;
import cn.yhq.dialog.provider.LoadingDialogProvider0;
import cn.yhq.dialog.provider.LoadingDialogProvider1;
import cn.yhq.dialog.provider.LoadingDialogProvider2;
import cn.yhq.dialog.provider.ProgressDialogProvider;

import static cn.yhq.dialog.core.DialogBuilder.DIALOG_ALERT;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_BOTTOM_SHEET;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_CIRCLE_PROGRESS;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_EDIT_TEXT;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_LIST;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_LOADING0;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_LOADING1;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_LOADING2;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_MESSAGE;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_OTHER;
import static cn.yhq.dialog.core.DialogBuilder.DIALOG_PROGRESS;

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
    register(DIALOG_LOADING0, new LoadingDialogProvider0());
    register(DIALOG_LOADING1, new LoadingDialogProvider1());
    register(DIALOG_LOADING2, new LoadingDialogProvider2());
    register(DIALOG_LIST, new ListDialogProvider());
    register(DIALOG_EDIT_TEXT, new EditTextDialogProvider());
    register(DIALOG_CIRCLE_PROGRESS, new CircleProgressDialogProvider());
    register(DIALOG_BOTTOM_SHEET, new BottomSheetDialogProvider());
    register(DIALOG_PROGRESS, new ProgressDialogProvider());
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
