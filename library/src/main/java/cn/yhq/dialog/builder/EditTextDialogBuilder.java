package cn.yhq.dialog.builder;

import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import cn.yhq.dialog.R;
import cn.yhq.dialog.core.DialogType;
import cn.yhq.utils.EditTextUtils;

/**
 * Created by Yanghuiqiang on 2017/5/4.
 */

public class EditTextDialogBuilder extends OtherDialogBuilder<EditTextDialogBuilder> {
    private OnEditTextDialogListener onEditTextDialogListener;

    public interface OnEditTextDialogListener {
        void onEditTextCreated(EditText editText);

        /**
         * 点击确定按钮的时候回调方法，返回true不会关闭对话框，返回false会关闭对话框
         *
         * @param editText
         * @param text
         * @return
         */
        boolean onEditTextSelected(EditText editText, String text);
    }

    public EditTextDialogBuilder(Context context) {
        super(context, DialogType.DIALOG_EDIT_TEXT);
        this.setContentViewResId(R.layout.comm_dialog_edittext);
        final OnContentViewListener onContentViewListener = this.getOnContentViewListener();
        this.setOnContentViewListener(new OnContentViewListener() {
            @Override
            public void onContentViewCreated(View contentView) {
                if (onContentViewListener != null) {
                    onContentViewListener.onContentViewCreated(contentView);
                }
                final EditText editText = (EditText) contentView.findViewById(R.id.edittext);
                EditTextUtils.setSelectionToEnd(editText);
                // 调用控件初始化接口
                final OnEditTextDialogListener onEditTextDialogListener = getOnEditTextDialogListener();
                if (onEditTextDialogListener != null) {
                    onEditTextDialogListener.onEditTextCreated(editText);
                }
//                final DialogInterface.OnClickListener onPositiveButtonClickListener = getOnPositiveButtonClickListener();
//                setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (onPositiveButtonClickListener != null) {
//                            onPositiveButtonClickListener.onClick(dialog, which);
//                        }
//                        if (onEditTextDialogListener != null) {
//                            boolean result = onEditTextDialogListener.onEditTextSelected(editText, editText.getText().toString());
//                            if (!result) {
//                                dialog.dismiss();
//                            }
//                        }
//                    }
//                });
                final DialogInterface.OnShowListener onShowListener = getOnShowListener();
                setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        if (onShowListener != null) {
                            onShowListener.onShow(dialog);
                        }
                        // 对话框show后才可以获取到button的对象
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // 如果返回true不会关闭对话框
                                        boolean result = getOnEditTextDialogListener().onEditTextSelected(editText, editText.getText().toString());
                                        if (!result) {
                                            dialog.dismiss();
                                        }
                                    }
                                });
                    }
                });
            }
        });

    }

    public OnEditTextDialogListener getOnEditTextDialogListener() {
        return onEditTextDialogListener;
    }

    public EditTextDialogBuilder setOnEditTextDialogListener(
            OnEditTextDialogListener onEditTextDialogListener) {
        this.onEditTextDialogListener = onEditTextDialogListener;
        return self();
    }
}
