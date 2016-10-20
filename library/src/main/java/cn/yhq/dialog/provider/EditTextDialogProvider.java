package cn.yhq.dialog.provider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import cn.yhq.dialog.R;
import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogProvider;
import cn.yhq.dialog.utils.EditTextUtils;


/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class EditTextDialogProvider extends DialogProvider {

  @SuppressLint("InflateParams")
  @Override
  public Dialog createInnerDialog(DialogBuilder dialogBuilder) {
    View contentView = LayoutInflater.from(dialogBuilder.getContext())
        .inflate(R.layout.comm_dialog_edittext, null, false);
    EditText editText = (EditText) contentView.findViewById(R.id.edittext);
    CheckBox checkBox = (CheckBox) contentView.findViewById(R.id.checkbox);
    View checkBoxLayout = contentView.findViewById(R.id.layout_checkbox);
    AlertDialog.Builder builder = new AlertDialog.Builder(dialogBuilder.getContext())
        .setTitle(dialogBuilder.getTitle()).setMessage(dialogBuilder.getMessage())
        .setNegativeButton(dialogBuilder.getNegativeButtonText(),
            dialogBuilder.getOnNegativeButtonClickListener())
        .setPositiveButton(dialogBuilder.getPositiveButtonText(),
            new PositiveButtonClickListener(dialogBuilder, editText, checkBox)).setView(contentView);
    // checkbox默认隐藏
    checkBox.setVisibility(View.GONE);
    // 调用控件初始化接口
    if (dialogBuilder.getOnEditTextDialogListener() != null) {
      dialogBuilder.getOnEditTextDialogListener().onEditTextCreated(editText, checkBox);
    }
    // 因为checkbox的文本是用textview控件实现，所以这里需要转换一下。
    TextView checkboxTextView = (TextView) contentView.findViewById(R.id.checkbox_text);
    checkboxTextView.setText(checkBox.getText());
    checkBox.setText("");
    checkBoxLayout.setVisibility(checkBox.getVisibility());
    EditTextUtils.onTextToEnd(editText);
    return builder.create();
  }

  static class PositiveButtonClickListener implements DialogInterface.OnClickListener {
    EditText editText;
    CheckBox checkBox;
    DialogBuilder dialogBuilder;

    public PositiveButtonClickListener(DialogBuilder dialogBuilder, EditText editText,
        CheckBox checkBox) {
      this.editText = editText;
      this.checkBox = checkBox;
      this.dialogBuilder = dialogBuilder;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
      // 如果返回true不会关闭对话框
      boolean result = dialogBuilder.getOnEditTextDialogListener().onEditTextSelected(editText,
          editText.getText().toString(), checkBox, checkBox.isChecked());
      if (!result) {
        dialog.dismiss();
      }
    }
  }

}
