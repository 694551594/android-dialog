package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogProvider;

/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class ListDialogProvider extends DialogProvider {

  @Override
  public Dialog createInnerDialog(DialogBuilder dialogBuilder) {
    AlertDialog.Builder builder = new AlertDialog.Builder(dialogBuilder.getContext())
        .setTitle(dialogBuilder.getTitle()).setOnCancelListener(dialogBuilder.getOnCancelListener())
        .setOnDismissListener(dialogBuilder.getOnDismissListener());

    if (dialogBuilder.getChoiceType() == DialogBuilder.TYPE_CHOICE_NORMAL) {
      builder.setItems(dialogBuilder.getChoiceItems(), new ChoiceClickListener(dialogBuilder));
    } else if (dialogBuilder.getChoiceType() == DialogBuilder.TYPE_CHOICE_SINGLE) {
      builder.setSingleChoiceItems(dialogBuilder.getChoiceItems(), dialogBuilder.getChoiceItem(),
          new ChoiceClickListener(dialogBuilder));
    } else if (dialogBuilder.getChoiceType() == DialogBuilder.TYPE_CHOICE_MULTI) {
      // TODO多选
    }
    if (dialogBuilder.getChoiceAdapter() != null) {
      builder.setAdapter(dialogBuilder.getChoiceAdapter(), new ChoiceClickListener(dialogBuilder));
    }
    return builder.create();
  }

  static class ChoiceClickListener implements DialogInterface.OnClickListener {
    DialogBuilder dialogBuilder;

    public ChoiceClickListener(DialogBuilder dialogBuilder) {
      this.dialogBuilder = dialogBuilder;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
      dialog.dismiss();
      if (dialogBuilder.getOnChoiceClickListener() != null) {
        dialogBuilder.getOnChoiceClickListener().onClick(dialog, which);
      }
    }

  }

}
