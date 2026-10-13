
export class AuthenticatedUser {
  loginData: {
    token: string;
  };
  userInfo: object;
  adminData: object;
  catalogVucem: object;

  constructor() {
    this.loginData = null;
    this.userInfo = null;
    this.adminData = null;
    this.catalogVucem = null;
  }
}
