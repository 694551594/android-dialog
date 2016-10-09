package cn.yhq.dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.yhq.dialog.core.DialogManager;
import cn.yhq.dialog.core.IDialog;
import cn.yhq.dialog.core.IDialogCreator;

/**
 * Created by Yanghuiqiang on 2016/10/9.
 */

public class BaseActivity extends AppCompatActivity implements IDialogCreator {
  private DialogManager dialogManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
