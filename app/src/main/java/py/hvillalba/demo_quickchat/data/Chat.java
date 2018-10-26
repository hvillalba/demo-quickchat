package py.hvillalba.demo_quickchat.data;

import java.io.Serializable;
import java.util.Date;

public class Chat implements Serializable {
    private String title;
    private String message;
    private Date fecha;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
