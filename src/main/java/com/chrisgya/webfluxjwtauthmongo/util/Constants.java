package com.chrisgya.webfluxjwtauthmongo.util;

public class Constants {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "ThisIsSecretForJWTHS512SignatureAlgorithmThatMUSTHave64ByteLength";
    public static final String AUTHORITIES_KEY = "scopes";
}
