package com.my.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Objects;

/**
 * @author hutf
 * @createTime 2022年07月16日 14:34:00
 */
//@Component
public class CustomPasswordEncoder   {
//    @Override
//    public String encode(CharSequence charSequence) {
//        String md5Password = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
//        return md5Password;
//    }
//
//    @Override
//    public boolean matches(CharSequence charSequence, String s) {
//        System.out.println(charSequence);
//        System.out.println(s);
////        return StringU;
//        String value1 = this.encode(charSequence);
//        String value2 = this.encode(s);
//        return Objects.equals(value1, value2);
//    }
//
//    @Override
//    public boolean upgradeEncoding(String encodedPassword) {
//        return false;
//    }
}
