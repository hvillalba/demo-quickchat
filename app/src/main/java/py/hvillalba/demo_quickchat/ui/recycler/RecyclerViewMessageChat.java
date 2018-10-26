package py.hvillalba.demo_quickchat.ui.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quickblox.chat.QBChatService;
import com.quickblox.chat.model.QBChatMessage;

import java.util.List;

import py.hvillalba.demo_quickchat.R;
import py.hvillalba.demo_quickchat.data.singleton.QBUserHolder;

public class RecyclerViewMessageChat extends RecyclerView.Adapter<CustomViewHolderMessageChat> {

    Context context;
    List<QBChatMessage> qbChatMessageList;
    private static final int VIEW_TYPE_ME = 0;
    private static final int VIEW_TYPE_USER= 1;

    public RecyclerViewMessageChat(Context context, List<QBChatMessage> list){
        this.context = context;
        qbChatMessageList = list;
    }

    @NonNull
    @Override
    public CustomViewHolderMessageChat onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layoutId;
        if (VIEW_TYPE_ME == viewType){
            layoutId = R.layout.list_send_message;
        }else {
            layoutId = R.layout.list_receive_message;
        }
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        CustomViewHolderMessageChat customViewHolderMessageChat = new CustomViewHolderMessageChat(view);
        return customViewHolderMessageChat;
    }

    @Override
    public int getItemViewType(int position) {
        if (qbChatMessageList.get(position).getSenderId().equals(QBChatService.getInstance().getUser().getId()))
            return VIEW_TYPE_ME;
        else
            return VIEW_TYPE_USER;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderMessageChat holder, int i) {
        QBChatMessage qbChatMessage = qbChatMessageList.get(holder.getAdapterPosition());
        if (qbChatMessage.getSenderId().equals(QBChatService.getInstance().getUser().getId())){
            holder.bubbleTextView.setText(qbChatMessage.getBody());
        }else {
            holder.bubbleTextView.setText(qbChatMessage.getBody());
            holder.textView.setText(QBUserHolder.getInstance().getUserById(qbChatMessage.getSenderId()).getFullName());
        }
    }

    @Override
    public int getItemCount() {
        return qbChatMessageList.size();
    }
}
