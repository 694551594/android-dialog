package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.app.ProgressDialog;

import cn.yhq.dialog.R;
import cn.yhq.dialog.builder.ProgressDialogBuilder;
import cn.yhq.dialog.core.DialogProvider;

/**
 * Created by Yanghuiqiang on 2016/12/2.
 */

public class ProgressDialogProvider extends DialogProvider<ProgressDialogBuilder> {

    @Override
    public Dialog createInnerDialog(ProgressDialogBuilder dialogBuilder) {
        final ProgressDialog dialog = new ProgressDialog(dialogBuilder.getContext());
        dialog.setMax(100);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        if (dialogBuilder.getMessage() != null) {
            dialog.setMessage(dialogBuilder.getMessage());
        } else {
            dialog.setMessage(dialogBuilder.getContext().getString(R.string.loading));
        }
        dialogBuilder.setOnProgressListener(new ProgressDialogBuilder.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                dialog.setProgress(progress);
            }
        });
        return dialog;
    }


}
