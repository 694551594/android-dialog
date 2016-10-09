package cn.yhq.dialog.core;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class DialogFragment extends android.support.v4.app.DialogFragment {

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
      IDialog dialog = creator.createDialog(id, args);
      return dialog.getInnerDialog();
    }
    return super.onCreateDialog(savedInstanceState);
  }

  public static DialogFragment create(Context context, int id, Bundle args) {
    Bundle bundle = new Bundle();
    bundle.putInt("id", id);
    bundle.putBundle("args", args);
    final DialogFragment dialogFragment =
        (DialogFragment) Fragment.instantiate(context, DialogFragment.class.getName(), bundle);
    return dialogFragment;
  }
}
