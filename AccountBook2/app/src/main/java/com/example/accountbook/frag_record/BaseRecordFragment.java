package com.example.accountbook.frag_record;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.accountbook.R;
import com.example.accountbook.db.AccountBean;
import com.example.accountbook.db.DBManager;
import com.example.accountbook.db.TypeBean;
import com.example.accountbook.utils.BeiZhuDialog;
import com.example.accountbook.utils.KeyBoardUtils;
import com.example.accountbook.utils.SelectTimeDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * 记录页面的支出模块
 */
public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {

    KeyboardView keyboardView;
    EditText monryEt;
    ImageView typeIv;
    TextView typeTv,beizhuTV,timeTV;
    GridView typeGv;
    List<TypeBean> typeList;
    TypeBaseAdapter adapter;
    AccountBean accountBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean=new AccountBean();
        accountBean.setTypename("其他");
        accountBean.setsImageId(R.mipmap.ic_qita_fs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_outcome,container,false);
        initView(view);
        setIniTime();
        //给GridView填充数据的方法
        loadDataToGV();
        setGVListener();
        return view;
    }
    //获取当前时间没显示子啊timetv上
    private void setIniTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time=sdf.format(date);
        timeTV.setText(time);
        accountBean.setTime(time);

        Calendar calendar=Calendar.getInstance();
        int  year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MARCH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);
    }

    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectPos=position;
                adapter.notifyDataSetInvalidated();
                TypeBean typeBean=typeList.get(position);
                String typename=typeBean.getTypename();
                typeTv.setText(typename);
                accountBean.setTypename(typename);
                int simageId=typeBean.getSimageId();
                typeIv.setImageResource(simageId);
                accountBean.setsImageId(simageId);
            }
        });
    }

    //给GridView填充数据的方法
    public void loadDataToGV() {
        typeList=new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);
    }

    private void initView(View view){
        keyboardView=view.findViewById(R.id.frag_record_keyboard);
        monryEt=view.findViewById(R.id.frag_record_et_money);
        typeIv=view.findViewById(R.id.frag_record_iv);
        typeGv=view.findViewById(R.id.frag_record_gv);
        typeTv=view.findViewById(R.id.frag_record_tv_type);
        beizhuTV=view.findViewById(R.id.frag_record_tv_beizhu);
        timeTV=view.findViewById(R.id.frag_record_tv_time);
        beizhuTV.setOnClickListener(this);
        timeTV.setOnClickListener(this);
        //让自定义键盘显示出来
        KeyBoardUtils boardUtils=new KeyBoardUtils(keyboardView,monryEt);
        boardUtils.showKeyboard();
        //设置按钮，监听确定按钮被点击
        boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String moneyStr=monryEt.getText().toString();
                if(TextUtils.isEmpty(moneyStr)||moneyStr.equals("0")){
                    getActivity().finish();
                    return ;
                }
                float money=Float.parseFloat(moneyStr);
                accountBean.setMoney(money);
                saveAccountToDB();
                getActivity().finish();
            }
        });
    }
    public abstract void saveAccountToDB();

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag_record_tv_time:
                showTimeDialog();
                break;
            case R.id.frag_record_tv_beizhu:
                showBZDialog();
                break;
        }
    }

    private void showTimeDialog() {
        SelectTimeDialog dialog=new SelectTimeDialog(getContext());
        dialog.show();

        dialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTV.setText(time);
                accountBean.setTime(time);
                accountBean.setYear(year);
                accountBean.setMonth(month);
                accountBean.setDay(day);
            }
        });
    }


    public void showBZDialog(){
        final BeiZhuDialog dialog=new BeiZhuDialog(getContext());
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new BeiZhuDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
               String msg=dialog.getEditText();
               if(!TextUtils.isEmpty(msg)){
                   beizhuTV.setText(msg);
                   accountBean.setBeizhu(msg);
               }
               dialog.cancel();
            }
        });
    }
}