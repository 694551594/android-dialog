package cn.yhq.dialog.builder;

import android.content.Context;

import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogType;

/**
 * Created by Yanghuiqiang on 2017/5/4.
 */

public class AlertDialogBuilder extends DialogBuilder<AlertDialogBuilder> {

    public AlertDialogBuilder(Context context) {
        super(context, DialogType.DIALOG_ALERT);
    }

}
