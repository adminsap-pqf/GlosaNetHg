export class ContentCatalog {
    posicion            : number;
    claveReporte        : string;
    descripcionBreve    : string;
    descripcionExtensa  : string;
    tooltip             : string;
    predeterminado      : boolean;
    fk_Catalogo         : number;
    nombre              : string;
    clave               : string;
    id                  : number;

    constructor() {
        this.posicion = 0;
        this.claveReporte = '';
        this.descripcionBreve = '';
        this.descripcionExtensa = '';
        this.tooltip = '';
        this.predeterminado = false;
        this.fk_Catalogo = 0;
        this.nombre = '';
        this.clave = '';
        this.id = 0;
    }
}
