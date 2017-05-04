package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.app.ProgressDialog;

import cn.yhq.dialog.R;
import cn.yhq.dialog.builder.LoadingDialogBuilder;
import cn.yhq.dialog.core.DialogProvider;

/**
 * Created by Yanghuiqiang on 2016/12/2.
 */

public class LoadingDialogProvider1 extends DialogProvider<LoadingDialogBuilder> {

    @Override
    public Dialog createInnerDialog(LoadingDialogBuilder dialogBuilder) {
        final ProgressDialog dialog = new ProgressDialog(dialogBuilder.getContext());
        dialog.setIndeterminate(true);
        dialog.setProgressNumberFormat(null);
        dialog.setProgressPercentFormat(null);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        if (dialogBuilder.getMessage() != null) {
            dialog.setMessage(dialogBuilder.getMessage());
        } else {
            dialog.setMessage(dialogBuilder.getContext().getString(R.string.loading));
        }
        return dialog;
    }


}
