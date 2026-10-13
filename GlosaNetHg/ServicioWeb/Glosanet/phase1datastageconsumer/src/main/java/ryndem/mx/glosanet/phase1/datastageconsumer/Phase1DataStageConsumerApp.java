package ryndem.mx.glosanet.phase1.datastageconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "ryndem.mx.glosanet.phase1.datastageconsumer",
        "ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence",
        "org.mx.servicio.jobs.datastage.loader.phase1.processing.impl",
        "org.mx.servicio.descomprimir.impl",
        "org.mx.persistencia"})
@EnableJpaRepositories("ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence")
public class Phase1DataStageConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(Phase1DataStageConsumerApp.class, args);
    }
}
