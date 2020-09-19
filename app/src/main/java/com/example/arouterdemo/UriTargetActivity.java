package com.example.arouterdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.arouterdemo.databinding.ActivityUriTargetBinding;

@Route(path = RouteConstant.URI_ACTIVITY)
public class UriTargetActivity extends AppCompatActivity {
    @Autowired
    String mUserName;
    @Autowired
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUriTargetBinding binding = ActivityUriTargetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ARouter.getInstance().inject(this);
        binding.parmasTv.setText(mUserName + "," + age);
    }
}