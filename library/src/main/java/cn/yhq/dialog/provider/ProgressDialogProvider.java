package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import cn.yhq.dialog.R;
import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogProvider;

/**
 * Created by Yanghuiqiang on 2016/10/10.
 */

public class ProgressDialogProvider extends DialogProvider {

  @Override
  public Dialog createInnerDialog(DialogBuilder dialogBuilder) {
    View contentView = LayoutInflater.from(dialogBuilder.getContext())
        .inflate(R.layout.comm_progress_dialog, null, false);
    TextView messageTextView = (TextView) contentView.findViewById(R.id.progress_message);
    if (dialogBuilder.getMessage() != null) {
      messageTextView.setText(dialogBuilder.getMessage());
    } else {
      messageTextView.setText(R.string.loading);
    }
    final DonutProgress donutProgress =
        (DonutProgress) contentView.findViewById(R.id.donut_progress);
    dialogBuilder.setOnProgressListener(new DialogBuilder.OnProgressListener() {
      @Override
      public void onProgress(int progress) {
        donutProgress.setProgress(progress);
      }
    });
    Dialog dialog = new AlertDialog.Builder(dialogBuilder.getContext()).setView(contentView)
        .create();
    Window window = dialog.getWindow();
    window.setBackgroundDrawableResource(android.R.color.transparent);
    return dialog;
  }

}
