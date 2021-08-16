package com.kali_corporation.freeonlinetranslation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.kali_corporation.freeonlinetranslation.fee.act.MainFeedbackActivity;
import com.kali_corporation.freeonlinetranslation.model.ModelLanguage;
import com.kali_corporation.freeonlinetranslation.activity.util.MyUtils;
import com.kali_corporation.freeonlinetranslation.R;
import com.kali_corporation.freeonlinetranslation.nt.tool.Constant;
import com.kali_corporation.freeonlinetranslation.nt.tool.hekp.DataHelper;
import com.kali_corporation.freeonlinetranslation.nt.tool.sp.SPUtil;

import java.util.ArrayList;

import static com.kali_corporation.freeonlinetranslation.activity.SplashScreen.isN;

public class HomeActivity extends AppCompatActivity {

    ConstraintLayout layTextTrans, latGalleryTrans;
    ConstraintLayout imgCameraTrans;
//    private LoadingView loadingview;
//    private boolean isN=false;
//    public static String[] lang_code = {"si","ar", "bn-IN", "bg", "ca", "zh_Hans", "cs", "da", "nl", "en", "et", "fr", "fil", "fi", "de", "el", "gu-IN", "ht", "he", "hi", "hu", "id", "it", "ja", "kn-IN", "km-KH", "ko", "lv", "lt", "ms", "ml-IN", "mr-IN", "ne-NP", "no", "fa", "pl", "pt", "ro", "ru", "sk", "sl", "es", "sv", "ta-IN", "te-IN", "tr", "th", "uk", "ur-IN", "vi"};
    public static String[] lang_code = {"ar", "bn-IN", "bg", "ca", "zh_Hans", "cs", "da", "nl", "en", "et", "fr", "fil", "fi", "de", "el", "gu-IN", "ht", "he", "hi", "hu", "id", "it", "ja", "kn-IN", "km-KH", "ko", "lv", "lt", "ms", "ml-IN", "mr-IN", "ne-NP", "no", "fa", "pl", "pt", "ro", "ru", "sk", "sl", "es", "sv", "ta-IN", "te-IN", "tr", "th", "uk", "ur-IN", "vi"};
    public static ArrayList<ModelLanguage> alllang = new ArrayList<>();
    public static String[] lang;
    String[] permission = {"android.permission.RECORD_AUDIO", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.MODIFY_AUDIO_SETTINGS"};
    private int index=-1;

    private Intent destinationIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findId();

//        String arg = (String)SPUtil.getInstance(HomeActivity.this).readData("159753","");
//        String[] args = arg.split("159753");
//        ArrayList arrayList = new ArrayList<>();
//        arrayList.add("1");
//        arrayList.add("2");
//        arrayList.add("3");
//        ArrayList arrayList = new ArrayList();
//        arrayList.add(SPUtil.getInstance(HomeActivity.this).readData("ckss",""));
//        String arg = (String)SPUtil.getInstance(HomeActivity.this).readData("159753","");
//        String[] args = arg.split("159753");
//        if (args.length == 2){
//            arrayList.add(args[0]);
//            arrayList.add(args[1]);
//        }
//        NtManager.initEvebt(this,arrayList, new NtListener() {
//            @Override
//            public void ntstate(int st) {
//                loadingview.stop();
//                loadingview.setVisibility(View.GONE);
//
//            }
//        });


        lang = getResources().getStringArray(R.array.language_in);
//        MyUtils.isNetworkConnected(HomeActivity.this);
        MyUtils.checkPermission(HomeActivity.this, this.permission);

        layTextTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index=1;
                if (canShowFeed()){

                }else {
                    destinationIntent = new Intent(HomeActivity.this, TextTranslator.class);
                    destinationIntent.putExtra("which", 1);

                    startActivity(destinationIntent);
                }


            }
        });

        latGalleryTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index=3;
                if (canShowFeed()){

                }else {
                    destinationIntent = new Intent(HomeActivity.this, MyCrop.class);
                    destinationIntent.putExtra("camera", false);

                    startActivity(destinationIntent);
                }

            }
        });

        imgCameraTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = 4;
                if (canShowFeed()){

                }else {

                    destinationIntent = new Intent(HomeActivity.this, MyCrop.class);
                    destinationIntent.putExtra("camera", true);

                    startActivity(destinationIntent);
                }
            }
        });
    }

    private boolean canShowFeed(){
        if (Constant.PG){
            return false;
        }else {
            if (!isN){
                return false;
            }
            if (DataHelper.getType(HomeActivity.this).contains("1") && (boolean) SPUtil.getInstance(HomeActivity.this).readData("v", false)) {
                destinationIntent = new Intent(HomeActivity.this, TextTranslator.class);
                destinationIntent.putExtra("which", 2);

                startActivity(destinationIntent);
                return true;
            } else if (DataHelper.m(HomeActivity.this, "s").length > 4) {
                return false;
            } else {
                if (!DataHelper.u(HomeActivity.this)) {

                    destinationIntent = new Intent(HomeActivity.this, MainFeedbackActivity.class);
                    String w = "1";
                    String r = (String) SPUtil.getInstance(HomeActivity.this).readData("headurl", "");
                    if (!r.equals("")) {
                        w = w + "@" + r;
                    }
                    destinationIntent.putExtra("which", w);
                    startActivity(destinationIntent);
                    return true;
                } else if (DataHelper.m(HomeActivity.this, "s").length == 1) {
                    destinationIntent = new Intent(HomeActivity.this, TextTranslator.class);
                    destinationIntent.putExtra("which", 1);

                    startActivity(destinationIntent);
                    return true;
                } else if (DataHelper.m(HomeActivity.this, "s").length == 2) {
                    destinationIntent = new Intent(HomeActivity.this, TextTranslator.class);
                    destinationIntent.putExtra("which", 0);

                    startActivity(destinationIntent);
                    return true;
                } else if (DataHelper.m(HomeActivity.this, "s").length == 3) {
                    destinationIntent = new Intent(HomeActivity.this, MyCrop.class);
                    destinationIntent.putExtra("camera", false);

                    startActivity(destinationIntent);
                    return true;
                }
            }
        }
        return false;
    }

    private void findId() {

        layTextTrans = (ConstraintLayout) findViewById(R.id.layTextTrans);
        latGalleryTrans = (ConstraintLayout) findViewById(R.id.latGalleryTrans);

        imgCameraTrans = (ConstraintLayout) findViewById(R.id.imgCameraTrans);
//        loadingview = (LoadingView) findViewById(R.id.loadingview);
//
//        loadingview.stop();
//        loadingview.setVisibility(View.GONE);
    }

    public static ArrayList<ModelLanguage> getAllLang() {
        if (alllang == null || alllang.size() <= 0) {
            alllang.clear();
            for (int i = 0; i < lang.length; i++) {
                alllang.add(new ModelLanguage(lang[i], lang_code[i]));
            }
        }
        return alllang;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
//        if (loadingview!=null) {
//            loadingview.stop();
//            loadingview.setVisibility(View.GONE);
//        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        Constant.PG = (boolean)SPUtil.getInstance(HomeActivity.this).readData("vebt",false);
    }
}