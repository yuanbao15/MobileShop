package com.idaniu.maga.shopping.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.idaniu.maga.shopping.R;
import com.idaniu.maga.shopping.manager.UserManager;

/**
 * 注册界面
 * Created by yuanbao15 on 2017/11/2.
 */
public class RegisterActivity extends BaseActivity{
    private static final String TAG = "RegisterActivity";
    private static final int VERIRY_CODE = 1000;

    private EditText mPhoneEditText, mPasswordEditText;
    private TextView mRegisterButton;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPhoneEditText = (EditText) findViewById(R.id.phoneEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        mRegisterButton = (TextView) findViewById(R.id.registerButton);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String phone = mPhoneEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();

            if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)){
                Toast.makeText(RegisterActivity.this, "请完整输入手机号码和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            showDialog();

            UserManager.getInstance().register(phone, password, new UserManager.OnRegisterListener() {
                @Override
                public void onRegister() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        hideDialog();
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                        }
                    });
                }

                @Override
                public void onError(String msg) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        hideDialog();
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            }
        });

        mPhoneEditText.setText(UserManager.getInstance().getPhone());   //回调显示在登录界面yb
        mPasswordEditText.setText(UserManager.getInstance().getPassword());

    }

    //返回按键
    public void back(View v){
        finish();
    }

    //显示对话框
    private void showDialog(){
        hideDialog();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("yb正在加载");
        mProgressDialog.show();
    }

    //毁掉进度条对话框
    private void hideDialog() {
        if (mProgressDialog != null){
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }
}
