package cn.yhq.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;

public abstract class BaseDialogFragment extends DialogFragment {
  private static final String TAG = "BaseDialogFragment";
  protected int id;
  protected DialogBuilder mDialogBuilder;
  protected OnDialogDismissListener mOnDialogDismissListener;

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    this.mDialogBuilder.savedDialogFragmentState(outState);
  }

  @Override
  public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
    return super.getLayoutInflater(savedInstanceState);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null) {
      mDialogBuilder = DialogBuilder.builder(this.getContext())
          .setFragmentManager(this.getFragmentManager() == null
              ? this.getFragmentManager()
              : this.getChildFragmentManager())
          .setDialogClass(this.getClass()).restoreDialogFragmentState(savedInstanceState);
      this.setCancelable(mDialogBuilder.isCancelable());
    }
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    if (mDialogBuilder.getOnDismissListener() != null) {
      mDialogBuilder.getOnDismissListener().onDismiss(dialog);
    }
    if (mOnDialogDismissListener != null) {
      mOnDialogDismissListener.onDismiss(mDialogBuilder.getId(), mDialogBuilder, dialog);
    }
  }

  @Override
  public void onCancel(DialogInterface dialoginterface) {
    super.onCancel(dialoginterface);
    if (mDialogBuilder.getOnCancelListener() != null) {
      mDialogBuilder.getOnCancelListener().onCancel(dialoginterface);
    }
  }

  public void setOnDialogDismissListener(OnDialogDismissListener mOnDialogDismissListener) {
    this.mOnDialogDismissListener = mOnDialogDismissListener;
  }

  /**
   * @return the mDialogBuilder
   */
  public DialogBuilder getDialogBuilder() {
    return mDialogBuilder;
  }

  /**
   * @param mDialogBuilder the mDialogBuilder to set
   */
  public void setDialogBuilder(DialogBuilder mDialogBuilder) {
    this.mDialogBuilder = mDialogBuilder;
    this.id = mDialogBuilder.getId();
    this.setCancelable(mDialogBuilder.isCancelable());
  }

  private Dialog mNormalDialog;

  public void dismissDialog() {
    try {
      if (mNormalDialog != null && mNormalDialog.isShowing()) {
        mNormalDialog.dismiss();
      } else {
        if (this.mDialogBuilder.getFragmentManager() != null) {
          this.dismiss();
        }
      }
    } catch (Exception e) {
      Log.e(TAG, e.getLocalizedMessage());
    }
  }

  public void showNormalDialog() {
    try {
      mNormalDialog = onCreateDialog(null);
      mNormalDialog.show();
    } catch (Exception e) {
      Log.e(TAG, e.getLocalizedMessage());
    }

  }

}
