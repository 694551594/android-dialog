package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import cn.yhq.dialog.builder.ListDialogBuilder;
import cn.yhq.dialog.core.DialogProvider;

/**
 * Created by Yanghuiqiang on 2016/10/8.
 */
public class ListDialogProvider extends DialogProvider<ListDialogBuilder> {

    @Override
    public Dialog createInnerDialog(final ListDialogBuilder dialogBuilder) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(dialogBuilder.getContext()).setTitle(dialogBuilder.getTitle());
        final DialogInterface.OnClickListener onChoiceClickListener =
                dialogBuilder.getOnChoiceClickListener();
        final ListDialogBuilder.OnChoiceListener onChoiceListener = dialogBuilder.getOnChoiceListener();
        final DialogInterface.OnClickListener onPositiveButtonClickListener =
                dialogBuilder.getOnPositiveButtonClickListener();
        final DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener =
                dialogBuilder.getOnMultiChoiceClickListener();
        final ListDialogBuilder.OnMultiChoiceListener onMultiChoiceListener =
                dialogBuilder.getOnMultiChoiceListener();
        final List<String> items = new ArrayList<>();
        for (Object o : dialogBuilder.getChoiceItems()) {
            items.add(o.toString());
        }
        final CharSequence[] choiceItems = items.toArray(new String[items.size()]);
        if (dialogBuilder.getChoiceType() == ListDialogBuilder.TYPE_CHOICE_NORMAL) {
            // 这种对话框选择了后会关闭
            if (dialogBuilder.getChoiceItems() != null) {
                builder.setItems(choiceItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onChoiceClickListener != null) {
                            onChoiceClickListener.onClick(dialog, which);
                        }
                        if (onChoiceListener != null) {
                            onChoiceListener.onChoiceItem(which, dialogBuilder.getChoiceItems().get(which));
                        }
                    }
                });
            } else if (dialogBuilder.getChoiceAdapter() != null) {
                builder.setAdapter(dialogBuilder.getChoiceAdapter(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onChoiceClickListener != null) {
                            onChoiceClickListener.onClick(dialog, which);
                        }
                        if (onChoiceListener != null) {
                            onChoiceListener.onChoiceItem(which, dialogBuilder.getChoiceAdapter().getItem(which));
                        }
                    }
                });
            }
        } else if (dialogBuilder.getChoiceType() == ListDialogBuilder.TYPE_CHOICE_SINGLE) {
            // 这种方式点击确定按钮才会关闭
            dialogBuilder.setDefaultButtonText();
            final int[] checkedItems = new int[1];
            if (dialogBuilder.getChoiceItems() != null) {
                builder.setSingleChoiceItems(choiceItems, dialogBuilder.getCheckedItem(),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (onChoiceClickListener != null) {
                                    onChoiceClickListener.onClick(dialog, which);
                                }
                                checkedItems[0] = which;
                            }
                        });
            } else if (dialogBuilder.getChoiceAdapter() != null) {
                builder.setAdapter(dialogBuilder.getChoiceAdapter(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onChoiceClickListener != null) {
                            onChoiceClickListener.onClick(dialog, which);
                        }
                        checkedItems[0] = which;
                    }
                });
            }
            builder.setPositiveButton(dialogBuilder.getPositiveButtonText(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (onPositiveButtonClickListener != null) {
                                onPositiveButtonClickListener.onClick(dialog, which);
                            }
                            if (onChoiceListener != null) {
                                if (dialogBuilder.getChoiceItems() != null) {
                                    onChoiceListener.onChoiceItem(checkedItems[0],
                                            dialogBuilder.getChoiceItems().get(checkedItems[0]));
                                } else if (dialogBuilder.getChoiceAdapter() != null) {
                                    onChoiceListener.onChoiceItem(checkedItems[0],
                                            dialogBuilder.getChoiceAdapter().getItem(checkedItems[0]));
                                }
                            }
                        }
                    });
            builder.setNegativeButton(dialogBuilder.getNegativeButtonText(),
                    dialogBuilder.getOnNegativeButtonClickListener());
        } else if (dialogBuilder.getChoiceType() == ListDialogBuilder.TYPE_CHOICE_MULTI) {
            // 这种方式点击确定按钮才会关闭
            dialogBuilder.setDefaultButtonText();
            if (dialogBuilder.getChoiceItems() != null) {
                final List<Integer> choiceIndexs = new ArrayList<>();
                final boolean itemCheckeds[] = new boolean[dialogBuilder.getChoiceItems().size()];
                for (int i = 0; i < itemCheckeds.length; i++) {
                    itemCheckeds[i] = false;
                    for (int j = 0; j < dialogBuilder.getCheckedItems().length; j++) {
                        if (i == dialogBuilder.getCheckedItems()[j]) {
                            itemCheckeds[i] = true;
                            choiceIndexs.add(i);
                            break;
                        }
                    }
                }
                builder.setMultiChoiceItems(choiceItems, itemCheckeds,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                itemCheckeds[which] = isChecked;
                                if (isChecked) {
                                    choiceIndexs.add(which);
                                } else {
                                    choiceIndexs.remove(choiceIndexs.indexOf(which));
                                }
                                if (onMultiChoiceClickListener != null) {
                                    onMultiChoiceClickListener.onClick(dialog, which, isChecked);
                                }
                            }
                        }).setPositiveButton(dialogBuilder.getPositiveButtonText(),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (onPositiveButtonClickListener != null) {
                                    onPositiveButtonClickListener.onClick(dialog, which);
                                }
                                List<Object> checkedItems = new ArrayList();
                                for (int i = 0; i < itemCheckeds.length; i++) {
                                    if (itemCheckeds[i]) {
                                        checkedItems.add(dialogBuilder.getChoiceItems().get(i));
                                    }
                                }
                                if (onMultiChoiceListener != null) {
                                    onMultiChoiceListener.onMultiChoiceItems(choiceIndexs,
                                            checkedItems.toArray());
                                }
                            }
                        });
            }
            builder.setNegativeButton(dialogBuilder.getNegativeButtonText(),
                    dialogBuilder.getOnNegativeButtonClickListener());
        }
        return builder.create();
    }

}
