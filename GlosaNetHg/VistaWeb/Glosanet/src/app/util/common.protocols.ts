export const ROUTE_FILES = 'https://glosanet.ryndem.mx/GlosaDocs';
export const ROUTE_DATASTAGES = 'https://glosanet.ryndem.mx/Datastages/';
export const APP_VERSION_NUMBER = 'Versión: 2.0.1009.0';
export const PAGING_LIMIT = 25;

/* RABBITMQ JOB STATES */
export const RABBITMQ_JOB_STATE_NONE = -1;
export const RABBITMQ_JOB_STATE_RECEIVED = 0;
export const RABBITMQ_JOB_STATE_PROCESSING = 1;
export const RABBITMQ_JOB_STATE_FAILURE = 2;
export const RABBITMQ_JOB_STATE_SUCCESS = 3;


export const LABEL_STATE_NONE = 'N/A';
export const LABEL_STATE_RECEIVED = 'En espera';
export const LABEL_STATE_PROCESSING = 'Descargando';
export const LABEL_STATE_FAILURE = 'Fallo';
export const LABEL_STATE_SUCCESS = 'Mostrar';

export const TIMER_VUCEM = 10000; // TODO: Tiempo en milisegundos

const today = new Date();

/* Fecha de inicio y fin para validar los eventos de Previsión */
export const START_DATE_PREVISION = new Date(today.getFullYear() + 1, 0, 1);
export const END_DATE_PREVISION = new Date(today.getFullYear() + 1, 4, 31);
export const START_DATE_PREVISION_CURRENT = new Date(today.getFullYear(), 0, 1);
export const END_DATE_PREVISION_CURRENT = new Date(today.getFullYear(), 4, 31);
