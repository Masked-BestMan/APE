package businessmonitor.com.example.newbusinessmonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import UtilBean.DBUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText account, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        account = (EditText) findViewById(R.id.login_account);
        password = (EditText) findViewById(R.id.login_password);
        Button login = (Button) findViewById(R.id.ensure_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                登陆后检查是否存在该用户，存在则提示成功，保存UserInfoBean并跳转，否则提示失败
                需要用到3个表
                 */
                String a = String.valueOf(account.getText());
                String p = String.valueOf(password.getText());
                if (a.equals("") || p.equals("")) {
                    Toast.makeText(LoginActivity.this, "账户或密码不能为空!", Toast.LENGTH_SHORT).show();
                } else {
//                    Intent intent = new Intent(LoginActivity.this, Select_Activity.class);
//                    startActivity(intent);
//                    LoginActivity.this.finish();
                    if (manager != null && isSoftInputMethodShowing(LoginActivity.this)) {
                        manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    DBUtil.getHttpUtilInstance().checkAccount(a, p, new DBUtil.OnReceiveResultListener() {
                        @Override
                        public void onReceived(ArrayList<AbstractDataBean> response) {
                            if (response.size() < 1)
                                Toast.makeText(LoginActivity.this, "请检查用户名或密码!", Toast.LENGTH_SHORT).show();
                            else {
                                Log.d("Select_Activity",response+"");
                                Intent intent = new Intent(LoginActivity.this, Select_Activity.class);
                                intent.putExtra("user_market_list", response); //将该用户可查看商会列表传递给主界面
                                startActivity(intent);
                                LoginActivity.this.finish();

                            }
                        }

                        @Override
                        public void onError() {
                            Toast.makeText(LoginActivity.this, "服务器维护中!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }

    public boolean isSoftInputMethodShowing(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;   //这个方法获取可能不是真实屏幕的高度(可能有虚拟导航栏)

        //获取View可见区域的bottom
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return usableHeight - rect.bottom != 0;
    }
}