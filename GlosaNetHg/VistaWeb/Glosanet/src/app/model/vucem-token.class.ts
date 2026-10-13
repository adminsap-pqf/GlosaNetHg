export class VucemTokenClass {
  public idToken: number;
  public token: string;
  public lastUpdateDate: Date;
  public activo: boolean;
  
  constructor() {
    this.idToken = 0;
    this.token = '';
    this.lastUpdateDate = new Date();
    this.activo = false;
  }
}
