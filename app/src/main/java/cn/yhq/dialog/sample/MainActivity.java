package cn.yhq.dialog.sample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import cn.yhq.adapter.list.SimpleStringListAdapter;
import cn.yhq.base.BaseActivity;
import cn.yhq.dialog.builder.EditTextDialogBuilder;
import cn.yhq.dialog.builder.ListDialogBuilder;
import cn.yhq.dialog.builder.ProgressDialogBuilder;
import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.IDialog;

/**
 * 创建对话框的方式有两种：
 * <p>
 * 1、直接创建，这种方式创建的对话框不会保存状态，即当屏幕旋转等导致的activity重建，对话框会消失。
 * 2、使用DialogManager，配合IDialogCreator接口创建的对话框，这种方式创建的对话框会保存状态，在屏幕旋转后对话框不会消失。
 * 3、当使用第二种方式的时候，你可以在你的Activity基类里面实现IDialogCreator接口，并提供DialogManager的封装方法，
 * 这样在你的Activity子类里面重写IDialogCreator接口方法后，直接调用显示对话框的方法showDialogFragment就可以直接显示对话框了。
 * 具体可以参考BaseActivity与BaseFragment
 */
public class MainActivity extends BaseActivity {

    /**
     * 如果想要保存对话框的状态，需要重写此方法，然后返回构建的IDialog对象即可
     *
     * @param id
     * @param args
     * @return
     */
    @Override
    public IDialog createDialog(int id, Bundle args) {
        switch (id) {
            case 1:
                return DialogBuilder.alertDialog(this).setTitle("我是标题").setMessage(args.getString("message"))
                        .setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "点击了确认按钮", Toast.LENGTH_LONG).show();
                            }
                        }).create();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ListView listView = (ListView) this.findViewById(R.id.listview);
        final SimpleStringListAdapter adapter = SimpleStringListAdapter.create(this,
                new String[]{"载入对话框0", "消息对话框", "确认对话框", "普通选择对话框", "单选对话框", "多选对话框", "文本输入对话框", "自定义对话框", "bottom sheet对话框", "普通进度对话框", "载入对话框1", "载入对话框2"});

        listView.setAdapter(adapter);
        final Object[] list = {"选择项1", "选择项2", "选择项3", "选择项4", "选择项5", "选择项6"};
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        DialogBuilder.loadingDialog0(MainActivity.this).show();
                        break;
                    case 1:
                        DialogBuilder.messageDialog(MainActivity.this).setMessage("消息对话框").show();
                        break;
                    case 2:
                        // 此种方式创建的对话框会保存状态，旋转屏幕的时候对话框不会消失
                        Bundle args = new Bundle();
                        args.putString("message", "确认对话框");
                        showDialogFragment(1, args);
                        break;
                    case 3:
                        DialogBuilder.listDialog(MainActivity.this).setChoiceItems(list)
                                .setChoiceType(ListDialogBuilder.TYPE_CHOICE_NORMAL)
                                .setOnChoiceListener(new ListDialogBuilder.OnChoiceListener<String>() {
                                    // 对话框关闭后回调的一个方法，返回选择的条目
                                    @Override
                                    public void onChoiceItem(int index, String item) {
                                        Toast.makeText(MainActivity.this, "最终选择了：" + item, Toast.LENGTH_LONG).show();
                                    }
                                }).setOnChoiceClickListener(new DialogInterface.OnClickListener() {
                            // 点击条目后回调的一个方法
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "点击了第" + (which + 1) + "个条目",
                                        Toast.LENGTH_LONG).show();
                            }
                        }).show();
                        break;
                    case 4:
                        DialogBuilder.listDialog(MainActivity.this).setChoiceItems(list)
                                .setChoiceType(ListDialogBuilder.TYPE_CHOICE_SINGLE)
                                .setOnChoiceListener(new ListDialogBuilder.OnChoiceListener() {
                                    // 对话框关闭后回调的一个方法，返回选择的条目
                                    @Override
                                    public void onChoiceItem(int index, Object item) {
                                        Toast.makeText(MainActivity.this, "最终选择了：" + item, Toast.LENGTH_LONG).show();
                                    }
                                }).setOnChoiceClickListener(new DialogInterface.OnClickListener() {
                            // 选择某一个条目的时候回调的一个方法，返回选择的是哪一个条目
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "点击了第" + (which + 1) + "个条目",
                                        Toast.LENGTH_LONG).show();
                            }
                        }).setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "点击了确定按钮", Toast.LENGTH_LONG).show();
                            }
                        }).show();
                        break;
                    case 5:
                        // 已经选好的条目
                        DialogBuilder.listDialog(MainActivity.this)
                                .setChoiceType(ListDialogBuilder.TYPE_CHOICE_MULTI).setChoiceItems(list)
                                .setCheckedItems(1, 3, 4)
                                .setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this, "点击了确定按钮", Toast.LENGTH_LONG).show();
                                    }
                                }).setOnMultiChoiceClickListener(new DialogInterface.OnMultiChoiceClickListener() {
                            // 选择或者取消选择某一个条目的时候回调的一个方法，返回某一个条目的选择情况
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                Toast.makeText(MainActivity.this,
                                        (isChecked ? "选择" : "取消选择") + "了第" + (which + 1) + "个条目", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }).setOnMultiChoiceListener(new ListDialogBuilder.OnMultiChoiceListener<String>() {
                            // 对话框关闭后回调的一个方法，返回选择的条目
                            @Override
                            public void onMultiChoiceItems(List<Integer> indexs, String[] items) {
                                Toast.makeText(MainActivity.this, "最终选择了：" + Arrays.toString(items),
                                        Toast.LENGTH_LONG).show();
                            }
                        }).show();
                        break;
                    case 6:
                        DialogBuilder.editTextDialog(MainActivity.this)
                                .setOnEditTextDialogListener(new EditTextDialogBuilder.OnEditTextDialogListener() {
                                    @Override
                                    public void onEditTextCreated(EditText editText) {
                                        editText.setHint("请输入文本内容");
                                    }

                                    @Override
                                    public boolean onEditTextSelected(EditText editText, String text) {
                                        Toast.makeText(MainActivity.this, editText.getText().toString(),
                                                Toast.LENGTH_LONG).show();
                                        return false;
                                    }
                                }).show();
                        break;
                    case 7:
                        View customView =
                                View.inflate(MainActivity.this, android.R.layout.simple_list_item_1, null);
                        TextView textView = (TextView) customView.findViewById(android.R.id.text1);
                        textView.setText("自定义视图");
                        DialogBuilder.otherDialog(MainActivity.this)
                                .setContentView(customView,
                                        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 800))
                                .show();
                        break;
                    case 8:
                        View sheetView =
                                View.inflate(MainActivity.this, android.R.layout.simple_list_item_1, null);
                        TextView sheetViewTextView = (TextView) sheetView.findViewById(android.R.id.text1);
                        sheetViewTextView.setText("自定义视图");
                        DialogBuilder.bottomSheetDialog(MainActivity.this)
                                .setContentView(sheetView,
                                        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 800))
                                .show();
                        break;
                    case 9:
                        final ProgressDialogBuilder.ProgressHandler progressHandler1 =
                                new ProgressDialogBuilder.ProgressHandler();
                        DialogBuilder.progressDialog(MainActivity.this).progressHandler(progressHandler1)
                                .show();
                        final Handler handler1 = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                int progress = msg.arg1;
                                progressHandler1.setProgress(progress);
                            }
                        };
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 1; i <= 100; i++) {
                                    Message message = new Message();
                                    message.arg1 = i;
                                    handler1.sendMessage(message);
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                        break;
                    case 10:
                        DialogBuilder.loadingDialog1(MainActivity.this).show();
                        break;
                    case 11:
                        DialogBuilder.loadingDialog2(MainActivity.this).show();
                        break;
                }
            }
        });
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onConfig(Config config) {
        super.onConfig(config);
        config.setSwipeBackWrapper(false);
    }
}
