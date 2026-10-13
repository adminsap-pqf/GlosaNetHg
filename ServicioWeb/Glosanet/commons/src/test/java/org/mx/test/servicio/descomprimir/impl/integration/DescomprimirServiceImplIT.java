package org.mx.test.servicio.descomprimir.impl.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.servicio.comun.UsuarioService;
import org.mx.servicio.descomprimir.DescomprimirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@WebAppConfiguration
public class DescomprimirServiceImplIT {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DescomprimirService descomprimirService;

    private UsuarioBO usuario = null;

    @Before
    public void setup() throws Exception {

        //usuario = usuarioService.validarUsuario("comarket", "613c9504bcef2ba34ea97febf9624dedbd068726");
        usuario = usuarioService.validarUsuario("csorzanoico", getPassword("cambiame"));
    }

    //region Helpers
    private String getPassword(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.reset();
        md.update(input.getBytes("utf8"));
        return String.format("%040x", new BigInteger(1, md.digest()));
    }
    //endregion

    @Test
    public void testLogin()
    {

        Assert.assertNotNull(usuario);
    }

    @Test
    public void uploadFile() {

        ClientesBO clientesBO = usuario.getCliente();
        clientesBO.setRfc("ICO9707094A5");
        clientesBO.setRfcActual("ICO9707094A5");

        descomprimirService.setLogginCliente(clientesBO);
        descomprimirService.setUsuarioLoggin(usuario);
        descomprimirService.setRutaDataStageArchivos("/Users/sergio.zavala/Desktop/DataStages/");
        int salida = 0;
        try {
            salida = descomprimirService.uploadFile(new File("/Users/sergio.zavala/Desktop/DataStages/COMARKET_355988_solicitudes_Enero17.zip"));
        } catch (GlosaNetException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(salida, 1);
    }
}