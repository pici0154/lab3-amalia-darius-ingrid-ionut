package tcp.common;
import java.io.*;

public class Message<T> implements Serializable {
    public static final String OK = "ok";
    public static final String ERROR = "error";

    private String header;
    private T body;

    public Message() {
    }

    public Message(String header, T body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public T getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }


    public void writeTo(ObjectOutputStream os) throws IOException {
        os.writeObject(this);
        os.flush();
    }

    public static Message readFrom(ObjectInputStream is) throws IOException, ClassNotFoundException {
        return (Message) is.readObject();
    }

}
