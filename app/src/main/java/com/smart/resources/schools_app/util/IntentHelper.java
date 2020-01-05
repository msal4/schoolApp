package com.smart.resources.schools_app.util;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IntentHelper {
    public static int GET_IMAGE_REQUEST = 0;
    public static int GET_MULTI_IMAGES_REQUEST = 1;

    public static void selectImage(Activity currentActivity, boolean neededForLaterUsage){
        startGallery(currentActivity, false, neededForLaterUsage);
    }

    public static void selectMultipleImages(Activity currentActivity, boolean neededForLaterUsage){
        startGallery(currentActivity, true, neededForLaterUsage);
    }

    private static void startGallery(Activity activity, Boolean multiSelect , boolean neededForLaterUsage){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multiSelect);
        intent.setAction(neededForLaterUsage? Intent.ACTION_OPEN_DOCUMENT: Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent,"Select Picture"), multiSelect? GET_MULTI_IMAGES_REQUEST: GET_IMAGE_REQUEST);
    }

    public static Uri getImage(Intent data){
        return getImages(data).get(0);
    }

    // NOTE: there is no reliable way to convert Uri into real Path
    public static List<Uri> getImages(Intent data){
        List<Uri> images= new ArrayList<>();
        if(data.getClipData() != null) {
            //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
            int count = data.getClipData().getItemCount();
            for(int i = 0; i < count; i++) {
                Uri imageUri = data.getClipData().getItemAt(i).getUri();
                images.add(imageUri);
            }
        } else if(data.getData() != null) {
            Uri imageUri = data.getData();
            images.add(imageUri);
        }

        return images;
    }



}
