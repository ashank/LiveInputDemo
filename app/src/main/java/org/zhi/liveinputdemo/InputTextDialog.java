package org.zhi.liveinputdemo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;


/**
 * 文本输入框
 */
public class InputTextDialog extends Dialog {

    public interface OnTextSendListener {
        void onTextSend(String msg, boolean tanmuOpen);
    }

    private TextView confirmBtn;
    private LinearLayout mBarrageArea;
    private EditText messageTextView;
    private static final String TAG = InputTextDialog.class.getSimpleName();
    private Context mContext;
    private InputMethodManager imm;
    private RelativeLayout rlDlg;
    private int mLastDiff = 0;
    private LinearLayout mConfirmArea;
    private OnTextSendListener mOnTextSendListener;
    private boolean mDanmuOpen = false;

    public InputTextDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        setContentView(org.zhi.liveinputdemo.R.layout.dialog_input_text);

        messageTextView = (EditText) findViewById(org.zhi.liveinputdemo.R.id.et_input_message);
        messageTextView.setInputType(InputType.TYPE_CLASS_TEXT);
        //修改下划线颜色
        messageTextView.getBackground().setColorFilter(context.getResources().getColor(org.zhi.liveinputdemo.R.color.transparent), PorterDuff.Mode.CLEAR);

        try {
            //修改光光标颜色
            Field ft = TextView.class.getDeclaredField("mCursorDrawableRes");
            ft.setAccessible(true);
            ft.set(messageTextView, org.zhi.liveinputdemo.R.drawable.edit_select);
        } catch (Exception e) {
            e.printStackTrace();
        }

        confirmBtn = (TextView) findViewById(org.zhi.liveinputdemo.R.id.confrim_btn);
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = messageTextView.getText().toString().trim();
                if (!TextUtils.isEmpty(msg)) {

                    mOnTextSendListener.onTextSend(msg, mDanmuOpen);
                    imm.showSoftInput(messageTextView, InputMethodManager.SHOW_FORCED);
                    imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                    messageTextView.setText("");
                    dismiss();
                } else {
                    Toast.makeText(mContext, "input can not be empty!", Toast.LENGTH_LONG).show();
                }
                messageTextView.setText(null);
            }
        });




        final Button barrageBtn = (Button) findViewById(org.zhi.liveinputdemo.R.id.barrage_btn);
        barrageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDanmuOpen = !mDanmuOpen;
                if (mDanmuOpen) {
                    barrageBtn.setBackgroundResource(org.zhi.liveinputdemo.R.drawable.barrage_slider_on);
                } else {
                    barrageBtn.setBackgroundResource(org.zhi.liveinputdemo.R.drawable.barrage_slider_off);
                }
            }
        });

        mBarrageArea = (LinearLayout) findViewById(org.zhi.liveinputdemo.R.id.barrage_area);
        mBarrageArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDanmuOpen = !mDanmuOpen;
                if (mDanmuOpen) {
                    barrageBtn.setBackgroundResource(org.zhi.liveinputdemo.R.drawable.barrage_slider_on);
                } else {
                    barrageBtn.setBackgroundResource(org.zhi.liveinputdemo.R.drawable.barrage_slider_off);
                }
            }
        });

        //监听键盘
        messageTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case KeyEvent.KEYCODE_ENDCALL:
                    case KeyEvent.KEYCODE_ENTER:
                        if (messageTextView.getText().length() > 0) {
//                            mOnTextSendListener.onTextSend("" + messageTextView.getText(), mDanmuOpen);
                            //sendText("" + messageTextView.getText());
                            //imm.showSoftInput(messageTextView, InputMethodManager.SHOW_FORCED);
                            imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
//                            messageTextView.setText("");
                            dismiss();
                        } else {
                            Toast.makeText(mContext, "input can not be empty!", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    case KeyEvent.KEYCODE_BACK:
                        dismiss();
                        return false;
                    default:
                        return false;
                }
            }
        });

        mConfirmArea = (LinearLayout) findViewById(org.zhi.liveinputdemo.R.id.confirm_area);
        mConfirmArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = messageTextView.getText().toString().trim();
                if (!TextUtils.isEmpty(msg)) {

                    mOnTextSendListener.onTextSend(msg, mDanmuOpen);
                    imm.showSoftInput(messageTextView, InputMethodManager.SHOW_FORCED);
                    imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                    messageTextView.setText("");
                    dismiss();
                } else {
                    Toast.makeText(mContext, "input can not be empty!", Toast.LENGTH_LONG).show();
                }
                messageTextView.setText(null);
            }
        });

        messageTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Log.d("My test", "onKey " + keyEvent.getCharacters());
                return false;
            }
        });

        rlDlg = (RelativeLayout) findViewById(org.zhi.liveinputdemo.R.id.rl_outside_view);
        rlDlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击编辑区域以上的位置，dialog消失
                if (v.getId() != org.zhi.liveinputdemo.R.id.rl_inputdlg_view)
                    dismiss();
            }
        });


        final LinearLayout rldlgview = (LinearLayout) findViewById(org.zhi.liveinputdemo.R.id.rl_inputdlg_view);

        //dialog消失键盘会消失，但是键盘消失，dialog不一定消失，比如按软键盘上的消失键，键盘消失，dialog不会消失
        // 所以在这里监听键盘的高度，如果高度为0则表示键盘消失，那么就应该dimiss dialog
        rldlgview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                Log.d("My test", "onKey >>>>>>>>>>>>>>>>>" );
                Rect r = new Rect();
                //获取当前界面可视部分
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;

                if (heightDifference <= 0 && mLastDiff > 0) {
                    //imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                    Log.d("My test", "onKey >>>>>>>>>>>>>>>>>" );
                    dismiss();
                }
                mLastDiff = heightDifference;
            }
        });
    }


    public void setmOnTextSendListener(OnTextSendListener onTextSendListener) {
        this.mOnTextSendListener = onTextSendListener;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //dismiss之前重置mLastDiff值避免下次无法打开
        mLastDiff = 0;
    }

    @Override
    public void show() {
        super.show();
    }
}
