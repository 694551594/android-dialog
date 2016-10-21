package cn.yhq.dialog.core;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

public class DialogFragment extends android.support.v4.app.DialogFragment {
  private IDialog dialog;

  @NonNull
  @Override
  public final Dialog onCreateDialog(Bundle savedInstanceState) {
    IDialogCreator creator = null;
    Fragment fragment = this.getParentFragment();
    Activity activity = this.getActivity();
    if (fragment != null) {
      if (fragment instanceof IDialogCreator) {
        creator = (IDialogCreator) fragment;
      }
    } else {
      if (activity instanceof IDialogCreator) {
        creator = (IDialogCreator) activity;
      }
    }
    if (creator != null) {
      Bundle bundle = this.getArguments();
      int id = bundle.getInt("id");
      Bundle args = bundle.getBundle("args");
      dialog = creator.createDialog(id, args);
      return dialog.getInnerDialog();
    }
    return super.onCreateDialog(savedInstanceState);
  }

  public static DialogFragment create(Context context, int id, Bundle args) {
    Bundle bundle = new Bundle();
    bundle.putInt("id", id);
    bundle.putBundle("args", args);
    DialogFragment dialogFragment =
        (DialogFragment) Fragment.instantiate(context, DialogFragment.class.getName(), bundle);
    return dialogFragment;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    DialogBuilder.OnStateChangeListener onStateChangeListener = getOnStateChangeListener();
    if (onStateChangeListener != null) {
      onStateChangeListener.onSaveInstanceState(dialog, outState);
    }
  }

  private DialogBuilder.OnStateChangeListener getOnStateChangeListener() {
    DialogBuilder.OnStateChangeListener onStateChangeListener =
        ((cn.yhq.dialog.core.Dialog) dialog).getDialogBuilder().getOnStateChangeListener();
    return onStateChangeListener;
  }

  @Override
  public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
    LayoutInflater layoutInflater = super.getLayoutInflater(savedInstanceState);
    if (savedInstanceState != null) {
      DialogBuilder.OnStateChangeListener onStateChangeListener = getOnStateChangeListener();
      if (onStateChangeListener != null) {
        onStateChangeListener.onRestoreInstanceState(dialog, savedInstanceState);
      }
    }
    return layoutInflater;
  }

}
