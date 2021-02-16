package com.example.accountbook.utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.accountbook.R;

public class KeyBoardUtils {
    private final Keyboard k1;
    private KeyboardView keyboardView;
    private EditText editText;
    public interface OnEnsureListener{
        public void onEnsure();
    }
    OnEnsureListener onEnsureListener;
    public void setOnEnsureListener(OnEnsureListener onEnsureListener){
        this.onEnsureListener=onEnsureListener;
    }
    public KeyBoardUtils(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_NULL);//取消系统弹出键盘
        k1=new Keyboard(this.editText.getContext(), R.xml.key);
        this.keyboardView.setKeyboard(k1);//设置显示键盘的样式
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(listener);
    }
    KeyboardView.OnKeyboardActionListener listener=new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editTble=editText.getText();
            int start=editText.getSelectionStart();
            switch (primaryCode){
                case Keyboard.KEYCODE_DELETE:
                    if(editTble!=null&&editTble.length()>0){
                        if(start>0){
                            editTble.delete(start-1,start);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_CANCEL:
                    editTble.clear();
                    break;
                case Keyboard.KEYCODE_DONE:
                    onEnsureListener.onEnsure();
                    break;
                default:
                    editTble.insert(start,Character.toString((char)primaryCode));
                    break;
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };
    //显示键盘
    public void  showKeyboard(){
       int visibility=keyboardView.getVisibility();
        if(visibility== View.INVISIBLE||visibility==View.GONE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
    //隐藏键盘
    public void  hideKeyboard(){
        int visibility=keyboardView.getVisibility();
        if(visibility== View.INVISIBLE||visibility==View.VISIBLE) {
            keyboardView.setVisibility(View.GONE);
        }
    }
}
