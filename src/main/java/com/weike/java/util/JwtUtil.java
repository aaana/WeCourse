package com.weike.java.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tina on 3/17/17.
 */
public class JwtUtil {
    private String JWT_SECRET = "day1day2study3and4up5";

    /**
     * 由字符串生成加密key
     */
    public SecretKey generalKey(){
        String stringKey = JWT_SECRET;
        byte[] encodedKey = DatatypeConverter.parseBase64Binary(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     */
    public String createJWT(String subject, long ttlMillis) throws Exception {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        SecretKey key = generalKey();

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    /**
     * 解密jwt
     */
    public Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();

        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();

        return claims;
    }

    /**
     * 生成subject信息
     */
    public String generalSubject(Map<String,Object> map){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 恢复subject信息
     */
    public Map<String,Object> translateSubject(Claims claims) throws IOException {

        String subject = claims.getSubject();
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(subject, Map.class);

        return map;
    }
}
