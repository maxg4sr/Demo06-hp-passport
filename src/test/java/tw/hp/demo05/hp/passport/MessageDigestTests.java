package tw.hp.demo05.hp.passport;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class MessageDigestTests {

    @Test
    public void test1MD5() {//一般密碼
        String rawPassword = "123456";
        String encodePassword = DigestUtils.md5DigestAsHex(rawPassword.getBytes());//加密結果用16進位算法顯示
        System.out.println("rawPassword:" + rawPassword);
        System.out.println("encodePassword:" + encodePassword);

    }

    @Test
    public void test2MD5() {
        String rawPassword = "123456";
        String salt = "abcd_";//加鹽值密碼
        String encodePassword = DigestUtils.md5DigestAsHex((salt + rawPassword + salt).getBytes());//加密結果用16進位算法顯示
        System.out.println("rawPassword:" + rawPassword);
        System.out.println("encodePassword:" + encodePassword);

    }

    //複雜密碼 加鹽 多重密碼
    @Test
    public void test3MD5() {
        String rawPassword = "123456";

        String encodePassword = DigestUtils.md5DigestAsHex(rawPassword.getBytes());//加密結果用16進位算法顯示
        String encodePassword1 = DigestUtils.md5DigestAsHex(encodePassword.getBytes());//加密結果用16進位算法顯示

        System.out.println("rawPassword:" + rawPassword);
        System.out.println("encodePassword1:" + encodePassword1);

    }

    @Test
    public void test4MD5() {//循環加密密碼
        String rawPassword = "123456";
        for (int i = 0; i < 5; i++) {

            String salt = UUID.randomUUID().toString();//隨機鹽值 - 需要紀錄密碼

            String encodePassword = DigestUtils.md5DigestAsHex((salt + rawPassword).getBytes());//加密結果用16進位算法顯示
            System.out.println("rawPassword:" + rawPassword);
            System.out.println("salt:" + salt);
            System.out.println("encodePassword:" + encodePassword);
            System.out.println();
        }
    }

    //BCryptPasswordEncoder  encode()-加密 matches()-驗證 算法使用
    @Test//encode()-加密
    public void testEncode() {
        String rawPassword = "123456";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(rawPassword);
        System.out.println("rawPassword:" + rawPassword);
        System.out.println("encodePassword:" + encodePassword);
//        rawPassword:123456
//        encodePassword:$2a$10$9njBTAwdlnFOfQLkyGLpVuNt1s6OtRfK7vykzxTkFAp7TWvjREhia
//        encodePassword:$2a$10$lmuRVrhJxMcb.tv9LT7TNeTDDjbEFGZJsRnYcnyQi.T5PioBX6fKK
    }

    @Test //matches()-驗證
    public void testMatches() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";
        String encodePassword = "$2a$10$9njBTAwdlnFOfQLkyGLpVuNt1s6OtRfK7vykzxTkFAp7TWvjREhia";
        boolean b = passwordEncoder.matches(rawPassword, encodePassword);
        System.out.println("驗證密碼結果:" + b);
    }


}

