package py.hvillalba.demo_quickchat.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import py.hvillalba.demo_quickchat.R;
import py.hvillalba.demo_quickchat.utils.Constans;

public class LoginActivity extends AppCompatActivity {
    EditText edUser, edPassword;
    Button btnLogin;
    Button btnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFrameWork();

        edUser = findViewById(R.id.edUser);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user = edUser.getText().toString();
                final String password = edPassword.getText().toString();

                QBUser qbUser = new QBUser(user,password);
                QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                        Toast.makeText(LoginActivity.this, "Login satisfactorio...!!!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, ChatListActivity.class);
                        i.putExtra("user", user);
                        i.putExtra("pass", password);
                        startActivity(i);
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Toast.makeText(LoginActivity.this, "No se pudo Loguear", Toast.LENGTH_SHORT).show();
                        Log.e("Login", e.getMessage());
                    }
                });
            }
        });
    }

    private void initFrameWork() {
        QBSettings.getInstance().init(getApplicationContext(), Constans.APP_ID, Constans.AUTH_KEY,
                Constans.AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(Constans.ACCOUNT_KEY);
    }
}
