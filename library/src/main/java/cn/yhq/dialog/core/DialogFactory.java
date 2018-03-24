package cn.yhq.dialog.core;

import android.util.SparseArray;

import cn.yhq.dialog.provider.AlertDialogProvider;
import cn.yhq.dialog.provider.BottomSheetDialogProvider;
import cn.yhq.dialog.provider.ContentViewDialogProvider;
import cn.yhq.dialog.provider.EditTextDialogProvider;
import cn.yhq.dialog.provider.ListDialogProvider;
import cn.yhq.dialog.provider.LoadingDialogProvider0;
import cn.yhq.dialog.provider.LoadingDialogProvider1;
import cn.yhq.dialog.provider.LoadingDialogProvider2;
import cn.yhq.dialog.provider.MessageDialogProvider;
import cn.yhq.dialog.provider.ProgressDialogProvider;


/**
 * 对话框生成工厂
 *
 * @author Yanghuiqiang 2015-10-16
 */
public final class DialogFactory {
    private final static SparseArray<IDialogProvider<?>> dialogProviders = new SparseArray<>();

    static {
        register(DialogType.DIALOG_OTHER, new ContentViewDialogProvider<>());
        register(DialogType.DIALOG_MESSAGE, new MessageDialogProvider());
        register(DialogType.DIALOG_ALERT, new AlertDialogProvider());
        register(DialogType.DIALOG_LOADING0, new LoadingDialogProvider0());
        register(DialogType.DIALOG_LOADING1, new LoadingDialogProvider1());
        register(DialogType.DIALOG_LOADING2, new LoadingDialogProvider2());
        register(DialogType.DIALOG_LIST, new ListDialogProvider());
        register(DialogType.DIALOG_EDIT_TEXT, new EditTextDialogProvider());
        register(DialogType.DIALOG_BOTTOM_SHEET, new BottomSheetDialogProvider());
        register(DialogType.DIALOG_PROGRESS, new ProgressDialogProvider());
    }

    public static <T extends DialogBuilder<T>> void register(int dialogType, IDialogProvider<T> dialogProvider) {
        dialogProviders.put(dialogType, dialogProvider);
    }

    public static <T extends DialogBuilder<T>> IDialog create(T dialogBuilder) {
        int dialogType = dialogBuilder.getDialogType();
        IDialogProvider<T> dialogProvider = (IDialogProvider<T>) dialogProviders.get(dialogType);
        if (dialogProvider == null) {
            throw new NullPointerException("没有找到DialogProvider: " + dialogType);
        }
        return dialogProvider.createDialog(dialogBuilder);
    }

}
