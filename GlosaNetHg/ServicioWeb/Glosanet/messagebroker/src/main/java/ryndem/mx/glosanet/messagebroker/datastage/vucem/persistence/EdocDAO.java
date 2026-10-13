package ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence;

import ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po.SolicitudEdocPO;

import java.util.List;

public interface EdocDAO {
    List<SolicitudEdocPO> saveEdoc (List<SolicitudEdocPO> list);
    SolicitudEdocPO saveEdoc (SolicitudEdocPO model);
    SolicitudEdocPO getRequestByCustomer(Long idCustomer);
    SolicitudEdocPO getRequestById(Long idReceipt);
    void resetStatus(Long id);
}
