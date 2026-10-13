package ryndem.mx.glosanet.messagebroker.datastage.vucem.document;

import ryndem.mx.glosanet.messagebroker.Message;

import java.util.List;

public class VucemDocumentsRequestMessage extends Message {
    private List<VucemDocumentRequest> requestedDocs;

    public VucemDocumentsRequestMessage(List<VucemDocumentRequest> requestedDocs) {
        this.requestedDocs = requestedDocs;
    }

    public List<VucemDocumentRequest> getRequestedDocs() {
        return requestedDocs;
    }

    public void setRequestedDocs(List<VucemDocumentRequest> requestedDocs) {
        this.requestedDocs = requestedDocs;
    }
}
