package org.mx.servicio.scheduler.notificaciones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public class notificationBody {
    private static final Logger log = Logger.getLogger(notificationBody.class.getName());
    private static final String TEMPLATE_PATH = "assets/notifications/notificationTemplate.html";
    private StringBuilder sb;
    private String costumerName;
    private String eventName;
    private List<EventRow> events = new ArrayList<>();

    public notificationBody(String costumerName) {
        sb = new StringBuilder();
        this.costumerName = costumerName;
    }

    public void appendEvent(String productCode, String productName, String folioCofepris, Date expirationDateReg, Date executionDate, Integer duracion) {
        events.add(new EventRow(productCode, productName, folioCofepris, expirationDateReg, executionDate, duracion));
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public Integer getEventCount() {
        return events.size();
    }
    public void newEnvelope() { sb = new StringBuilder();}
    public String buildHTML() {
        Locale locale = new Locale("es", "MX");
        loadTemplate();

        //Nombre del cliente
        Integer indexCustomerName = sb.indexOf("{{customerName}}");
        sb.replace(indexCustomerName,indexCustomerName + "{{customerName}}".length(), costumerName.toUpperCase());

        //Nombre del grupo de eventos
        Integer indexEventName = sb.indexOf("{{eventName}}");
        sb.replace(indexEventName,indexEventName + "{{eventName}}".length(), eventName);

        //Fecha de envío
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
        Integer indexDateOfQuery = sb.indexOf("{{dateOfQuery}}");
        sb.replace(indexDateOfQuery,indexDateOfQuery + "{{dateOfQuery}}".length(), dateFormat.format(new Date()));

        //Conteo de eventos
        Integer eventsCount = sb.indexOf("{{eventsCount}}");
        sb.replace(eventsCount,eventsCount + "{{eventsCount}}".length(), events.size() + "");

        //Listado de eventos
        for (EventRow row : events) {
            Integer eventRow = sb.indexOf("{{eventRow}}");
            sb.replace(eventRow,eventRow + "{{eventRow}}".length(), row.toString());
        }
        sb.delete(sb.indexOf("{{eventRow}}"), sb.indexOf("{{eventRow}}") + "{{eventRow}}".length());

        return sb.toString();
    }
    private void loadTemplate() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(TEMPLATE_PATH);

        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (IOException ex) {
            log.severe("Error al localizar la plantilla para el envío de notificaciones de eventos. " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.severe("Error al cargar la plantilla para el envío de notificaciones de eventos. " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
