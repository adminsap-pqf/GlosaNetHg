package org.mx.servicio.scheduler;

import org.mx.modelo.notificaciones.po.NotificationEventoPO;
import org.mx.servicio.automatico.AutomaticoService;
import org.mx.servicio.registros.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

public class TareaGLOSANET {

    private final static Logger LOGGER = Logger.getLogger(TareaGLOSANET.class.getName());

    @Autowired
    AutomaticoService automaticoService;

    @Autowired
    RegistroService registroService;

    public void imprimeTiempoActual() {
        LOGGER.info("@imprimeTiempoActual: Tiempo actual: " + Calendar.getInstance().getTime());
    }

    @Transactional(rollbackFor = {Exception.class})
    public void ejecutaVerificacion() {

        LOGGER.info("@ejecutaVerificacion: Inicio");

        try {
            automaticoService.crearReportesDescargas();
            //automaticoService.verificarDataStage();

        } catch (Exception e) {
            LOGGER.severe("@ejecutaVerificacion: Excepción al ejecutar método");
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean ejecutaProgramacionDICIPA() {

        LOGGER.info("@ejecutaProgramacionDICIPA: Inicio");

        try {
            automaticoService.crearReportes_DICIPA();
            return true;
        } catch (Exception e) {
            LOGGER.severe("@ejecutaProgramacionDICIPA: Excepción al ejecutar método");
            e.printStackTrace();
        }
        return false;
    }


    @Transactional(rollbackFor = {Exception.class})
    public void ejecutaProgramacionNotificaciones() {

        LOGGER.info("@ejecutaProgramacionNotificaciones: Inicio");

        try {
            final LocalDateTime today = LocalDateTime.now(ZoneId.systemDefault());
            registroService.getNotificacionesEventoHoy();
            registroService.notificaTramitesPendientesCOFEPRIS(today);
        } catch (Exception e) {
            LOGGER.severe("@ejecutaProgramacionNotificaciones: Excepción al ejecutar método");
            e.printStackTrace();
        }
    }


    @Transactional(rollbackFor = {Exception.class})
    public void ejecutaProgramacionCOFEPRIS() {

        LOGGER.info("@ejecutaProgramacionCOFEPRIS: Inicio");

        try {

            registroService.obtenerRegistros();

        } catch (Exception e) {
            LOGGER.severe("@ejecutaProgramacionCOFEPRIS: Excepción al ejecutar método");
            e.printStackTrace();
        }
    }
}
