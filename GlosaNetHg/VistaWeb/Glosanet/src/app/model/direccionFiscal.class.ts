export class DireccionFiscal {

  idDireccion: number;
  calle: string;
  noInterior: string;
  noExterior: string;
  colonia: string;
  municipio: string;
  estado: string;
  pais: string;
  codigoPostal: string;

  constructor() {
    this.idDireccion = 0;
    this.calle = '';
    this.noInterior = '';
    this.noExterior = '';
    this.colonia = '';
    this.municipio = '';
    this.estado = '';
    this.pais = '';
    this.codigoPostal = '';
  }

}
