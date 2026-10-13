package ryndem.mx.glosanet.messagebroker.datastage.vucem.document;

public class VucemDocumentRequest {
    private String idDocument;
    private Long idDocRequest;
    private Long idCustomer;

    public VucemDocumentRequest(String numeroFactura, Long idFactura, Long idCustomer) {
        this.idDocument = numeroFactura;
        this.idDocRequest = idFactura;
        this.idCustomer = idCustomer;
    }

    public String getIdDocument() { return idDocument; }
    public void setIdDocument(String idDocument) { this.idDocument = idDocument; }
    public Long getIdDocRequest() { return idDocRequest; }
    public void setIdDocRequest(Long idDocRequest) { this.idDocRequest = idDocRequest; }
    public Long getIdCustomer() { return idCustomer; }
    public void setIdCustomer(Long idCustomer) { this.idCustomer = idCustomer; }

    @Override
    public String toString() {
        return "VucemDocumentRequest{" +
                "idDocument='" + idDocument + '\'' +
                ", idDocRequest=" + idDocRequest +
                ", idCustomer=" + idCustomer +
                '}';
    }
}
