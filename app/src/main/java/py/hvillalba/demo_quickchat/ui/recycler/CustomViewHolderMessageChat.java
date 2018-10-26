package py.hvillalba.demo_quickchat.ui.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.library.bubbleview.BubbleTextView;

import org.w3c.dom.Text;

import py.hvillalba.demo_quickchat.R;

public class CustomViewHolderMessageChat extends RecyclerView.ViewHolder {
    BubbleTextView bubbleTextView;
    TextView textView;

    public CustomViewHolderMessageChat(@NonNull View itemView) {
        super(itemView);
        bubbleTextView = itemView.findViewById(R.id.message_content);
        textView = itemView.findViewById(R.id.message_user);

    }
}
