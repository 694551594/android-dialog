package cn.yhq.dialog.core;


import android.os.Bundle;

/**
 * Created by Yanghuiqiang on 2016/10/9.
 */

public interface IDialogCreator {
    IDialog createDialog(int id, Bundle args);
}
