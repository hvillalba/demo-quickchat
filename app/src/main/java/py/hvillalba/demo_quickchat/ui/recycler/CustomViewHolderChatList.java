package py.hvillalba.demo_quickchat.ui.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import py.hvillalba.demo_quickchat.R;

public class CustomViewHolderChatList extends RecyclerView.ViewHolder {
     TextView tvTitle, tvMessage, tvFecha;
     ImageView imageView;

    public CustomViewHolderChatList(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvMessage = itemView.findViewById(R.id.tvMessage);
        tvFecha = itemView.findViewById(R.id.tvFecha);
    }

}
