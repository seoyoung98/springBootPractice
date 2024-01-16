package com.shinhan.sbproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
	
	// 빈을 넣어주면 security가 알아서 인코딩, 디코딩을 해준다.
	@Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
    // 로그인을 거치고 가지 않아도 들어갈 수 있는 아이들 
    private static final String[] WHITE_LIST = {
            "/auth/**", "/sample1","/v3/**", "/swagger-ui/**"
    };

    // USER인 사람만 들어갈 수 있는 곳
    private static final String[] USER_LIST = {
    		"/webboard/**","/sample2"
    };
    
    // ADMIN인 사람만 들어갈 수 있는 곳 
    private static final String[] ADMIN_LIST = {
            "/sample3"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
        .csrf(c -> c.disable())
            .cors(c -> c.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers(USER_LIST).hasRole("USER")
                        .requestMatchers(ADMIN_LIST).hasRole("ADMIN")
                        .anyRequest().authenticated();
                         
                    });
        http.formLogin(login -> login
                .loginPage("/auth/login")  //form 기반인증, 기본적으로 HTTPSession 이용 
//                .loginProcessingUrl("/auth/login") 
                // 알아서 default로 처리를 해준다. userDetailServcie를 implement받은 memberservice에 가서 loadUserByUsername을 불러라
                .defaultSuccessUrl("/auth/loginSuccess")
                .permitAll())
                .logout(out -> out
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                        .logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true))
                .exceptionHandling(handling -> handling.accessDeniedPage("/auth/accessDenined"));         
         return http.build();
    }

    
}