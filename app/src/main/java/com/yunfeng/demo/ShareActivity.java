package com.yunfeng.demo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * activity
 * Created by xll on 2018/8/2.
 */
public class ShareActivity extends Activity {

    ImageView imageView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        imageView = this.findViewById(R.id.share_imgview);
        Intent intent = this.getIntent();
        if (intent != null) {
            String action = intent.getAction();
            String type = intent.getType();
            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if ("text/".equals(type)) {
                    handleSendText(intent); // 处理发送来的文字
                } else if (type.startsWith("image/")) {
                    handleSendImage(intent); // 处理发送来的图片
                }
            } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
                if (type.startsWith("image/")) {
                    handleSendMultipleImages(intent); // 处理发送来的多张图片
                }
            } else {
                // 处理其他intents，比如由主屏启动
            }
        }
    }

    private void handleSendMultipleImages(Intent intent) {
        Log.d("app", "handleSendMultipleImages");
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
//            type.setText("type:multiple");
            if (imageUris.size() == 2) {
//                image.setImageURI(imageUris.get(0));
//                imageMultiple.setImageURI(imageUris.get(1));
            }
        }
    }

    private void handleSendImage(Intent intent) {
        Log.d("app", "handleSendImage");
//        String className = intent.getComponent().getClassName();
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
//            type.setText("type:image");
            imageView.setImageURI(imageUri);
        }
    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
//            type.setText("type:text");
//            text.setText(sharedText);
        }
        Log.d("app", "handleSendText");
    }
}
