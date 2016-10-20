package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialog;

import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogProvider;

/**
 * Created by Yanghuiqiang on 2016/10/20.
 */

public class BottomSheetDialogProvider extends DialogProvider {

  @Override
  public Dialog createInnerDialog(DialogBuilder dialogBuilder) {
    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(dialogBuilder.getContext());
    if (dialogBuilder.getContentView() == null) {
      bottomSheetDialog.setContentView(dialogBuilder.getContentViewResId());
    } else {
      bottomSheetDialog.setContentView(dialogBuilder.getContentView());
    }
    return null;
  }
}
