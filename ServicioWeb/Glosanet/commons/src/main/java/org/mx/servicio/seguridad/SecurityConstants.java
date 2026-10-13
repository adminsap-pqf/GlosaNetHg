package org.mx.servicio.seguridad;

public final class SecurityConstants {
    public static final String AUTH_LOGIN_URL = "/GlosaNet/inicio/authenticate";
    public static final String JWT_SECRET = "GlosaNet-Ryndem-GlosaNet-Ryndem-GlosaNet-Ryndem";

    // JWT token defaults
    public static final String HEADER_AUTHORIZACION_KEY = "x-auth-token";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
