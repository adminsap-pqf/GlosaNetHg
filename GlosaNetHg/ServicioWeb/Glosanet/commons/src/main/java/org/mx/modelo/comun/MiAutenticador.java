package org.mx.modelo.comun;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.logging.Logger;

public class MiAutenticador extends Authenticator {

    private final static Logger LOGGER = Logger.getLogger(MiAutenticador.class.getName());

    private PasswordAuthentication passwordAuthentication;

    public MiAutenticador(String user, String pwd) {
        this.passwordAuthentication = new PasswordAuthentication(user, pwd);
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return passwordAuthentication;
    }
}
