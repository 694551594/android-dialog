package cn.yhq.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;

/**
 * 对话框生成工厂
 * 
 * @author Yanghuiqiang 2015-10-16
 * 
 */
public class DialogFactory implements IDialogFactory, OnDialogDismissListener {
  private static final String TAG = "DialogFactory";
  // 保存了activity当前所有的对话框实例
  private SparseArray<BaseDialogFragment> mDialogs = new SparseArray<BaseDialogFragment>();

  // 对话框ids的储存key
  public final static String KEY_DIALOG_IDS = "dialog_ids";
  // fragment tag前缀，和id结合用于标示不同的fragment
  public final static String KEY_DIALOG_TAG = "DialogFragment_";

  public DialogFactory() {}

  /**
   * 创建对话框
   * 
   */
  @Override
  public BaseDialogFragment createDialogFragment(DialogBuilder builder) {
    onPrepareDialogFragment(null, builder);
    BaseDialogFragment dialogFragment = (BaseDialogFragment) Fragment
        .instantiate(builder.getContext(), builder.getDialogClass().getName(), builder.toBundle());
    initFragment(dialogFragment, builder);
    return dialogFragment;
  }

  private void initFragment(BaseDialogFragment dialogFragment, DialogBuilder builder) {
    dialogFragment.setOnDialogDismissListener(this);
    dialogFragment.setCancelable(builder.isCancelable());
    dialogFragment.setDialogBuilder(builder);
    mDialogs.put(builder.getId(), dialogFragment);
  }

  /**
   * 显示对话框
   * 
   */
  @Override
  public void showDialogFragment(DialogBuilder builder) {
    int id = builder.getId();
    BaseDialogFragment dialogFragment = this.mDialogs.get(id);
    // 对话框显示后是否被保留
    if (dialogFragment == null) {
      dialogFragment = createDialogFragment(builder);
    }
    // 如果fragmentmanager为null，则显示普通的对话框，普通对话框的状态无法被保存，即旋转屏幕后对话框和对话框保持的数据都会消失
    if (builder.getFragmentManager() == null) {
      dialogFragment.showNormalDialog();
    } else {
      try {
        if (!dialogFragment.isAdded()) {
          dialogFragment.show(builder.getFragmentManager(), KEY_DIALOG_TAG + id);
        }
      } catch (Exception e) {
        Log.e(TAG, e.getLocalizedMessage());
        // 可以保存状态的对话框显示失败，则以正常对话框显示出来
        dialogFragment.showNormalDialog();
      }
    }
  }

  /**
   * 关闭对话框
   * 
   */
  @Override
  public void dismissDialogFragment(int id) {
    BaseDialogFragment dialogFragment = this.mDialogs.get(id);
    if (dialogFragment == null) {
      return;
    }
    try {
      dialogFragment.dismissDialog();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 在create和对话框状态恢复的时候回调的一个方法
   * 
   */
  @Override
  public void onPrepareDialogFragment(DialogFragment dialogFragment, DialogBuilder builder) {

  }

  /**
   * 对话框状态保存
   *
   */
  @Override
  public void savedDialogFragmentState(Bundle outState) {
    ArrayList<Integer> list = new ArrayList<Integer>();
    for (int i = 0; i < this.mDialogs.size(); i++) {
      list.add(this.mDialogs.keyAt(i));
    }
    if (!list.isEmpty()) {
      outState.putIntegerArrayList(KEY_DIALOG_IDS, list);
    }
  }

  /**
   * 对话框状态恢复
   *
   */
  @Override
  public void restoreDialogFragmentState(FragmentManager fragmentManager, Bundle state) {
    ArrayList<Integer> list = state.getIntegerArrayList(KEY_DIALOG_IDS);
    if (list == null) {
      return;
    }
    for (Integer id : list) {
      BaseDialogFragment dialogFragment =
          (BaseDialogFragment) fragmentManager.findFragmentByTag(KEY_DIALOG_TAG + id);
      if (dialogFragment == null) {
        continue;
      }
      DialogBuilder dialogBuilder = dialogFragment.getDialogBuilder();
      initFragment(dialogFragment, dialogBuilder);
      onPrepareDialogFragment(dialogFragment, dialogBuilder);
      mDialogs.put(id, dialogFragment);
    }
  }

  /**
   * 是否移除对话框缓存，默认是移除的
   * 
   */
  @Override
  public void onDismiss(int id, DialogBuilder builder, DialogInterface dialog) {
    if (builder.isRemoved()) {
      mDialogs.remove(id);
    }
  }

}
