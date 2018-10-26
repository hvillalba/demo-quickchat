package py.hvillalba.demo_quickchat.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBIncomingMessagesManager;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBChatDialogMessageListener;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.request.QBMessageGetBuilder;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;

import org.jivesoftware.smack.SmackException;

import java.util.ArrayList;

import py.hvillalba.demo_quickchat.R;
import py.hvillalba.demo_quickchat.data.ChatUtils;
import py.hvillalba.demo_quickchat.data.singleton.QBChatMessageHolder;
import py.hvillalba.demo_quickchat.ui.recycler.RecyclerViewMessageChat;

public class ChatMessageActivity extends AppCompatActivity {

    QBChatDialog qbChatDialog;
    RecyclerView recyclerView;
    ImageButton submitButton;
    EditText editText;
    RecyclerViewMessageChat recyclerViewMessageChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);
        initViews();
        initChatDialog();
        retriveMessages();

    }

    private void retriveMessages() {
        QBMessageGetBuilder messageGetBuilder = new QBMessageGetBuilder();
        messageGetBuilder.setLimit(500);
        if (qbChatDialog != null){
            QBRestChatService.getDialogMessages(qbChatDialog, messageGetBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatMessage>>() {
                @Override
                public void onSuccess(ArrayList<QBChatMessage> qbChatMessages, Bundle bundle) {
                    QBChatMessageHolder.getInstance().putMessages(qbChatDialog.getDialogId(), qbChatMessages);
                    recyclerViewMessageChat = new RecyclerViewMessageChat(getBaseContext(), qbChatMessages);
                    recyclerView.setAdapter(recyclerViewMessageChat);
                }

                @Override
                public void onError(QBResponseException e) {
                    Log.e("Error", e.getMessage());
                }
            });
        }
    }

    private void initChatDialog() {
        qbChatDialog = (QBChatDialog) getIntent().getSerializableExtra(ChatUtils.DIALOG_EXTRA);
        qbChatDialog.initForChat(QBChatService.getInstance());

        QBIncomingMessagesManager incomingMessagesManager = QBChatService.getInstance().getIncomingMessagesManager();
        incomingMessagesManager.addDialogMessageListener(new QBChatDialogMessageListener() {
            @Override
            public void processMessage(String s, QBChatMessage qbChatMessage, Integer integer) {

            }

            @Override
            public void processError(String s, QBChatException e, QBChatMessage qbChatMessage, Integer integer) {
                Log.e("Error", e.getMessage());
            }
        });
        qbChatDialog.addMessageListener(new QBChatDialogMessageListener() {
            @Override
            public void processMessage(String s, QBChatMessage qbChatMessage, Integer integer) {
                QBChatMessageHolder.getInstance().putMessage(qbChatMessage.getDialogId(), qbChatMessage);
                ArrayList<QBChatMessage> messages = QBChatMessageHolder.getInstance().getMessagesByDialogId(qbChatMessage.getDialogId());
                recyclerViewMessageChat = new RecyclerViewMessageChat(getBaseContext(), messages);
                recyclerView.setAdapter(recyclerViewMessageChat);
            }

            @Override
            public void processError(String s, QBChatException e, QBChatMessage qbChatMessage, Integer integer) {

            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.list_of_message);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        submitButton = findViewById(R.id.send_button);
        editText = findViewById(R.id.edt_content);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QBChatMessage qbChatMessage = new QBChatMessage();
                qbChatMessage.setBody(editText.getText().toString());
                qbChatMessage.setSenderId(QBChatService.getInstance().getUser().getId());
                qbChatMessage.setSaveToHistory(true);
                try {
                    qbChatDialog.sendMessage(qbChatMessage);
                } catch (SmackException.NotConnectedException e) {
                    Log.e("SendM", e.getMessage());
                }
                //Put message to cache
                QBChatMessageHolder.getInstance().putMessage(qbChatDialog.getDialogId(),qbChatMessage);
                ArrayList<QBChatMessage> messages = QBChatMessageHolder.getInstance().getMessagesByDialogId(qbChatDialog.getDialogId());
                recyclerViewMessageChat = new RecyclerViewMessageChat(getBaseContext(), messages);
                recyclerView.setAdapter(recyclerViewMessageChat);

                //Remove text from edit text
                editText.setText("");
                editText.setFocusable(true);
            }
        });
    }
}
