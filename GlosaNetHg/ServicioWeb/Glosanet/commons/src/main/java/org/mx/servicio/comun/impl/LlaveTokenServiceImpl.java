package org.mx.servicio.comun.impl;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.servicio.comun.LlaveTokenService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.logging.Logger;

@Service("llaveTokenService")
public class LlaveTokenServiceImpl implements LlaveTokenService {
    private static final Logger log = Logger.getLogger(LlaveTokenServiceImpl.class.getName());

    @Override
    public String generarToken(UsuarioBO usuario) throws JoseException {
        try {

            String secret = "GlosaNet-Ryndem-GlosaNet-Ryndem-GlosaNet-Ryndem";
            Key key = new HmacKey(secret.getBytes("UTF-8"));

            // Create the Claims, which will be the content of the JWT
            JwtClaims claims = new JwtClaims();
            claims.setIssuer("Ryndem");  // who creates the token and signs it
            claims.setAudience("GlosaNet"); // to whom the token is intended to be sent

            claims.setExpirationTimeMinutesInTheFuture(118); // time when the token will expire (10 minutes from now)
            claims.setGeneratedJwtId(); // a unique identifier for the token
            claims.setIssuedAtToNow();  // when the token was issued/created (now)
            claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
            claims.setSubject("Login");
            claims.setStringClaim("idUsuario", String.valueOf(usuario.getIdUsuario()));
            claims.setStringClaim("usuario", usuario.getUsuario());
//			claims.setStringClaim("rol", usuario.getRol().getNombreES()); 
//			claims.setStringClaim("idRol", String.valueOf(usuario.getRol().getIdRol()));

            JsonWebSignature jws = new JsonWebSignature();

            // The payload of the JWS is JSON content of the JWT Claims
            jws.setPayload(claims.toJson());

            // The JWT is signed using the private key
            jws.setKey(key);
            jws.setDoKeyValidation(false); // relaxes the key length requirement

            // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

            // Sign the JWS and produce the compact serialization, which will be the inner JWT/JWS
            // representation, which is a string consisting of three dot ('.') separated
            // base64url-encoded parts in the form Header.Payload.Signature
            String innerJwt = jws.getCompactSerialization();

            return innerJwt;
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return "";
        }
    }

    @Override
    public UsuarioBO validacionToken(String token) throws JoseException {
        try {
            String secret = "GlosaNet-Ryndem-GlosaNet-Ryndem-GlosaNet-Ryndem";
            Key key = new HmacKey(secret.getBytes("UTF-8"));

            // Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
            // be used to validate and process the JWT.
            // The specific validation requirements for a JWT are context dependent, however,
            // it typically advisable to require a (reasonable) expiration time, a trusted issuer, and
            // and audience that identifies your system as the intended recipient.
            // If the JWT is encrypted too, you need only provide a decryption key or
            // decryption key resolver to the builder.
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime() // the JWT must have an expiration time
                    .setMaxFutureValidityInMinutes(120) // but the  expiration time can't be too crazy
                    .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                    .setRequireSubject() // the JWT must have a subject claim
                    .setExpectedIssuer("Ryndem") // whom the JWT needs to have been issued by
                    .setExpectedAudience("GlosaNet") // to whom the JWT is intended for
                    .setVerificationKey(key) // verify the signature with the public key
                    .build(); // create the JwtConsumer instance


            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
            log.info("Token autorizado: " + jwtClaims);
            UsuarioBO u = new UsuarioBO();
            //RolBO rol = new RolBO();

//			claims.setStringClaim("idUsuario", String.valueOf(usuario.getIdUsuario())); 
//			claims.setStringClaim("usuario", usuario.getUsuario()); 
//			claims.setStringClaim("rol", usuario.getRol().getNombreES()); 
//			claims.setStringClaim("idRol", String.valueOf(usuario.getRol().getIdRol()));

            u.setIdUsuario(Integer.parseInt((String) jwtClaims.getClaimValue("idUsuario")));
            u.setUsuario((String) jwtClaims.getClaimValue("usuario"));
            //rol.setNombreES((String) jwtClaims.getClaimValue("rol"));
            //rol.setIdRol(Integer.parseInt((String) jwtClaims.getClaimValue("idRol")));
//			u.setRol(rol);

            return u;
        } catch (InvalidJwtException | UnsupportedEncodingException e) {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            // Hopefully with meaningful explanations(s) about what went wrong.
            log.severe("Token inválido: " + e);
            return null;
        }

    }

}
