package kr.co.teamA.Haru.security.config;

import kr.co.teamA.Haru.security.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
<<<<<<< HEAD
    // JWT 토큰을 검증하는 필터

    @Value("${server-port-local-url}")
    private String serverPortLocalUrl;

    @Value("${server-port-url}")
    private String serverPortUrl;

    @Autowired
    private JwtTokenFilter jwtAuthenticationFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean // 사용자 인증을 위한 DaoAuthenticationProvider를 빈으로 등록
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // 사용자 정보를 제공하는 'userDetailService'
        provider.setPasswordEncoder(passwordEncoder()); // 비밀번호 인코딩 위한 passwordEncoder 설정
        return provider;
    }

    @Bean // 사용자 비밀번호 안전하게 저장하기 위해
=======

    // 로컬에서 실행시 포트번호
    @Value("${server-port-local-url}")
    private String serverPortLocalUrl;

    // 서버에서 실행시 포트번호
    @Value("${server-port-url}")
    private String serverPortUrl;

    // JWT 토큰 필터
    @Autowired
    private JwtTokenFilter jwtAuthenticationFilter;

    // 유저 정보
    @Autowired
    private UserDetailsService userDetailsService;

    // 유저 정보를 가져오는 메소드
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // 비밀번호 암호화
    @Bean
>>>>>>> yj_base
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

<<<<<<< HEAD
    @Bean // Spring Security의 AuthenticationManager를 빈으로 등록
    // 인증(authentication)을 관리
=======
    // 토큰 인증 매니저
    @Bean
>>>>>>> yj_base
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

<<<<<<< HEAD
=======
    // 보안 필터 체인
>>>>>>> yj_base
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
                .formLogin((login) -> login.disable())
                .httpBasic((basic) -> basic.disable())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout((logout) -> logout.disable());
        return http.build();
    }
<<<<<<< HEAD
=======
    // CORS 설정
>>>>>>> yj_base
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(List.of(serverPortLocalUrl,serverPortUrl));
        corsConfiguration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }
}