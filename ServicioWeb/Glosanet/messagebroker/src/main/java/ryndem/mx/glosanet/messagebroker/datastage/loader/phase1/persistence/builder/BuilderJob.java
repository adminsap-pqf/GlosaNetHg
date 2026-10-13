package ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.builder;

import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.bo.JobBO;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.po.JobPO;

public class BuilderJob {


    public static JobBO builderBO(JobPO persistenceObj) {

        JobBO businessObj = new JobBO();
        businessObj.setId(persistenceObj.getPK_Fase1RMQMensaje());
        businessObj.setGuid_job(persistenceObj.getGUID());
        businessObj.setStatus(persistenceObj.getStatus());
        businessObj.setIdDownload(persistenceObj.getFK01_Descarga());
        businessObj.setCreationDate(persistenceObj.getCreationDate());
        businessObj.setStackTrace(persistenceObj.getStackTrace());

        return businessObj;
    }

    public static JobPO builderPO(JobBO businessObj) {

        JobPO persistenceObj = new JobPO();
        persistenceObj.setPK_Fase1RMQMensaje(businessObj.getId());
        persistenceObj.setGUID(businessObj.getGuid_job());
        persistenceObj.setStatus(businessObj.getStatus());
        persistenceObj.setFK01_Descarga(businessObj.getIdDownload());
        persistenceObj.setCreationDate(businessObj.getCreationDate());
        persistenceObj.setStackTrace(businessObj.getStackTrace());

        return persistenceObj;
    }
}
