package com.my.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author hutf
 * @createTime 2021年07月27日 23:08:00
 */

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private SecurityWhitelistHandler whitelistHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(this.whiteUrl()).permitAll()
                .antMatchers("/admin/api/**").hasRole("ADMIN").
                antMatchers("/user/api/**").hasRole("USER").
                anyRequest().authenticated().
                and()
                .formLogin()
                .defaultSuccessUrl("/index")
                .permitAll()
                .loginPage("/login/index.html")
                .permitAll()
                .and()
                .csrf().disable();
    }

    @Qualifier("sysUserDetailsService")
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

//    @Bean
//    public  static PasswordEncoder passwordEncoder( ){
//        DelegatingPasswordEncoder delegatingPasswordEncoder =
//                (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
////设置defaultPasswordEncoderForMatches为NoOpPasswordEncoder
//        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
//        return  delegatingPasswordEncoder;
//    }


    // 白名单路径
    private String[] whiteUrl() {
        return new String[]{
                "/login",
                "/login/index.html"
        };
    }
}
