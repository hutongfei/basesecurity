package com.my.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hutf
 * @createTime 2022年07月16日 13:25:00
 */
public class CustomAuthorityUtils {


    public static List<GrantedAuthority> convertToAuthor(String roles) {
        if (StringUtils.isEmpty(roles)) return null;
        List<GrantedAuthority> list = new ArrayList<>();
        String[] split = roles.split(",");

        CustomGrantedAuthority authority = null;
        for (String item : split) {
             authority = new CustomGrantedAuthority(item);
            list.add(authority);
        }
        return list;
    }


    static class CustomGrantedAuthority implements GrantedAuthority {
        private String roleCode;

        public CustomGrantedAuthority(String roleCode) {
            this.roleCode = roleCode;
        }

        @Override
        public String getAuthority() {
            return this.roleCode;
        }
    }
}
