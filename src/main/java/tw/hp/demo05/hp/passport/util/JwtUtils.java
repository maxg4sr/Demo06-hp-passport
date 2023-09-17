package tw.hp.demo05.hp.passport.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具類
 */
public class JwtUtils {

    /**
     * 簽名密鑰
     */
    private static final String SECRET_KEY = "qwerdf";

    /**
     * 過期時間：以分鐘為單位
     */
    private static final long EXPIRED_IN_MINUTE = 30L * 60 * 24 * 60 * 1000;

    /**
     * 私有化構造
     */
    private JwtUtils() {
    }

    /**
     * 生成JWT
     *
     * @param claims 希望在JWT中封裝的數據
     * @return JWT字符串
     */
    public static String generate(Map<String, Object> claims) {

        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRED_IN_MINUTE * 60 * 1000);

        String jwt = Jwts.builder()
                .setHeaderParam("typ", "jwt")
                .setHeaderParam("alg", "HS256")
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();// 打包

        return jwt;
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT數據
     * @return 解析等到的Claims，其中封裝了生成JWT時所存入的數據
     */
    public static Claims parse(String jwt) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
    }

}