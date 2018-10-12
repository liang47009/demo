package com.yunfeng.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.yunfeng.Const;
import com.yunfeng.Res;

import java.lang.reflect.Method;


/**
 *
 */
public class FloatView extends LinearLayout implements View.OnClickListener {
    private boolean isAdd = false;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams wLayoutParams;
    private int screenWidth;
    private int screenHeight;
    private Context context;
    private int lastAction;
    private int viewWidth;
    private int viewHeight;
    private boolean isAnim = false;

    private float[] temp = new float[4];

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public FloatView(Context context) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(Res.getLayoutId("float_window_layout"), this);
        ImageButton imageView = ((ImageButton) findViewById(Res.getViewId("floatview_img")));
        this.mWindowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        initLayoutParams();
        refresh();
    }

    private void initLayoutParams() {
        this.wLayoutParams = new WindowManager.LayoutParams();
        this.wLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.wLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.wLayoutParams.format = PixelFormat.TRANSLUCENT;
        this.wLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        this.wLayoutParams.gravity = Gravity.START | Gravity.TOP;
        this.wLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;
    }

    private void refresh() {
        try {
            if (this.isAdd) {
                this.mWindowManager.updateViewLayout(this, this.wLayoutParams);
            } else {
                setVisibility(getVisibility());
                this.mWindowManager.addView(this, this.wLayoutParams);
                this.isAdd = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        if (this.isInit) {
//            return;
//        }
//        this.isInit = true;
        this.viewWidth = getWidth();
        this.viewHeight = getHeight();
        this.screenWidth = getRealWidth(this.context);
        this.screenHeight = getResources().getDisplayMetrics().heightPixels;
//        int x = this.configuration.getFloatX();
//        int y = this.configuration.getFloatY();
//        this.wLayoutParams.x = x;
//        this.wLayoutParams.y = y;
        refresh();
    }

    public void destroy() {
        try {
            this.mWindowManager.removeViewImmediate(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if (getVisibility() != VISIBLE) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                setFloatImageNormal();
//                this.handler.removeCallbacks(this.runnable);
                this.temp[0] = event.getX();
                this.temp[1] = event.getY();
                this.temp[2] = (event.getX() - getLeft());
                this.temp[3] = (event.getY() - getTop());
                this.lastAction = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.lastAction == 0) {
                    if ((Math.abs(event.getX() - this.temp[0]) > 10.0F) || (Math.abs(event.getY() - this.temp[1]) > 10.0F)) {
                        this.lastAction = 2;
                        refreshPos((int) event.getRawX(), (int) event.getRawY());
                    }
                } else {
                    refreshPos((int) event.getRawX(), (int) event.getRawY());
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                this.handler.postDelayed(this.runnable, 15000L);
                if (this.lastAction == 0) {
                    onClick(this);
                } else if ((this.wLayoutParams.x != 0) && (this.wLayoutParams.x != this.screenWidth - this.viewWidth)) {
                    this.isAnim = true;
                    refreshPosUp();
                    postInvalidate();
                }
        }
        return super.onTouchEvent(event);
    }

    private boolean isLeftSide() {
        return this.wLayoutParams.x < this.screenWidth / 2;
    }

    private void refreshPosUp() {
        if ((this.wLayoutParams.x == 0) || (this.wLayoutParams.x == this.screenWidth - this.viewWidth)) {
            this.isAnim = false;
//            this.configuration.setFloatPlace(this.wLayoutParams.x, this.wLayoutParams.y);
        } else {
            if (isLeftSide()) {
                this.wLayoutParams.x = 0;
            } else {
                this.wLayoutParams.x = (this.screenWidth - this.viewWidth);
            }
            refresh();
            invalidate();
        }
    }

    private void refreshPos(int x, int y) {
        this.wLayoutParams.x = (x - (int) this.temp[2]);
        if (y >= 0) {
            this.wLayoutParams.y = (y - (int) this.temp[3]);
        }
        if (this.wLayoutParams.y < 0) {
            this.wLayoutParams.y = 0;
        }
        if (this.wLayoutParams.y > this.screenHeight - this.viewHeight) {
            this.wLayoutParams.y = (this.screenHeight - this.viewHeight);
        }
        refresh();
        invalidate();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setVisibility(int visibility) {
//        if ((visibility != 0) && (isPopup())) {
//            this.mWindowManager.removeView(this.allLayout);
//            super.setVisibility(0);
//            this.isPopup = false;
//        }
        super.setVisibility(visibility);
    }

    public int getRealWidth(Context context) {
        int vh = 0;
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            Class c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }
}

//
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.DisplayMetrics;
//import android.util.TypedValue;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.WindowManager;
//import android.view.WindowManager.LayoutParams;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yunfeng.Res;
//
//import java.lang.reflect.Method;
//import java.util.List;
//import java.util.Map;
//
//public class FloatView extends LinearLayout implements View.OnClickListener {
//    private Handler handler;
//    private WindowManager mWindowManager;
//    private WindowManager.LayoutParams wLayoutParams;
//    private int screenWidth;
//    private int screenHeight;
//    private int viewWidth;
//    private int viewHeight;
//    private boolean isInit;
//    private boolean isAdd;
//    private boolean isAnim;
//    private float[] temp = new float[4];
//    private int lastAction;
//    private static final int minMove = 10;
//    private static final int delayMillis = 15000;
//    private boolean isPopup;
//    private LinearLayout allLayout;
//    private ImageView leftButton;
//    private ImageView rightButton;
//    private LinearLayout popLayout;
//    private LinearLayout popHideItem;
//    private ImageView imageView;
//    private Context context;
//
//    public FloatView(Context context) {
//        super(context);
//        this.context = context;
//        LayoutInflater.from(context).inflate(Res.getLayoutId("floatview"), this);
//        this.imageView = ((ImageView) findViewById(Res.getViewId("floatview_img")));
//        setFloatImageNormal();
//        this.mWindowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
//        initLayoutParams();
//        refresh();
//        this.handler = new Handler(Looper.getMainLooper());
//        this.handler.postDelayed(this.runnable, 15000L);
//    }
//
//    private void initLayoutParams() {
//        this.wLayoutParams = new WindowManager.LayoutParams();
//        this.wLayoutParams.width = -2;
//        this.wLayoutParams.height = -2;
//        this.wLayoutParams.format = -3;
//        this.wLayoutParams.type = 2;
//        this.wLayoutParams.gravity = 51;
//        this.wLayoutParams.flags = 66568;
//    }
//
//    private void setFloatImageNormal() {
//        this.imageView.setImageResource(getDrawableId("float_image"));
//    }
//
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        if (this.isInit) {
//            return;
//        }
//        this.isInit = true;
//        this.viewWidth = getWidth();
//        this.viewHeight = getHeight();
//
//
//        this.screenWidth = getRealWidth(this.context);
//        this.screenHeight = getResources().getDisplayMetrics().heightPixels;
//        int x = this.configuration.getFloatX();
//        int y = this.configuration.getFloatY();
//        this.wLayoutParams.x = x;
//        this.wLayoutParams.y = y;
//        refresh();
//    }
//
//    private void refresh() {
//        try {
//            if (this.isAdd) {
//                this.mWindowManager.updateViewLayout(this, this.wLayoutParams);
//            } else {
//                setVisibility(getVisibility());
//                this.mWindowManager.addView(this, this.wLayoutParams);
//                this.isAdd = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void destroy() {
//        this.handler.removeCallbacks(this.runnable);
//        this.configuration.setFloatPlace(this.wLayoutParams.x, this.wLayoutParams.y);
//        try {
//            this.mWindowManager.removeViewImmediate(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean onTouchEvent(MotionEvent event) {
//        if (getVisibility() != 0) {
//            return super.onTouchEvent(event);
//        }
//        switch (event.getAction()) {
//            case 0:
//                setFloatImageNormal();
//                this.handler.removeCallbacks(this.runnable);
//                this.temp[0] = event.getX();
//                this.temp[1] = event.getY();
//                this.temp[2] = (event.getX() - getLeft());
//                this.temp[3] = (event.getY() - getTop());
//                this.lastAction = 0;
//                break;
//            case 2:
//                if (this.lastAction == 0) {
//                    if ((Math.abs(event.getX() - this.temp[0]) > 10.0F) || (Math.abs(event.getY() - this.temp[1]) > 10.0F)) {
//                        this.lastAction = 2;
//                        refreshPos((int) event.getRawX(), (int) event.getRawY());
//                    }
//                } else {
//                    refreshPos((int) event.getRawX(), (int) event.getRawY());
//                }
//                break;
//            case 1:
//            case 3:
//                this.handler.postDelayed(this.runnable, 15000L);
//                if (this.lastAction == 0) {
//                    onClick(this);
//                } else if ((this.wLayoutParams.x != 0) && (this.wLayoutParams.x != this.screenWidth - this.viewWidth)) {
//                    this.isAnim = true;
//                    refreshPosUp();
//                    postInvalidate();
//                }
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
//
//    private void refreshPosUp() {
//        if ((this.wLayoutParams.x == 0) || (this.wLayoutParams.x == this.screenWidth - this.viewWidth)) {
//            this.isAnim = false;
//            this.configuration.setFloatPlace(this.wLayoutParams.x, this.wLayoutParams.y);
//        } else {
//            if (isLeftSide()) {
//                this.wLayoutParams.x = 0;
//            } else {
//                this.wLayoutParams.x = (this.screenWidth - this.viewWidth);
//            }
//            refresh();
//            invalidate();
//        }
//    }
//
//    private void refreshPos(int x, int y) {
//        this.wLayoutParams.x = (x - (int) this.temp[2]);
//        if (y >= 0) {
//            this.wLayoutParams.y = (y - (int) this.temp[3]);
//        }
//        if (this.wLayoutParams.y < 0) {
//            this.wLayoutParams.y = 0;
//        }
//        if (this.wLayoutParams.y > this.screenHeight - this.viewHeight) {
//            this.wLayoutParams.y = (this.screenHeight - this.viewHeight);
//        }
//        refresh();
//        invalidate();
//    }
//
//    public void setVisibility(int visibility) {
//        if ((visibility != 0) && (isPopup())) {
//            this.mWindowManager.removeView(this.allLayout);
//            super.setVisibility(0);
//            this.isPopup = false;
//        }
//        super.setVisibility(visibility);
//    }
//
//    private boolean isLeftSide() {
//        return this.wLayoutParams.x < this.screenWidth / 2;
//    }
//
//    public void onClick(View v) {
//        if (v.equals(this)) {
//            showPopup();
//        } else if (v.equals(this.leftButton)) {
//            if (isPopup()) {
//                super.setVisibility(0);
//                this.mWindowManager.removeView(this.allLayout);
//                this.isPopup = false;
//            }
//        } else if (v.equals(this.rightButton)) {
//            if (isPopup()) {
//                super.setVisibility(0);
//                this.mWindowManager.removeView(this.allLayout);
//                this.isPopup = false;
//            }
//        } else if (v.equals(this.popHideItem)) {
//            MyAlertDialog dialog = new MyAlertDialog(getContext());
//            dialog.setMessage(Res.getString("float_alert_hide"));
//            dialog.setPositiveButton(Res.getString("http_ok"), new FloatView .1 (this));
//
//
//            dialog.setNegativeButton(Res.getString("float_alert_cancel"), null);
//            dialog.show();
//        }
//    }
//
//    private Runnable runnable = new FloatView.2(this);
//
//    private boolean isPopup() {
//        return this.isPopup;
//    }
//
//    private void showPopup() {
//        if (this.floatData == null) {
//            return;
//        }
//        BillingActivity.refreshLocal(getContext());
//        this.allLayout = new LinearLayout(getContext());
//        this.allLayout.setOrientation(0);
//        if (isLeftSide()) {
//            addLeftButton();
//            addPopupLayout();
//            for (int i = 0; i < this.floatData.getData().size(); i++) {
//                int index = i;
//                addPopItem(index);
//                addViewSplit();
//            }
//            addPopHideItem();
//            addRightImage();
//        } else {
//            addLeftImage();
//            addPopupLayout();
//            addPopHideItem();
//            for (int i = 0; i < this.floatData.getData().size(); i++) {
//                addViewSplit();
//                int index = this.floatData.getData().size() - i - 1;
//                addPopItem(index);
//            }
//            addRightButton();
//        }
//        super.setVisibility(8);
//        this.mWindowManager.addView(this.allLayout, this.wLayoutParams);
//        this.isPopup = true;
//    }
//
//    private void addLeftButton() {
//        this.leftButton = new ImageView(getContext());
//        this.leftButton.setImageResource(getDrawableId("float_image_popup_left"));
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
//        params.gravity = 17;
//        this.allLayout.addView(this.leftButton, params);
//
//        this.leftButton.setOnClickListener(this);
//    }
//
//    private void addRightButton() {
//        this.rightButton = new ImageView(getContext());
//        this.rightButton.setImageResource(getDrawableId("float_image_popup_right"));
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
//        params.gravity = 17;
//        this.allLayout.addView(this.rightButton, params);
//
//        this.rightButton.setOnClickListener(this);
//    }
//
//    private void addLeftImage() {
//        ImageView leftImage = new ImageView(getContext());
//        leftImage.setImageResource(Res.getDrawableId("float_bg_l"));
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
//        params.gravity = 17;
//        this.allLayout.addView(leftImage, params);
//    }
//
//    private void addRightImage() {
//        ImageView rightImage = new ImageView(getContext());
//        rightImage.setImageResource(Res.getDrawableId("float_bg_r"));
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
//        params.gravity = 17;
//        this.allLayout.addView(rightImage, params);
//    }
//
//    private void addPopupLayout() {
//        this.popLayout = new LinearLayout(getContext());
//        this.popLayout.setOrientation(0);
//        this.popLayout.setBackgroundResource(Res.getDrawableId("float_bg"));
//        LinearLayout.LayoutParams popLayoutParams = new LinearLayout.LayoutParams(-2, -2);
//        popLayoutParams.gravity = 17;
//        this.allLayout.addView(this.popLayout, popLayoutParams);
//    }
//
//    private void addPopItem(int index) {
//        LinearLayout popItem = initPopItem(Res.getDrawableId((String) ((Map) this.floatData.getData().get(index)).get("icon")), getStringId((String) ((Map) this.floatData.getData().get(index)).get("click"), (String) ((Map) this.floatData.getData().get(index)).get("text")));
//        popItem.setOnClickListener(new FloatView .3 (this, index));
//    }
//
//    private void addPopHideItem() {
//        this.popHideItem = initPopItem(Res.getDrawableId("float_icon_hide"), Res.getStringId("float_text_hide"));
//        this.popHideItem.setOnClickListener(this);
//    }
//
//    private LinearLayout initPopItem(int imageResId, int textResId) {
//        int padding = dpToPx(10.0F);
//        LinearLayout itemLayout = new LinearLayout(getContext());
//        itemLayout.setOrientation(1);
//        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(-2, -2);
//        itemParams.gravity = 17;
//        itemParams.setMargins(padding, 0, padding, 0);
//        this.popLayout.addView(itemLayout, itemParams);
//
//        ImageView imageView = new ImageView(getContext());
//        imageView.setImageResource(imageResId);
//        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(-2, -2);
//        imageParams.gravity = 17;
//        itemLayout.addView(imageView, imageParams);
//
//        TextView textView = new TextView(getContext());
//        textView.setTextSize(10.0F);
//        textView.setTextColor(-6250336);
//        if (textResId == 0) {
//            textView.setText("undefine");
//        } else {
//            textView.setText(textResId);
//        }
//        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(-2, -2);
//        textParams.gravity = 17;
//        itemLayout.addView(textView, textParams);
//        return itemLayout;
//    }
//
//    private void addViewSplit() {
//        int padding = dpToPx(10.0F);
//        View splitView = new View(getContext());
//        splitView.setBackgroundColor(-6250336);
//        LinearLayout.LayoutParams splitParams = new LinearLayout.LayoutParams(1, -1);
//        splitParams.setMargins(0, padding, 0, padding);
//        this.popLayout.addView(splitView, splitParams);
//    }
//
//    private int dpToPx(float dp) {
//        return (int) TypedValue.applyDimension(1, dp, getContext().getResources().getDisplayMetrics());
//    }
//
//    private int getDrawableId(String name) {
//        String prefix = Res.getPrefix();
//        int gameId = Integer.valueOf(DataCache.getInstance().gameId).intValue();
//        if (gameId == 109) {
//            return ResUtil.getDrawableId(prefix + gameId + "_" + name);
//        }
//        return ResUtil.getDrawableId(prefix + name);
//    }
//
//    public int getStringId(String click, String name) {
//        int clickType = Integer.valueOf(click).intValue();
//        if ((clickType == 0) || (clickType == 1)) {
//            return Res.getStringId(name);
//        }
//        return ResUtil.getStringId("snailbilling_" + name);
//    }
//
//    public int getRealWidth(Context context) {
//        int vh = 0;
//        WindowManager windowManager = (WindowManager) context.getSystemService("window");
//        Display display = windowManager.getDefaultDisplay();
//        DisplayMetrics dm = new DisplayMetrics();
//        try {
//            Class c = Class.forName("android.view.Display");
//
//            Method method = c.getMethod("getRealMetrics", new Class[]{DisplayMetrics.class});
//            method.invoke(display, new Object[]{dm});
//            vh = dm.widthPixels;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return vh;
//    }
//}
