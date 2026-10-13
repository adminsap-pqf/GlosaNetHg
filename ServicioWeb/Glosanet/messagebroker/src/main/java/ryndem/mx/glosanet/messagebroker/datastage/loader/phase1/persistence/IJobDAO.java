package ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.po.JobPO;

import java.util.Optional;

public interface IJobDAO extends JpaRepository<JobPO, Integer> {

    /**
     * @param GUID
     * @return
     */
    Optional<JobPO> findByGUID(String GUID);
}
