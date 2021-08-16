package com.kali_corporation.freeonlinetranslation.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;
////import com.guna.ocrlibrary.OCRCapture;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.theartofdev.edmodo.cropper.CropImageView.CropResult;
import com.theartofdev.edmodo.cropper.CropImageView.OnCropImageCompleteListener;
import com.theartofdev.edmodo.cropper.CropImageView.OnSetImageUriCompleteListener;
import com.kali_corporation.freeonlinetranslation.activity.util.MyUtils;
import com.kali_corporation.freeonlinetranslation.R;

import java.io.File;

public class MyCrop extends AppCompatActivity {

    private int RESULT_CAMERA = 11;
    ImageView btnback, btncrop, btnrotate;
    Context f61cn = this;
    CropImageView cropImageView;
    LayoutParams layoutParams;
    LinearLayout laytop;
    private File mFileTemp;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_crop);


        init();
        getWindow().setFlags(1024, 1024);
        if (getIntent().getBooleanExtra("camera", false)) {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                this.mFileTemp = new File(Environment.getExternalStorageDirectory(), "tmp.jpeg");
            } else {
                this.mFileTemp = new File(getFilesDir(), "tmp.jpeg");
            }
            StrictMode.setVmPolicy(new Builder().build());
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra("output", Uri.fromFile(this.mFileTemp));
            startActivityForResult(intent, this.RESULT_CAMERA);
        } else {
            Intent i = new Intent("android.intent.action.PICK");
            i.setType("image/*");
            startActivityForResult(i, 10);
        }
        this.cropImageView.setOnCropImageCompleteListener(new OnCropImageCompleteListener() {
            public void onCropImageComplete(CropImageView view, CropResult result) {

                TextRecognizer textRecognizer = new TextRecognizer.Builder(MyCrop.this).build();
                StringBuilder imageText = new StringBuilder();
                Frame imageFrame = new Frame.Builder()
                        .setBitmap(result.getBitmap())
                        .build();
                SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);
                for (int i = 0; i < textBlocks.size(); i++) {
                    TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
                    imageText.append(textBlock.getValue());
                }
                Log.e("AAA", "Text : " + imageText);

//                String text = OCRCapture.Builder(MyCrop.this).getTextFromBitmap(result.getBitmap());
//                Log.e("AAA", "Text : " + text);
                if (imageText.length() > 0) {
                    Intent i1 = new Intent(MyCrop.this.f61cn, TextTranslator.class);
                    i1.putExtra("text", imageText.toString());
                    MyCrop.this.startActivity(i1);
                    MyCrop.this.finish();
                    return;
                }
                MyUtils.Toast(MyCrop.this.f61cn, "No Text Detect...");
                MyCrop.this.finish();
            }
        });
        this.cropImageView.setOnSetImageUriCompleteListener(new OnSetImageUriCompleteListener() {
            public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
                if (error != null) {
                    Log.e("AAA", error.getLocalizedMessage());
                }
            }
        });
        this.btnrotate.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyCrop.this.cropImageView.rotateImage(90);
            }
        });
        this.btncrop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyCrop.this.btnrotate.setEnabled(false);
                MyCrop.this.cropImageView.getCroppedImageAsync();
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1 && data != null) {
            Uri uri = data.getData();
            Log.e("AAA", "Gal Uri : " + uri.toString());
            this.cropImageView.setImageUriAsync(uri);
        } else if (requestCode == this.RESULT_CAMERA && resultCode == -1) {
            this.cropImageView.setImageUriAsync(Uri.fromFile(this.mFileTemp));
        } else {
            MyUtils.Toast(this.f61cn, "Selection Cancel");
            finish();
        }
    }


    public void init() {
        this.btncrop = (ImageView) findViewById(R.id.btncrop);
        this.btnrotate = (ImageView) findViewById(R.id.btnrotate);
        this.cropImageView = (CropImageView) findViewById(R.id.setImage);
        this.btnback = (ImageView) findViewById(R.id.btnback);
        this.laytop = (LinearLayout) findViewById(R.id.laytop);
        resize();
        this.btnback.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyCrop.this.onBackPressed();
            }
        });
    }


    public void resize() {
//        this.layoutParams = MyUtils.getParamsL(this.f61cn, 450, 170);
//        this.btnrotate.setLayoutParams(this.layoutParams);
//        this.btncrop.setLayoutParams(this.layoutParams);
//        RelativeLayout.LayoutParams layoutParams2 = MyUtils.getParamsR(this.f61cn, 110, 110);
//        layoutParams2.addRule(15);
//        this.btnback.setLayoutParams(layoutParams2);
//        this.laytop.setLayoutParams(MyUtils.getParamsR(this.f61cn, 1080, 197));
    }
}
