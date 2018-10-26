package py.hvillalba.demo_quickchat.data;

import com.quickblox.users.model.QBUser;

import java.io.Serializable;
import java.util.List;

import py.hvillalba.demo_quickchat.data.singleton.QBUserHolder;

public class ChatUtils implements Serializable {

    public static final String DIALOG_EXTRA = "dialogs";

    public static String createChatDialogName(List<Integer> qbUsers){
        List<QBUser> qbUsers1 = QBUserHolder.getInstance().getUsersByIds(qbUsers);
        StringBuilder name = new StringBuilder();
        for (QBUser user: qbUsers1){
            name.append(user.getFullName()).append(" ");
            if (name.length() > 30){
                name = name.replace(30, name.length()-1, "...");
            }
        }
        return  name.toString();
    }
}
