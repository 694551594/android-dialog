# android-dialog

此框架提供七种对话框的显示，并支持对话框的扩展，目的是为了提供对话框的统一管理，并提供对话框显示的公共接口。

- LoadingDialog：正在加载对话框
- MessageDialog：消息对话框，只有确定按钮
- AlertDialog：确认对话框，有确认和取消按钮
- ListDialog：普通选择、单选或者多选对话框
- EditTextDialog：有一个editText和checkbox的对话框
- ProgressDialog：一个可以显示具体进度的对话框
- BottomSheetDialog：一个在底部弹出的对话框
- 支持扩展的ContentViewDialog

[![](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140525.png)](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140525.png)

#gradle配置方式
`compile 'cn.yhq:android-dialog:1.6.0'`
#使用方法
创建对话框的方式有两种：
#### 1、使用DialogBuilder直接创建，这种方式创建的对话框不会保存状态，即当屏幕旋转等导致的activity重建，对话框会消失。
#####显示一个正在加载的对话框
```java
DialogBuilder.loadingDialog(this).show();
```
[![](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140528.png)](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140528.png)
#####显示一个进度对话框
这个地方需要注意的是需要设置一个ProgressHandler，用于主动更新对话框的进度：
```java
DialogBuilder.ProgressHandler progressHandler =
                new DialogBuilder.ProgressHandler();
 DialogBuilder.progressDialog(MainActivity.this).progressHandler(progressHandler).show();
 // 更新进度
progressHandler.setProgress(progress);
```
[![](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_175735.png)](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_175735.png)
#####显示一个消息对话框
```java
DialogBuilder.messageDialog(MainActivity.this).setMessage("消息对话框").show();
```
[![](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140532.png)](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140532.png)
#####显示一个确认对话框
```java
DialogBuilder.alertDialog(MainActivity.this).setMessage(args.getString("message"))
            .setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "点击了确认按钮", Toast.LENGTH_LONG).show();
              }
            }).create();
```
[![](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140535.png)](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140535.png)
#####显示一个普通选择对话框
```java
  DialogBuilder.listDialog(MainActivity.this).setChoiceItems(list)
                .setChoiceType(DialogBuilder.TYPE_CHOICE_NORMAL)
                .setOnChoiceListener(new DialogBuilder.OnChoiceListener() {
                  // 对话框关闭后回调的一个方法，返回选择的条目
                  @Override
                  public void onChoiceItem(Object item) {
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
```
[![](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140538.png)](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140538.png)
#####显示一个单选对话框
```java
 DialogBuilder.listDialog(MainActivity.this).setChoiceItems(list)
                .setChoiceType(DialogBuilder.TYPE_CHOICE_SINGLE)
                .setOnChoiceListener(new DialogBuilder.OnChoiceListener() {
                  // 对话框关闭后回调的一个方法，返回选择的条目
                  @Override
                  public void onChoiceItem(Object item) {
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
```
[![](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140547.png)](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140547.png)
#####显示一个多选对话框
```java
 // 已经选好的条目
            int[] checkedItems = {1, 3, 4};
            DialogBuilder.listDialog(MainActivity.this)
                .setChoiceType(DialogBuilder.TYPE_CHOICE_MULTI).setChoiceItems(list)
                .setCheckedItems(checkedItems)
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
                }).setOnMultiChoiceListener(new DialogBuilder.OnMultiChoiceListener() {
                  // 对话框关闭后回调的一个方法，返回选择的条目
                  @Override
                  public void onMultiChoiceItems(Object[] items) {
                    Toast.makeText(MainActivity.this, "最终选择了：" + Arrays.toString(items),
                        Toast.LENGTH_LONG).show();
                  }
                }).show();
```
[![](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140550.png)](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140550.png)
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
[![](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140553.png)](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140553.png)
#####显示一个BottomSheet对话框
```java
  View sheetView = View.inflate(MainActivity.this, android.R.layout.simple_list_item_1, null);
  TextView sheetViewTextView = (TextView) sheetView.findViewById(android.R.id.text1);
  sheetViewTextView.setText("自定义视图");
  DialogBuilder.bottomSheetDialog(MainActivity.this).setContentView(sheetView).show();
```
[![](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161021_113255.png)](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161021_113255.png)
#####显示一个自定义对话框
```java
View customView =
                View.inflate(MainActivity.this, android.R.layout.simple_list_item_1, null);
            TextView textView = (TextView) customView.findViewById(android.R.id.text1);
            textView.setText("自定义视图");
            DialogBuilder.otherDialog(MainActivity.this).setContentView(customView).show();
```
[![](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140556.png)](https://raw.githubusercontent.com/694551594/android-dialog/master/screenshots/截屏_20161010_140556.png)
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




