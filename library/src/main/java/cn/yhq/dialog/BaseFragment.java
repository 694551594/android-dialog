package cn.yhq.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import cn.yhq.dialog.core.DialogManager;
import cn.yhq.dialog.core.IDialog;
import cn.yhq.dialog.core.IDialogCreator;

/**
 * Created by Yanghuiqiang on 2016/10/9.
 */

public class BaseFragment extends Fragment implements IDialogCreator {

  private DialogManager dialogManager;

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    dialogManager = new DialogManager(this);
  }

  public void showDialogFragment(int id) {
    dialogManager.showDialog(id);
  }

  public void showDialogFragment(int id, Bundle bundle) {
    dialogManager.showDialog(id, bundle);
  }

  @Override
  public IDialog createDialog(int id, Bundle args) {
    return null;
  }
}
