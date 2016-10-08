package cn.yhq.dialog.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayUtils {

  public static boolean isLandscape(Context context) {
    Configuration mConfiguration = context.getResources().getConfiguration(); // 获取设置的配置信息
    int ori = mConfiguration.orientation; // 获取屏幕方向
    if (ori == Configuration.ORIENTATION_LANDSCAPE) {
      // 横屏
      return true;
    } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
      // 竖屏
      return false;
    }
    return false;
  }

  public static Point getDisplaySize(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics dm = new DisplayMetrics();
    wm.getDefaultDisplay().getMetrics(dm);
    int widthPixels = dm.widthPixels;
    int heightPixels = dm.heightPixels;
    Point p = new Point();
    p.x = widthPixels;
    p.y = heightPixels;
    return p;
  }

  /**
   * 将dp转换为像素
   * 
   * @param context
   * @param dp
   * @return
   */
  public static int dp2Px(Context context, float dp) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }

  /**
   * 将像素转换为dp
   * 
   * @param context
   * @param px
   * @return
   */
  public static int px2Dp(Context context, float px) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (px / scale + 0.5f);
  }

  /**
   * 将px值转换为sp值，保证文字大小不变
   * 
   * @param pxValue
   * @return
   */
  public static int px2sp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  /**
   * 将sp值转换为px值，保证文字大小不变
   * 
   * @param spValue
   * @return
   */
  public static int sp2px(Context context, float spValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (spValue * scale + 0.5f);
  }

}
