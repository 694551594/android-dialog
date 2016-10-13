package cn.yhq.dialog.provider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogProvider;

/**
 * Created by Yanghuiqiang on 2016/10/8.
 */
public class ListDialogProvider extends DialogProvider {

  @Override
  public Dialog createInnerDialog(final DialogBuilder dialogBuilder) {
    AlertDialog.Builder builder = new AlertDialog.Builder(dialogBuilder.getContext())
        .setTitle(dialogBuilder.getTitle()).setOnCancelListener(dialogBuilder.getOnCancelListener())
        .setOnDismissListener(dialogBuilder.getOnDismissListener());
    final DialogInterface.OnClickListener onChoiceClickListener =
        dialogBuilder.getOnChoiceClickListener();
    final DialogBuilder.OnChoiceListener onChoiceListener = dialogBuilder.getOnChoiceListener();
    final DialogInterface.OnClickListener onPositiveButtonClickListener =
        dialogBuilder.getOnPositiveButtonClickListener();
    final DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener =
        dialogBuilder.getOnMultiChoiceClickListener();
    final DialogBuilder.OnMultiChoiceListener onMultiChoiceListener =
        dialogBuilder.getOnMultiChoiceListener();
    if (dialogBuilder.getChoiceType() == DialogBuilder.TYPE_CHOICE_NORMAL) {
      // 这种对话框选择了后会关闭
      if (dialogBuilder.getChoiceItems() != null) {
        builder.setItems(dialogBuilder.getChoiceItems(), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            if (onChoiceClickListener != null) {
              onChoiceClickListener.onClick(dialog, which);
            }
            if (onChoiceListener != null) {
              onChoiceListener.onChoiceItem(dialogBuilder.getChoiceItems()[which]);
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
              onChoiceListener.onChoiceItem(dialogBuilder.getChoiceAdapter().getItem(which));
            }
          }
        });
      }
    } else if (dialogBuilder.getChoiceType() == DialogBuilder.TYPE_CHOICE_SINGLE) {
      // 这种方式点击确定按钮才会关闭
      final int[] checkedItems = new int[1];
      if (dialogBuilder.getChoiceItems() != null) {
        builder.setSingleChoiceItems(dialogBuilder.getChoiceItems(), dialogBuilder.getCheckedItem(),
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
                  onChoiceListener.onChoiceItem(dialogBuilder.getChoiceItems()[checkedItems[0]]);
                } else if (dialogBuilder.getChoiceAdapter() != null) {
                  onChoiceListener
                      .onChoiceItem(dialogBuilder.getChoiceAdapter().getItem(checkedItems[0]));
                }

              }
            }
          });
    } else if (dialogBuilder.getChoiceType() == DialogBuilder.TYPE_CHOICE_MULTI) {
      // 这种方式点击确定按钮才会关闭
      if (dialogBuilder.getChoiceItems() != null) {
        final boolean items[] = new boolean[dialogBuilder.getChoiceItems().length];
        for (int i = 0; i < items.length; i++) {
          items[i] = false;
          for (int j = 0; j < dialogBuilder.getCheckedItems().length; j++) {
            if (i == dialogBuilder.getCheckedItems()[j]) {
              items[i] = true;
              break;
            }
          }
        }
        builder.setMultiChoiceItems(dialogBuilder.getChoiceItems(), items,
            new DialogInterface.OnMultiChoiceClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                items[which] = isChecked;
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
                    for (int i = 0; i < items.length; i++) {
                      if (items[i]) {
                        checkedItems.add(dialogBuilder.getChoiceItems()[i]);
                      }
                    }
                    if (onMultiChoiceListener != null) {
                      onMultiChoiceListener.onMultiChoiceItems(checkedItems.toArray());
                    }
                  }
                });
      }
    }
    builder.setNegativeButton(dialogBuilder.getNegativeButtonText(),
            dialogBuilder.getOnNegativeButtonClickListener());
    return builder.create();
  }

}
