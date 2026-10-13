package ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence;

import ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po.SolicitudAcuseFacturaPO;

import java.util.List;

public interface AcuseCoveDAO {
    List<SolicitudAcuseFacturaPO> saveFactura (List<SolicitudAcuseFacturaPO> list);
    SolicitudAcuseFacturaPO saveFactura (SolicitudAcuseFacturaPO model);
    SolicitudAcuseFacturaPO getRequestByCustomer(Long idCustomer);
    SolicitudAcuseFacturaPO getRequestById(Long idReceipt);
    void resetStatus(Long id);
}
