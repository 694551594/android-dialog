package cn.yhq.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

public interface IDialogFactory {

  DialogFragment createDialogFragment(DialogBuilder builder);

  void showDialogFragment(DialogBuilder builder);

  void dismissDialogFragment(int id);

  void onPrepareDialogFragment(DialogFragment dialogFragment, DialogBuilder builder);

  void savedDialogFragmentState(Bundle state);

  void restoreDialogFragmentState(FragmentManager fragmentManager, Bundle state);

}
