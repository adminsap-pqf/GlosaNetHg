

export class UsuarioPermisos {

    idUsuarioPermiso: number;
	idUsuario: number;
	permiso: any;
    activo: boolean;
    
    constructor(){
        this.idUsuarioPermiso = 0;
        this.idUsuario = 0;
        this.permiso = null;
        this.activo = false;
    }

    reset(){
        this.idUsuarioPermiso = 0;
        this.idUsuario = 0;
        this.permiso = null;
        this.activo = false;
    }
}