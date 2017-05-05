package cn.yhq.dialog.builder;

import android.content.Context;

import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogType;

/**
 * Created by Yanghuiqiang on 2017/5/4.
 */

public class LoadingDialogBuilder extends DialogBuilder<LoadingDialogBuilder> {

    protected LoadingDialogBuilder(Context context, int dialogType) {
        super(context, dialogType);
    }

    public static LoadingDialogBuilder getLoadingDialogBuilder0(Context context) {
        return new LoadingDialogBuilder(context, DialogType.DIALOG_LOADING0);
    }

    public static LoadingDialogBuilder getLoadingDialogBuilder1(Context context) {
        return new LoadingDialogBuilder(context, DialogType.DIALOG_LOADING1);
    }

    public static LoadingDialogBuilder getLoadingDialogBuilder2(Context context) {
        return new LoadingDialogBuilder(context, DialogType.DIALOG_LOADING2);
    }

}
