package cn.yhq.dialog.utils;

import android.text.Selection;
import android.text.Spannable;
import android.widget.EditText;

public class EditTextUtils {
  public static void onTextToEnd(EditText editText) {
    CharSequence text = editText.getText();
    if (text instanceof Spannable) {
      Spannable spanText = (Spannable) text;
      Selection.setSelection(spanText, text.length());
    }
  }
}
