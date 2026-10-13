package org.mx.servicio.scheduler.notificaciones;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

public class EventRow {
    private String clave;
    private String productName;
    private String folioCofepris;
    private Date expirationDateReg;
    private Date executionDate;
    private Integer duracion;

    public EventRow(String clave, String productName, String folioCofepris, Date expirationDateReg, Date executionDate, Integer duracion) {
        this.clave = clave;
        this.productName = productName;
        this.folioCofepris = folioCofepris;
        this.expirationDateReg = expirationDateReg;
        this.executionDate = executionDate;
        this.duracion = duracion;
    }
    public EventRow(){}

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getFolioCofepris() { return folioCofepris; }
    public void setFolioCofepris(String folioCofepris) { this.folioCofepris = folioCofepris; }
    public Date getExpirationDateReg() { return expirationDateReg; }
    public void setExpirationDateReg(Date expirationDateReg) { this.expirationDateReg = expirationDateReg; }
    public Date getExecutionDate() { return executionDate; }
    public void setExecutionDate(Date executionDate) { this.executionDate = executionDate; }
    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRow eventRow = (EventRow) o;
        return Objects.equals(getProductName(), eventRow.getProductName()) &&
                getExecutionDate().equals(eventRow.getExecutionDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductName(), getFolioCofepris(), getExecutionDate());
    }

    @Override
    public String toString() {
        Locale locale = new Locale("es", "MX");
        String pathToCofepris = folioCofepris.equals("Sin folio") ?
                "#": "http://tramiteselectronicos02.cofepris.gob.mx/EstadoTramite/Consulta.aspx?id=" + folioCofepris;

        return "<tr><td><table class=\"full\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
                "<tr><td valign=\"top\"><table width=\"100\" align=\"left\" class=\"res-full\" border=\"0\" " +
                "cellpadding=\"0\" cellspacing=\"0\"><tr><td><table align=\"left\" class=\"res-full\" border=\"0\" " +
                "cellpadding=\"0\" cellspacing=\"0\"><tr><td><table style=\"border-radius:4px; overflow:hidden;\" " +
                "align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><img width=\"97\" " +
                "style=\"max-width:97px; width:100%; display:block; line-height:0px; font-size:0px; border:0px;\" " +
                "src=\"https://glosanet.ryndem.mx/assets/images/notification/box.svg\"></td>" +
                "</tr></table></td></tr></table></td></tr></table><table width=\"1\" " +
                "align=\"left\" class=\"res-full\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td " +
                "height=\"20\" style=\"font-size:0px\">&nbsp;</td></tr></table><table style=\"border:1px solid " +
                "#F0F0F0; border-radius:4px; padding:17px 20px;\" width=\"485\" align=\"right\" class=\"res-full\" " +
                "border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"res-center\" style=\"text-align:left;" +
                " color:#607080; font-family:'Nunito', Arial, Sans-serif; font-size:15px; letter-spacing:0.7px; " +
                "word-break:break-word; font-weight:600; line-height:14px;\">" + clave + " - " + productName +
                "</td></tr><tr><td height=\"3\" style=\"font-size:0px\">&nbsp;</td></tr><tr><td class=\"res-center\" " +
                "style=\"text-align:left; color:#607080; font-family:'Nunito', Arial, Sans-serif; font-size:16px; " +
                "letter-spacing:0.4px; line-height:23px; word-break:break-word; padding-right:10px;\">" +
                "El registro vence el " + DateFormat.getDateInstance(DateFormat.LONG, locale).format(expirationDateReg) +
                ". </td></tr><tr><td height=\"7\" style=\"font-size:0px;\">&nbsp;</td>" +
                "</tr><tr><td><table align=\"left\" class=\"res-full\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
                "<tr><td><table align=\"center\" class=\"res-full\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
                "<tr><td><table align=\"center\" style=\"border:1.5px solid #999999; border-radius:4px;\" border=\"0\" " +
                "cellpadding=\"0\" cellspacing=\"0\"><tr><td height=\"42\" style=\"padding:0 23px; text-align:center;\">" +
                "<a href=\"" + pathToCofepris + "\" style=\"color:#607080; letter-spacing:0.9px; font-size:16px; " +
                "font-family:'Nunito', Arial, Sans-serif; text-decoration:none; text-align:center; line-height:24px; " +
                "word-break:break-word; font-weight:600;\">" + folioCofepris + " </a></td></tr></table></td></tr></table>" +
                "</td></tr></table><table width=\"8\" align=\"left\" class=\"res-full\" border=\"0\" cellpadding=\"0\" " +
                "cellspacing=\"0\"><tr><td height=\"8\"></td></tr></table><table align=\"res-right\" border=\"0\" " +
                "cellpadding=\"0\" cellspacing=\"0\"><tr><td><table width=\"200\" align=\"left\" class=\"res-full\" " +
                "border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><table style=\"width:100%\" align=\"center\"" +
                " border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><table class=\"full\" align=\"center\" " +
                "border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"res-left\" style=\"text-align:left;" +
                " color:#607080; font-family:'Nunito', Arial, Sans-serif; font-size:16px; letter-spacing:0.4px; " +
                "line-height:23px; word-break:break-word\">" + differenceDays(executionDate) + " </td></tr></table></td><td><table class=\"full\"" +
                " align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"res-right\" " +
                "style=\"text-align:right; color:#607080; font-family:'Nunito', Arial, Sans-serif; font-size:16px; " +
                "letter-spacing:0.4px; line-height:23px; word-break:break-word\">" +
                DateFormat.getDateInstance(DateFormat.DEFAULT, locale).format(executionDate) +
                " </td></tr></table></td></tr></table></td></tr><tr><td height=\"5\" style=\"font-size:0px\">&nbsp;</td></tr><tr>" +
                "<td><table style=\"width:100%; background:#F0F0F0; border-radius:2px;\" align=\"center\" " +
                "border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><table style=\"width:" + percentageDiff(executionDate, duracion) + "%; " +
                "background:" + progressBarColor(percentageDiff(executionDate, duracion)) + "; border-radius:2px;\" " +
                "align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td height=\"12\" " +
                "style=\"font-size:0px\">&nbsp; </td></tr></table></td></tr></table></td></tr></table></td></tr>" +
                "</table></td></tr></table></td></tr></table></td></tr><tr><td height=\"20\" style=\"font-size:0px\">&nbsp;</td></tr>" +
                "{{eventRow}}";
    }

    private int percentageDiff(Date date, Integer duracion) {
        Integer percentage = Math.round((duracion - diffSinceDate(date)) * 100 / duracion);
        return percentage > 100 ? 100 : percentage;
    }
    private String progressBarColor(Integer percentage) {
        if(percentage > 90)
            return "#b60303";
        else if (percentage <= 90 && percentage >= 80)
            return "#d8961b";
        else
            return "#71b603";
    }
    private String differenceDays(Date date) {
        long noOfDaysBetween = diffSinceDate(date);

        return noOfDaysBetween > 1 ?
                String.format("%d dias", noOfDaysBetween) : String.format("%d dia", noOfDaysBetween);
    }
    private long diffSinceDate(Date date) {
        LocalDate now = LocalDate.now(ZoneId.of("America/Mexico_City"));
        LocalDate aDate = date.toInstant()
                .atZone(ZoneId.of("America/Mexico_City"))
                .toLocalDate();

        return DAYS.between(now, aDate);
    }
}
