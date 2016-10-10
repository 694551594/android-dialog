# android-dialog

此框架提供五种对话框的显示，并支持对话框的扩展，目的是为了提供对话框的统一管理，并提供对话框显示的公共接口。

- LoadingDialog：正在加载对话框
- MessageDialog：消息对话框，只有确定按钮
- AlertDialog：确认对话框，有确认和取消按钮
- ListDialog：单选或者多选对话框
- EditTextDialog：有一个editText和checkbox的对话框
- 支持扩展的ContentViewDialog

#gradle配置方式
`compile 'cn.yhq:android-dialog:1.0'`
#使用方法
创建对话框的方式有两种：
#### 1、使用DialogBuilder直接创建，这种方式创建的对话框不会保存状态，即当屏幕旋转等导致的activity重建，对话框会消失。
#####显示一个正在加载的对话框
```java
DialogBuilder.loadingDialog(this).show();
```
#####显示一个消息对话框
```java
DialogBuilder.messageDialog(MainActivity.this).setMessage("消息对话框").show();
```
#####显示一个确认对话框
```java
DialogBuilder.alertDialog(this).setMessage(args.getString("message"))
            .setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "点击了确认按钮", Toast.LENGTH_LONG).show();
              }
            }).create();
```
#####显示一个选择对话框
```java
final String[] items = {"选择项1", "选择项2"};
            DialogBuilder.listDialog(MainActivity.this).setChoiceItems(items)
                .setOnChoiceClickListener(new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "选择了：" + items[which], Toast.LENGTH_LONG)
                        .show();
                  }
                }).show();
```
#####显示一个文本输入对话框
```java
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
```
#####显示一个自定义对话框
```java
View customView =
                View.inflate(MainActivity.this, android.R.layout.simple_list_item_1, null);
            TextView textView = (TextView) customView.findViewById(android.R.id.text1);
            textView.setText("自定义视图");
            DialogBuilder.otherDialog(MainActivity.this).setContentView(customView).show();
```
#### 2、使用DialogManager，配合IDialogCreator接口创建的对话框，这种方式创建的对话框会保存状态，在屏幕旋转后对话框不会消失。

```java
DialogManager dialogManager = new DialogManager(this);
Bundle args = new Bundle();
args.putString("message", "确认对话框");
dialogManager.showDialog(1, args);
```
```java
  @Override
  public IDialog createDialog(int id, Bundle args) {
    switch (id) {
      case 1:
	    // 这里返回的就是按照普通方式创建的对话框
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
```
#### 3、当使用第二种方式的时候，你可以在你的Activity基类里面实现IDialogCreator接口，并提供DialogManager的封装方法，这样在你的Activity子类里面重写IDialogCreator接口方法后，直接调用显示对话框的方法showDialogFragment就可以直接显示对话框了。具体可以参考BaseActivity与BaseFragment。

```java
public class BaseActivity extends AppCompatActivity implements IDialogCreator {
  private DialogManager dialogManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    dialogManager = new DialogManager(this);
  }

  public void showDialogFragment(int id) {
    dialogManager.showDialog(id);
  }

  public void showDialogFragment(int id, Bundle bundle) {
    dialogManager.showDialog(id, bundle);
  }

  @Override
  public IDialog createDialog(int id, Bundle args) {
    return null;
  }
}

```




