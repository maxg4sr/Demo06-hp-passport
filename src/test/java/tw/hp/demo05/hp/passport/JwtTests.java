package tw.hp.demo05.hp.passport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JwtTests {

    @Test
    public void testGenerateJwt() { // 生成JWT

        // 需要封裝到JWT中的數據
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", "9527");
        claims.put("name", "唐伯虎");

        // 過期時間(10分鐘)
        Date expirationDate = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);

        // JWT組成部分:
        // (Header)頭
        //  用於配置算法與結果數據類型
        // (Payload)載荷
        //  用於配置需要封裝到JWT中的數據
        // (Signature)簽名
        //  用於指定算法與密鑰(鹽值)
        String jwt = Jwts.builder()
                // Header: typ-類型，alg-算法
                .setHeaderParam("typ", "jwt")
                .setHeaderParam("alg", "HS256")
                // Payload
                .setClaims(claims)
                .setExpiration(expirationDate)
                // Signature
                .signWith(SignatureAlgorithm.HS256, "qwerdf")
                .compact();// 打包

        System.out.println(jwt);
        // 頭.載荷.簽名
        // eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9
        // .
        // eyJuYW1lIjoi5ZSQ5Lyv6JmOIiwiaWQiOiI5NTI3IiwiZXhwIjoxNjgxMzA2NDc0fQ
        // .
        // QPbNW-aNzM3Mb50VD9KLgtzTAOZm5QAJESBGqA-rF_c
    }

    @Test
    public void testParseJwt() { // 解析JWT
        String jwt = "eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi5ZSQ5Lyv6JmOIiwiaWQiOiI5NTI3IiwiZXhwIjoxNjkzNTU0NjY4fQ.-NJCoaYczds_mpUM9uojI31K0Ee792ErZECO-8o_pNE";

        Claims claims = Jwts.parser().setSigningKey("qwerdf")
                .parseClaimsJws(jwt).getBody();
//        Object id = claims.get("id");
//        Object name = claims.get("name");
//        System.out.println("id= "+id);
//        System.out.println("name= "+name);

        Object username = claims.get("username");
        System.out.println("username= " + username);

    }


    @Test
    public void testDate() {
        Date now = new Date();
        System.out.println("當前時間：" + now);
        System.out.println("內部維護的long值：" + now.getTime());
        System.out.println(System.currentTimeMillis());

        Date date1 = new Date();
        date1.setTime(0);
        System.out.println("date1:" + date1);

        Date date2 = new Date();
        date2.setTime(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000);
        System.out.println("date2:" + date2);

        Date date3 = new Date(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000);
        System.out.println("date3:" + date3);
    }

}
