package org.zhi.liveinputdemo;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements InputTextDialog.OnTextSendListener {

    InputTextDialog mInputTextMsgDialog;
    ImageView showMsg,showMsg111;
    LinearLayout ll;
    LinearLayout rl;
    private View decorView;

    private int  height;
    private boolean isSetup,isCancel;

    SoftKeyboardHeight softKeyboardHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(org.zhi.liveinputdemo.R.layout.activity_liveroom);
        initUI();

        decorView=getWindow().getDecorView();
        softKeyboardHeight=new SoftKeyboardHeight(this);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height=softKeyboardHeight.getSoftKeyHeight(getWindow().getDecorView());
                if (height>0){
                    softKeyboardHeight.saveHeight();
                }
                Log.e("TAG","键盘高度==="+height+">>>>>");
                if (height>0&&!isSetup){
                    Log.e("TAG","isSetup==="+isSetup+">>>>>");
                    //高度大于0，说明键盘已经弹起了 需要设置距离底部的高度
                    ll.setVisibility(View.GONE);
                    LinearLayout.LayoutParams la=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    la.bottomMargin=height;
                    rl.setLayoutParams(la);
                    isSetup=true;
                }

            }
        });
    }

    private void initUI() {
        mInputTextMsgDialog = new InputTextDialog(this, org.zhi.liveinputdemo.R.style.InputDialog);
        mInputTextMsgDialog.setmOnTextSendListener(this);
        showMsg = (ImageView) findViewById(org.zhi.liveinputdemo.R.id.btn_message_input);
        showMsg111 = (ImageView) findViewById(org.zhi.liveinputdemo.R.id.btn_message_input111);
        rl = (LinearLayout) findViewById(org.zhi.liveinputdemo.R.id.rl);
        ll = (LinearLayout) findViewById(org.zhi.liveinputdemo.R.id.ll);
        showMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                height=softKeyboardHeight.getHeight();
                //点击输入文字按钮，弹出dialog同时设置高度
                showInputMsgDialog();
                if (height>0){
                    ll.setVisibility(View.GONE);
                    LinearLayout.LayoutParams la=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    la.bottomMargin=height;
                    rl.setLayoutParams(la);
                }

            }
        });
    }

    private void showInputMsgDialog() {
        WindowManager windowManager = getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = mInputTextMsgDialog.getWindow().getAttributes();
        lp.width = (dm.widthPixels); //设置宽度
        mInputTextMsgDialog.getWindow().setAttributes(lp);
        mInputTextMsgDialog.setCancelable(true);
        mInputTextMsgDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mInputTextMsgDialog.show();

        mInputTextMsgDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //当dialog消失，还原原来的状态
                Log.e("TAG","onDismiss==="+height+">>>>>");
                ll.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams la=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                la.bottomMargin=0;
                rl.setLayoutParams(la);

            }
        });
    }

    @Override
    public void onTextSend(String msg, boolean tanmuOpen) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onStop() {
        Log.e("onStop",">>>>>");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("onDestroy",">>>>>");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.e("onPause",">>>>>");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.e("onResume",">>>>>");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.e("onRestart",">>>>>");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.e("onStart",">>>>>");
        super.onStart();
    }




}