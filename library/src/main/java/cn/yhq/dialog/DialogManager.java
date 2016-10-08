package cn.yhq.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * 对话框管理器，提供状态保存对话框的统一管理
 * 
 * @author Yanghuiqiang 2015-10-19
 * 
 */
public class DialogManager implements IDialogManager {
  // 对话框生成工厂
  private DialogFactory mDialogFactory;
  // 当前的context
  private Context mContext;
  // 当前的fragmentmanager
  private FragmentManager mFragmentManager;
  private OnPrepareDialogListener mOnPrepareDialogListener;

  public DialogManager(Context context, FragmentManager fragmentManager) {
    this.mContext = context;
    this.mFragmentManager = fragmentManager;
    this.mDialogFactory = new DialogFactory() {

      @Override
      public void onPrepareDialogFragment(DialogFragment dialogFragment, DialogBuilder builder) {
        super.onPrepareDialogFragment(dialogFragment, builder);
        if (mOnPrepareDialogListener != null) {
          mOnPrepareDialogListener.onPrepareDialogFragment(builder.getId(), builder);
        }
      }

    };
  }

  public void setOnPrepareDialogListener(OnPrepareDialogListener onPrepareDialogListener) {
    this.mOnPrepareDialogListener = onPrepareDialogListener;
  }

  public DialogBuilder newDialogBuilder() {
    DialogBuilder builder = DialogBuilder.builder(mContext).setDialogFactory(mDialogFactory);
    initBuilder(builder);
    return builder;
  }

  private void initBuilder(final DialogBuilder builder) {
    builder.setDialogFactory(mDialogFactory);
    Context context = builder.getContext();
    // 如果context为空，则设置为当前的context
    if (context == null) {
      builder.setContext(mContext);
    }
    // 如果fragmentManager为空，则判断context是否为空，
    // 如果context为空，则设置为当前的fragmentManager，
    // 如果不为空，则判断是否和当前的context相同，如果不同则设置为新的context里面的fragmentManager，
    // 对话框会显示到指定的fragmentManager里
    if (builder.getFragmentManager() == null) {
      if (context != null && context != mContext) {
        if (context instanceof FragmentActivity) {
          FragmentActivity activity = (FragmentActivity) context;
          builder.setFragmentManager(activity.getSupportFragmentManager());
        }
      } else {
        builder.setFragmentManager(mFragmentManager);
      }
    }
  }

  /**
   * 创建对话框provider，此接口负责显示各种对话框
   * 
   * @param builder
   * @return
   */
  private IDialogProvider createDialogProvider(DialogBuilder builder) {
    initBuilder(builder);
    return builder.create();
  }

  @Override
  public void dismissDialogFragment(int id) {
    mDialogFactory.dismissDialogFragment(id);
  }

  @Override
  public void showDialog(DialogBuilder builder) {
    createDialogProvider(builder).showDialog();
  }

  @Override
  public void showDialogFragment(int id) {
    createDialogProvider(DialogBuilder.builder(mContext).setId(id)).showDialog();
  }

  @Override
  public void showDialogFragment(int id, DialogBuilder.DialogBundle bundle) {
    createDialogProvider(DialogBuilder.builder(mContext).setId(id).setDialogBundle(bundle))
        .showDialog();
  }

  public void savedDialogFragmentState(Bundle state) {
    mDialogFactory.savedDialogFragmentState(state);
  }

  public void restoreDialogFragmentState(Bundle state) {
    mDialogFactory.restoreDialogFragmentState(mFragmentManager, state);
  }

}
