package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.FrameLayout;

import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogProvider;

/**
 * Created by Yanghuiqiang on 2016/10/20.
 */

public class BottomSheetDialogProvider extends DialogProvider {

  @Override
  public Dialog createInnerDialog(DialogBuilder dialogBuilder) {
    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(dialogBuilder.getContext());

    FrameLayout frameLayout = new FrameLayout(dialogBuilder.getContext());
    FrameLayout.LayoutParams layoutParams = dialogBuilder.getLayoutParams();

    if (layoutParams == null) {
      layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    frameLayout.setLayoutParams(layoutParams);
    if (dialogBuilder.getContentViewResId() != 0) {
      dialogBuilder.setContentView(
              View.inflate(dialogBuilder.getContext(), dialogBuilder.getContentViewResId(), null));
    }

    if (dialogBuilder.getContentView() != null) {
      frameLayout.addView(dialogBuilder.getContentView(), layoutParams);
    }

    bottomSheetDialog.setContentView(frameLayout);

    return bottomSheetDialog;
  }
}
