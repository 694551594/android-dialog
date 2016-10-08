package cn.yhq.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import cn.yhq.dialog.utils.EditTextUtils;


/**
 * Created by Yanghuiqiang on 2016/10/8.
 */

public class EditTextDialogFragment extends BaseDialogFragment {
  private EditText editText;
  private CheckBox checkBox;
  private AlertDialog dialog;

  @SuppressLint("InflateParams")
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    View contentView =
        LayoutInflater.from(this.mDialogBuilder.getContext()).inflate(R.layout.comm_dialog_edittext, null, false);
    editText = (EditText) contentView.findViewById(R.id.edittext);
    checkBox = (CheckBox) contentView.findViewById(R.id.checkbox);
    View checkBoxLayout = contentView.findViewById(R.id.layout_checkbox);
    AlertDialog.Builder builder = new AlertDialog.Builder(this.mDialogBuilder.getContext())
        .setTitle(this.mDialogBuilder.getTitle()).setMessage(this.mDialogBuilder.getMessage())
        .setNegativeButton(this.mDialogBuilder.getNegativeButtonText(),
            this.mDialogBuilder.getOnNegativeButtonClickListener())
        .setPositiveButton(this.mDialogBuilder.getPositiveButtonText(), null).setView(contentView);
    // checkbox默认隐藏
    checkBox.setVisibility(View.GONE);
    // 调用控件初始化接口
    if (this.mDialogBuilder.getOnEditTextDialogListener() != null) {
      this.mDialogBuilder.getOnEditTextDialogListener().onEditTextCreated(editText, checkBox);
    }
    // 因为checkbox的文本是用textview控件实现，所以这里需要转换一下。
    TextView checkboxTextView = (TextView) contentView.findViewById(R.id.checkbox_text);
    checkboxTextView.setText(checkBox.getText());
    checkBox.setText("");
    checkBoxLayout.setVisibility(checkBox.getVisibility());
    EditTextUtils.onTextToEnd(editText);
    dialog = builder.create();
    return dialog;
  }

  @Override
  public void onStart() {
    super.onStart();
    // 对话框show后才可以获取到button的对象
    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        .setOnClickListener(new PositiveButtonClickListener(dialog, editText, checkBox));
  }

  class PositiveButtonClickListener implements View.OnClickListener {
    EditText editText;
    CheckBox checkBox;
    Dialog dialog;

    public PositiveButtonClickListener(Dialog dialog, EditText editText, CheckBox checkBox) {
      this.dialog = dialog;
      this.editText = editText;
      this.checkBox = checkBox;
    }

    @Override
    public void onClick(View v) {
      // 如果返回true不会关闭对话框
      boolean result = mDialogBuilder.getOnEditTextDialogListener().onEditTextSelected(editText,
          editText.getText().toString(), checkBox, checkBox.isChecked());
      if (!result) {
        dialog.dismiss();
      }
    }

  }

}
