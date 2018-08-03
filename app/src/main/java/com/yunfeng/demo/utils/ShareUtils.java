package com.yunfeng.demo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.FileNameMap;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * share
 * Created by xll on 2018/8/2.
 */
public class ShareUtils {
    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param filePath
     */
    public static String getMimeType(Context context, String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "text/plain";
        if (filePath != null) {
            try {
                Uri uri = Uri.fromFile(new File(filePath));
                FileNameMap fileNameMap = URLConnection.getFileNameMap();
                mime = fileNameMap.getContentTypeFor(uri.toString());
//                File file = new File(filePath);
//                if (file.exists()) {
//                    Path path = Paths.get(filePath);
//                    mime = Files.probeContentType(path);
//                }
//                URI uri = URI.create(filePath);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                    mmr.setDataSource(uri.getPath(), new HashMap<String, String>());
//                } else {
//                    mmr.setDataSource(uri.getPath());
//                }
//                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mime;
    }

    /**
     * 发送文件
     *
     * @param context
     * @param path
     */
    public static void sendFileByOtherApp(Context context, String path) {
        File file = new File(path);
        if (file.exists()) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            final String mimeType = getMimeType(context, path);
            shareIntent.setType(mimeType);
            List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(shareIntent, 0);
            if (!resInfo.isEmpty()) {
                ArrayList<Intent> targetIntents = new ArrayList<>();
                for (ResolveInfo info : resInfo) {
                    ActivityInfo activityInfo = info.activityInfo;
//                    if (activityInfo.packageName.contains("com.tencent.mobileqq") || activityInfo.packageName.contains("com.tencent.mm")) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setPackage(activityInfo.packageName);
                    intent.setType(mimeType);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    intent.setClassName(activityInfo.packageName, activityInfo.name);
                    targetIntents.add(intent);
//                    }
                }
                Intent chooser = Intent.createChooser(targetIntents.remove(0), "Send mail...");
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetIntents.toArray(new Parcelable[]{}));
                context.startActivity(chooser);
            }
        }
    }
}
