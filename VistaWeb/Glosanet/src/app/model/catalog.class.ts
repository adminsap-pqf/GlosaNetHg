import {ContentCatalog} from "./contentcatalog.class";

export class Catalog {
    id              : number;
    nombreCatalogo  : string;
    descripcion     : string;
    contenido       : ContentCatalog[];

    constructor() {
        this.id = 0;
        this.nombreCatalogo = '';
        this.descripcion = '';
        this.contenido = [];
    }
}
