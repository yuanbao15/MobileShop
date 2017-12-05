package com.idaniu.maga.shopping.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.idaniu.maga.shopping.R;
import com.idaniu.maga.shopping.manager.UserManager;

/**
 * 登录界面.实现登录时检验账号和密码及注册的问题，用pref本地实现。但登陆成功后跳转的问题to do
 * Created by yuanbao on 2017-11-2
 *
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    public static final int LOGIN_CODE = 10001;     //登录成功标识

    private TextView mRegisterButton, mFogetButton, mLoginButton;
    private EditText mPhoneEdit, mPasswordEdit;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        mPhoneEdit = (EditText) findViewById(R.id.phoneEditText);
        mPasswordEdit = (EditText) findViewById(R.id.passwordEditText);

        mRegisterButton = (TextView) findViewById(R.id.registerButton);
        mFogetButton = (TextView) findViewById(R.id.forgetButton);
        mLoginButton = (TextView) findViewById(R.id.loginButton);

        //注册按钮
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            }
        });

        //登录按钮
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //phone、password都不能为空
            String phone = mPhoneEdit.getText().toString();
            String password = mPasswordEdit.getText().toString();
            if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(phone.trim()) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password.trim())) {
                Toast.makeText(LoginActivity.this, "请输入手机号与密码", Toast.LENGTH_SHORT).show();
                return;
            }

            //开始登录
            login(phone, password);
            }
        });

        //如果曾经登录成功过，自动填入
        mPhoneEdit.setText(UserManager.getInstance().getPhone());
        mPasswordEdit.setText(UserManager.getInstance().getPassword());
    }

    private void login(String phone, String password) {
        showDialog();//显示登录进度框，像这些显示隐藏的逻辑一定要记得出入口，一个出口都不要漏掉哦！！！

        UserManager.getInstance().login(phone, password, new UserManager.OnLoginListener() {
            @Override
            public void onLogin() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    hideDialog();//不管成功还是失败，第一件事就是取消掉进度框

                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);//设置登录成功标志，前一个Activity根据这个标志进行下一步操作

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivityForResult(intent, 10001);  //

                    //登录成功后，这个页面不再有用，直接杀掉
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

                    Toast.makeText(LoginActivity.this, "登录失败，请检查用户名或密码是否准确", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10002){   //表示注册成功，销毁掉上一个activity
            if(resultCode == RESULT_OK){
                finish();
            }
        }
    }

    private void showDialog(){
        hideDialog();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("yb正在加载");
        mProgressDialog.show();
    }

    private void hideDialog(){
        if(mProgressDialog != null){
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }
}
