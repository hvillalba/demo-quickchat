package py.hvillalba.demo_quickchat.ui.recycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quickblox.chat.model.QBChatDialog;

import java.util.ArrayList;
import java.util.List;

import py.hvillalba.demo_quickchat.R;
import py.hvillalba.demo_quickchat.data.ChatUtils;
import py.hvillalba.demo_quickchat.ui.ChatListActivity;
import py.hvillalba.demo_quickchat.ui.ChatMessageActivity;

public class RecyclerChatList extends RecyclerView.Adapter<CustomViewHolderChatList> {

    private Context context;
    private List<QBChatDialog> qbChatDialogs = new ArrayList<>();



    public RecyclerChatList(Context context, List<QBChatDialog> list){
        this.context = context;
        this.qbChatDialogs = list;
    }

    @NonNull
    @Override
    public CustomViewHolderChatList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, null);
        CustomViewHolderChatList customViewHolderChatList = new CustomViewHolderChatList(view);
        return customViewHolderChatList;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderChatList holder, int i) {
        try {
            final QBChatDialog qbChatDialog = qbChatDialogs.get(holder.getAdapterPosition());
            holder.tvTitle.setText(qbChatDialog.getName());
            holder.tvMessage.setText(qbChatDialog.getLastMessage());
            holder.tvFecha.setText(qbChatDialog.getUpdatedAt().toString());
            holder.tvFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChatMessageActivity.class);
                    intent.putExtra(ChatUtils.DIALOG_EXTRA, qbChatDialog);
                    context.startActivity(intent);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return qbChatDialogs.size() ;
    }
}
