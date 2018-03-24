package cn.yhq.dialog.builder;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.DialogType;

/**
 * Created by Yanghuiqiang on 2017/5/4.
 */

public class ListDialogBuilder extends DialogBuilder<ListDialogBuilder> {
    public final static int TYPE_CHOICE_NORMAL = 0;
    public final static int TYPE_CHOICE_SINGLE = 1;
    public final static int TYPE_CHOICE_MULTI = 2;

    private int choiceType = TYPE_CHOICE_NORMAL;

    private List<Object> choiceItems;
    private ListAdapter choiceAdapter;
    private int checkedItem;
    private int[] checkedItems;

    private DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener;
    private OnChoiceListener onChoiceListener;
    private OnMultiChoiceListener onMultiChoiceListener;
    private DialogInterface.OnClickListener onChoiceClickListener;

    public interface OnChoiceListener<T> {
        void onChoiceItem(int index, T item);
    }

    public interface OnMultiChoiceListener<T> {
        void onMultiChoiceItems(List<Integer> indexs, T[] items);
    }

    public ListDialogBuilder(Context context) {
        super(context, DialogType.DIALOG_LIST);
    }

    public int getChoiceType() {
        return choiceType;
    }

    public ListDialogBuilder setChoiceType(int choiceType) {
        this.choiceType = choiceType;
        return self();
    }

    public ListAdapter getChoiceAdapter() {
        return choiceAdapter;
    }

    public ListDialogBuilder setChoiceAdapter(ListAdapter choiceAdapter) {
        this.choiceAdapter = choiceAdapter;
        if (this.getChoiceType() == TYPE_CHOICE_MULTI) {
            throw new IllegalArgumentException("多选对话框不可以设置adapter");
        }
        return self();
    }

    public int getCheckedItem() {
        return checkedItem;
    }

    public ListDialogBuilder setCheckedItem(int checkedItem) {
        this.checkedItem = checkedItem;
        return self();
    }

    public ListDialogBuilder setChoiceItems(List<?> choiceItems) {
        this.choiceItems = new ArrayList<>();
        for (Object o : choiceItems) {
            this.choiceItems.add(o);
        }
        return self();
    }

    public <T> ListDialogBuilder setChoiceItems(T... choiceItems) {
        this.choiceItems = new ArrayList<>();
        for (Object o : choiceItems) {
            this.choiceItems.add(o);
        }
        return self();
    }

    public List<Object> getChoiceItems() {
        return choiceItems;
    }

    public DialogInterface.OnMultiChoiceClickListener getOnMultiChoiceClickListener() {
        return onMultiChoiceClickListener;
    }

    public ListDialogBuilder setOnMultiChoiceClickListener(
            DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
        this.onMultiChoiceClickListener = onMultiChoiceClickListener;
        return self();
    }

    public DialogInterface.OnClickListener getOnChoiceClickListener() {
        return onChoiceClickListener;
    }

    public ListDialogBuilder setOnChoiceClickListener(
            DialogInterface.OnClickListener onChoiceClickListener) {
        this.onChoiceClickListener = onChoiceClickListener;
        return self();
    }

    public int[] getCheckedItems() {
        return checkedItems;
    }

    public ListDialogBuilder setCheckedItems(int... checkedItems) {
        this.checkedItems = checkedItems;
        return self();
    }

    public <T> OnChoiceListener<T> getOnChoiceListener() {
        return onChoiceListener;
    }

    public <T> ListDialogBuilder setOnChoiceListener(OnChoiceListener<T> onChoiceListener) {
        this.onChoiceListener = onChoiceListener;
        return self();
    }

    public <T> OnMultiChoiceListener<T> getOnMultiChoiceListener() {
        return onMultiChoiceListener;
    }

    public <T> ListDialogBuilder setOnMultiChoiceListener(OnMultiChoiceListener<T> onMultiChoiceListener) {
        this.onMultiChoiceListener = onMultiChoiceListener;
        return self();
    }
}
