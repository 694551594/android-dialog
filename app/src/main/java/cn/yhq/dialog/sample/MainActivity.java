package cn.yhq.dialog.sample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.yhq.adapter.core.ViewHolder;
import cn.yhq.adapter.list.SimpleListAdapter;
import cn.yhq.dialog.BaseActivity;
import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.IDialog;

/**
 * 创建对话框的方式有两种：
 *
 * 1、直接创建，这种方式创建的对话框不会保存状态，即当屏幕旋转等导致的activity重建，对话框会消失。
 * 2、使用DialogManager，配合IDialogCreator接口创建的对话框，这种方式创建的对话框会保存状态，在屏幕旋转后对话框不会消失。
 * 3、当使用第二种方式的时候，你可以在你的Activity基类里面实现IDialogCreator接口，并提供DialogManager的封装方法，
 *    这样在你的Activity子类里面重写IDialogCreator接口方法后，直接调用显示对话框的方法showDialogFragment就可以直接显示对话框了。
 *    具体可以参考BaseActivity与BaseFragment
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
        return DialogBuilder.alertDialog(this).setMessage(args.getString("message"))
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
    setContentView(R.layout.activity_main);

    ListView listView = (ListView) this.findViewById(R.id.listview);
    SimpleListAdapter<String> adapter = SimpleListAdapter.create(this,
        new String[] {"正在载入对话框", "消息对话框", "确认对话框", "单选对话框", "文本输入对话框", "自定义对话框"},
        android.R.layout.simple_list_item_1, new SimpleListAdapter.IItemViewSetup<String>() {
          @Override
          public void setupView(ViewHolder viewHolder, int position, String entity) {
            viewHolder.bindTextData(android.R.id.text1, entity);
          }
        });

    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
          case 0:
            DialogBuilder.loadingDialog(MainActivity.this).show();
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
            final String[] items = {"选择项1", "选择项2"};
            DialogBuilder.listDialog(MainActivity.this).setChoiceItems(items)
                .setOnChoiceClickListener(new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "选择了：" + items[which], Toast.LENGTH_LONG)
                        .show();
                  }
                }).show();
            break;
          case 4:
            DialogBuilder.editTextDialog(MainActivity.this)
                .setOnEditTextDialogListener(new DialogBuilder.OnEditTextDialogListener() {
                  @Override
                  public void onEditTextCreated(EditText editText, CheckBox checkbox) {
                    editText.setHint("请输入文本内容");
                    checkbox.setVisibility(View.VISIBLE);
                    checkbox.setText("checkbox文本");
                  }

                  @Override
                  public boolean onEditTextSelected(EditText editText, String text,
                      CheckBox checkbox, boolean checked) {
                    Toast.makeText(MainActivity.this, editText.getText().toString(),
                        Toast.LENGTH_LONG).show();
                    return false;
                  }
                }).show();
            break;
          case 5:
            View customView =
                View.inflate(MainActivity.this, android.R.layout.simple_list_item_1, null);
            TextView textView = (TextView) customView.findViewById(android.R.id.text1);
            textView.setText("自定义视图");
            DialogBuilder.otherDialog(MainActivity.this).setContentView(customView).show();
            break;
        }
      }
    });
  }



}
