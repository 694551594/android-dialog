package cn.yhq.dialog.sample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.yhq.dialog.DialogBuilder;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ListView listView = (ListView) this.findViewById(R.id.listview);
    ArrayAdapter adapter =
        new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1,
            new String[] {"正在载入对话框", "消息对话框", "确认对话框", "单选对话框", "文本输入对话框", "自定义对话框"});

    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
          case 0:
            DialogBuilder.builder(MainActivity.this).create().showLoadingDialog();
            break;
          case 1:
            DialogBuilder.builder(MainActivity.this).setMessage("消息对话框").create()
                .showMessageDialog();
            break;
          case 2:
            DialogBuilder.builder(MainActivity.this).setMessage("确认对话框")
                .setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "点击了确认按钮", Toast.LENGTH_LONG).show();
                  }
                }).create().showAlertDialog();
            break;
          case 3:
            final String[] items = {"选择项1", "选择项2"};
            DialogBuilder.builder(MainActivity.this).setChoiceItems(items)
                .setOnChoiceClickListener(new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "选择了：" + items[which], Toast.LENGTH_LONG)
                        .show();
                  }
                }).create().showListDialog();
            break;
          case 4:
            DialogBuilder.builder(MainActivity.this)
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
                }).create().showEditTextDialog();
            break;
          case 5:
            View customView =
                View.inflate(MainActivity.this, android.R.layout.simple_list_item_1, null);
            TextView textView = (TextView) customView.findViewById(android.R.id.text1);
            textView.setText("自定义视图");
            DialogBuilder.builder(MainActivity.this).setContentView(customView).create()
                .showDialog();
            break;
        }
      }
    });
  }
}
