package cn.yhq.dialog.core;

/**
 * Created by Yanghuiqiang on 2016/10/9.
 */

public interface IDialog {
    void dismiss();

    IDialog show();

    android.app.Dialog getInnerDialog();
}
