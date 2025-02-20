package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.accountbook.adapter.RecordPagerAdapter;
import com.example.accountbook.frag_record.IncomeFragment;
import com.example.accountbook.frag_record.BaseRecordFragment;
import com.example.accountbook.frag_record.OutcomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        tabLayout=findViewById(R.id.record_tabs);
        viewPager=findViewById(R.id.record_vp);
        initPager();
    }

    private void initPager(){
        //初始化Viewpager页面的集合
        List<Fragment> fragmentList=new ArrayList<>();
        //创建收入支出页面，放置在Fragment当中
        OutcomeFragment outcomeFragment=new OutcomeFragment();//支出
        IncomeFragment incomeFragment=new IncomeFragment();//收入
        fragmentList.add(outcomeFragment);
        fragmentList.add(incomeFragment);
//      创建适配器
        RecordPagerAdapter pagerAdapter=new RecordPagerAdapter(getSupportFragmentManager(),fragmentList);
        //设置适配器
        viewPager.setAdapter(pagerAdapter);
        //将Tablayout和ViewPager进行关联
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.record_iv_back:
                finish();
                break;
        }
    }
}