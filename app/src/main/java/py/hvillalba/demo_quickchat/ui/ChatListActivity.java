package py.hvillalba.demo_quickchat.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.BaseService;
import com.quickblox.auth.session.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.BaseServiceException;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

import py.hvillalba.demo_quickchat.R;
import py.hvillalba.demo_quickchat.data.singleton.QBUserHolder;
import py.hvillalba.demo_quickchat.ui.recycler.RecyclerChatList;

public class ChatListActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        floatingActionButton = findViewById(R.id.btnFloating);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatListActivity.this, ListUserActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recyclerChatList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        createSessionForChat();
        loadChatDialogs();
    }

    private void loadChatDialogs() {
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();

        QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBChatDialog> qbChatDialogs, Bundle bundle) {
                RecyclerChatList recyclerChatList = new RecyclerChatList(ChatListActivity.this, qbChatDialogs);
                recyclerView.setAdapter(recyclerChatList);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("Error", e.getMessage());
            }
        });
    }

    private void createSessionForChat() {
       String user, pass;
       user = getIntent().getStringExtra("user");
       pass = getIntent().getStringExtra("pass");
        final QBUser qbUser = new QBUser(user, pass);

        QBUsers.getUsers(null).performAsync(new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> qbUsers, Bundle bundle) {
                QBUserHolder.getInstance().putUsers(qbUsers);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("QBResponseException", e.getMessage());
            }
        });

        QBAuth.createSession(qbUser).performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                qbUser.setId(qbSession.getUserId());
                try {
                    qbUser.setPassword(BaseService.getBaseService().getToken());
                } catch (BaseServiceException e) {
                    e.printStackTrace();
                    Log.e("Error:", e.getMessage() );
                }
                QBChatService.getInstance().login(qbUser, new QBEntityCallback() {
                    @Override
                    public void onSuccess(Object o, Bundle bundle) {
                        Log.e("Login", "Success");
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Log.e("Error", e.getMessage());
                    }
                });
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("Error", e.getMessage());
            }
        });
    }
}
