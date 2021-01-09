package tcp.common;

import java.io.IOException;

public class ServiceException  extends RuntimeException{

    public ServiceException(String message, IOException e) {
        super(message);
    }
}
