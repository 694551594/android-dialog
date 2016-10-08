package cn.yhq.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class ListDialogFragment extends BaseDialogFragment {

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder =
        new AlertDialog.Builder(this.mDialogBuilder.getContext())
            .setTitle(this.mDialogBuilder.getTitle());

    if (this.mDialogBuilder.getChoiceType() == DialogBuilder.TYPE_CHOICE_NORMAL) {
      builder.setItems(this.mDialogBuilder.getChoiceItems(), new ChoiceClickListener());
    } else if (this.mDialogBuilder.getChoiceType() == DialogBuilder.TYPE_CHOICE_SINGLE) {
      builder.setSingleChoiceItems(this.mDialogBuilder.getChoiceItems(),
          this.mDialogBuilder.getChoiceItem(), new ChoiceClickListener());
    } else if (this.mDialogBuilder.getChoiceType() == DialogBuilder.TYPE_CHOICE_MULTI) {
      // TODO多选
    }
    if (this.mDialogBuilder.getChoiceAdapter() != null) {
      builder.setAdapter(this.mDialogBuilder.getChoiceAdapter(), new ChoiceClickListener());
    }
    return builder.create();
  }

  class ChoiceClickListener implements DialogInterface.OnClickListener {

    @Override
    public void onClick(DialogInterface dialog, int which) {
      dialog.dismiss();
      if (mDialogBuilder.getOnChoiceClickListener() != null) {
        mDialogBuilder.getOnChoiceClickListener().onClick(dialog, which);
      }
    }

  }

}
