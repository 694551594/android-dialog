package cn.yhq.dialog.builder;

import android.content.Context;

import cn.yhq.dialog.core.DialogType;

/**
 * Created by Yanghuiqiang on 2017/5/4.
 */

public class BottomSheetDialogBuilder extends OtherDialogBuilder<BottomSheetDialogBuilder> {

    public BottomSheetDialogBuilder(Context context) {
        super(context, DialogType.DIALOG_BOTTOM_SHEET);
    }

}
