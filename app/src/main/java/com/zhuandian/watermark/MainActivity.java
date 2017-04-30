package com.zhuandian.watermark;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int TAKE_PHOTO = 1;
    private Button takePhoto;
    private ImageView mImageView;
    private Button addMark;
    private Button del_mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        takePhoto = (Button) findViewById(R.id.take_photo);
        mImageView = (ImageView) findViewById(R.id.img);
        addMark = (Button) findViewById(R.id.add_mark);
        del_mark = (Button) findViewById(R.id.del_mark);

        takePhoto.setOnClickListener(this);
        addMark.setOnClickListener(this);
        del_mark.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();

            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                System.out.println("sd card is not avaiable");
                return;
            }

            String name = "photo.jpg";

            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
            file.mkdirs(); //创建文件夹

            String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + name;

            FileOutputStream b = null;

            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            mImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_photo:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_PHOTO);
                break;

            case R.id.add_mark:
                addWatermarkToPhoto(mImageView);
                break;

            case R.id.del_mark:
                //delWatermark();
        }
    }

    private void addWatermarkToPhoto(ImageView mImageView) {
        Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
        Bitmap watermarkBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.abc_dialog_material_background_dark)).getBitmap();
        ImageWatermarkUtils.Watermark(bitmap, watermarkBitmap, 0);
    }
}
