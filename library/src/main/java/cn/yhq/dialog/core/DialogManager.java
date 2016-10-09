package cn.yhq.dialog.core;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by Yanghuiqiang on 2016/10/9.
 */

public final class DialogManager {
  private Context context;
  private FragmentManager fragmentManager;

  public DialogManager(FragmentActivity activity) {
    this.context = activity;
    if (this.context instanceof IDialogCreator) {
      this.fragmentManager = activity.getSupportFragmentManager();
    } else {
      throw new IllegalArgumentException("当前的activity需要实现IDialogCreator接口");
    }
  }

  public DialogManager(Fragment fragment) {
    this.context = fragment.getContext();
    if (fragment instanceof IDialogCreator) {
      this.fragmentManager = fragment.getChildFragmentManager();
    } else {
      throw new IllegalArgumentException("当前的fragment需要实现IDialogCreator接口");
    }
  }

  public void showDialog(int id, Bundle args) {
    DialogFragment dialogFragment = DialogFragment.create(this.context, id, args);
    dialogFragment.show(fragmentManager, dialogFragment.getClass().getName());
  }

  public void showDialog(int id) {
    showDialog(id, null);
  }

}
