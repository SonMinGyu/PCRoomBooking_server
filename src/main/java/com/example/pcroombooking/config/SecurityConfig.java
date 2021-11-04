package com.example.pcroombooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// WebSecurityConfigurerAdapter 어떤 필터를 적용하여 필터체인을 구성할건지
//@Order(1) // 두개 이상의 security 필터를 사용할때 순서 정해주기
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser(User.builder().username("user2")
//                        .password(passwordEncoder().encode("1111"))
//                        .roles("USER"))
//                .withUser(User.builder().username("admin")
//                        .password(passwordEncoder().encode("2222"))
//                        .roles("ADMIN"));
    }

    // 유저 생성시 비밀번호 encoding
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // home 화면은 인증 필요없게

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/**") // 모든 url
//        http.authorizeRequests((requests) -> requests.antMatchers("/").permitAll()
//                .anyRequest().authenticated());

        http.csrf().disable()
                .authorizeRequests((requests) -> requests
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/user/register").permitAll()
                .antMatchers("/api/user/test").permitAll()
                .anyRequest().authenticated());

        //        http.formLogin();
//        http.httpBasic();
//
//        http.formLogin(login -> {
//            login.defaultSuccessUrl("/", false); // 로그인 성공시 어디로 이동동
//       });
    }


}
