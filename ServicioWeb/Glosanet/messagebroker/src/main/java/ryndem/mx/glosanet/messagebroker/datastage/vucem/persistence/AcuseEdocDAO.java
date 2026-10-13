package ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence;

import ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po.SolicitudAcuseEdocPO;

import java.util.List;

public interface AcuseEdocDAO {
    List<SolicitudAcuseEdocPO> saveAcuseEdoc (List<SolicitudAcuseEdocPO> list);
    SolicitudAcuseEdocPO saveAcuseEdoc (SolicitudAcuseEdocPO model);
    SolicitudAcuseEdocPO getRequestByCustomer(Long idCustomer);
    SolicitudAcuseEdocPO getRequestById(Long idReceipt);
    void resetStatus(Long id);
}
