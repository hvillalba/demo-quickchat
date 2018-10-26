package py.hvillalba.demo_quickchat.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import py.hvillalba.demo_quickchat.R;

public class RegistroActivity extends AppCompatActivity {
    EditText edUser, edPassword, edNombreCompleto;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        createSesion();
        edUser = findViewById(R.id.edUser);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnRegistrar);
        edNombreCompleto = findViewById(R.id.edNombreCompleto);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edUser.getText().toString();
                String password = edPassword.getText().toString();
                String nombreCompleto = edNombreCompleto.toString();

                QBUser qbUser = new QBUser(user,password);
                qbUser.setFullName(nombreCompleto);
                QBUsers.signUp(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                        Toast.makeText(RegistroActivity.this, "Login satisfactorio...!!!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegistroActivity.this, LoginActivity.class);
                        startActivity(i);
                        RegistroActivity.this.finish();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Toast.makeText(RegistroActivity.this, "No se pudo Loguear", Toast.LENGTH_SHORT).show();
                        Log.e("Login", e.getMessage());
                    }
                });
            }
        });
    }

    private void createSesion() {
        QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                Log.i("Success", "success");
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("Error", e.getMessage());
            }
        });
    }
}
