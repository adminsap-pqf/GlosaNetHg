package ryndem.mx.glosanet.messagebroker.config;

public class Protocols {

    /* GLOBALES */
    public static final String RABBIT_CONN_FACTORY = "rabbitConnFactory";

    /* CARGA DE DATASTAGE FASE 1 */
    public static final String PHASE1_RABBIT_CONFIG_NAME = "phase1-rabbitconfig";
    /*Sobre el exchange: se puede usar para asociar una cola y enrutar el mensaje<,
    por lo pronto omitiremos este valor y usaremos el default exchange provisto por rabbitmq*/
    //public static final String PHASE1_DIRECT_EXCHANGE_NAME = "datastages-phase1-exchange";
    public static final String PHASE1_QUEUE_NAME = "datastages-phase1-queue";
    public static final String PHASE1_JOB_DAO_NAME = "phase1-job-dao";
    public static final String PHASE1_MESSAGES_TABLE = "GN_Fase1RMQMensaje";

    /* SOLICITUDES DE DOCUMENTOS VUCEM */
    public static final String VUCEM_RABBIT_CONFIG_NAME = "vucem-rabbitconfig";
    /* ACUSE COVE */
    public static final String VUCEM_ACUSE_COVE_QUEUE_NAME = "vucem-acusecove-docrequest-queue";
    public static final String VUCEM_ACUSE_COVE_JOB_DAO_NAME = "vucem-acusecove-job-dao";
    public static final String VUCEM_ACUSE_COVE_MESSAGES_TABLE = "GN_Vucem_AcuseCove";

    /* EDOC */
    public static final String VUCEM_EDOC_QUEUE_NAME = "vucem-edoc-docrequest-queue";
    public static final String VUCEM_EDOC_JOB_DAO_NAME = "vucem-edoc-job-dao";
    public static final String VUCEM_EDOC_MESSAGES_TABLE = "GN_Vucem_Edoc";

    /* ACUSE EDOC */
    public static final String VUCEM_ACUSE_EDOC_QUEUE_NAME = "vucem-acuseedoc-docrequest-queue";
    public static final String VUCEM_ACUSE_EDOC_JOB_DAO_NAME = "vucem-acuseedoc-job-dao";
    public static final String VUCEM_ACUSE_EDOC_MESSAGES_TABLE = "GN_Vucem_AcuseEdoc";

    /* RABBITMQ JOB STATES */
    public static final int RABBITMQ_JOB_STATE_RECEIVED = 0;
    public static final int RABBITMQ_JOB_STATE_PROCESSING = 1;
    public static final int RABBITMQ_JOB_STATE_FAILURE = 2;
    public static final int RABBITMQ_JOB_STATE_SUCCESS = 3;

}
