package py.hvillalba.demo_quickchat.ui.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import py.hvillalba.demo_quickchat.R;

public class CustomViewHolderListUser extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;
    LinearLayout linearLayout;

    public CustomViewHolderListUser(@NonNull View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.linear);
        imageView = itemView.findViewById(R.id.imageView1);
        textView = itemView.findViewById(R.id.textView1);

    }
}
