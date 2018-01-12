package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;

import cn.yhq.dialog.builder.MessageDialogBuilder;
import cn.yhq.dialog.core.DialogProvider;

/**
 * Created by Yanghuiqiang on 2017/3/6.
 */

public class MessageDialogProvider extends DialogProvider<MessageDialogBuilder> {

    @Override
    public Dialog createInnerDialog(MessageDialogBuilder dialogBuilder) {
        dialogBuilder.defaultButtonText();
        return new AlertDialog.Builder(dialogBuilder.getContext()).setTitle(dialogBuilder.getTitle())
                .setMessage(dialogBuilder.getMessage())
                .setPositiveButton(dialogBuilder.getPositiveButtonText(),
                        dialogBuilder.getOnPositiveButtonClickListener())
                .create();
    }

}
