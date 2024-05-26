package org.weathersensor.SpringRESTWeatherSensor.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.weathersensor.SpringRESTWeatherSensor.services.OperatorsService;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
public class SecurityConfig {

//    private final CustomAuthenticationProviderImpl authenticationProvider;
    private final OperatorsService operatorsService;
    private final JWTRequestFilter jwtRequestFilter;

//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                    .antMatchers("/auth/login", "/error").permitAll()
//                    .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                    .loginPage("/auth/login")
//                    .loginProcessingUrl("/process-login")
//                    .defaultSuccessUrl("/operators")
//                    .failureUrl("/auth/login");
//    }

    //настраиваем аутентификацию
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//
//        auth.userDetailsService(operatorsService)
//                .passwordEncoder(passwordEncoder());
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/secured").authenticated()
                .antMatchers("/info").authenticated()
//                .antMatchers("/measurements").authenticated()
                .antMatchers("/measurements").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(operatorsService);

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

//TODO:
// Циклическая зависимость возникает из-за того, что UserService зависит от PasswordEncoder,
// а SecurityConfig зависит от UserService.
// Проще всего делегировать создание Bean Definition на ioc контейнер
// и создать отдельную конфигурацию для PasswordEncoder,
// вынести PasswordEncoder-bean в отдельный класс PasswordEncoderConfiguration.


//Слушайте, вопрос такой. С какой-то версии спринга поменяли принцип описания
//        SecurityFilterChain. Теперь тот метод через .cors().disable()
//        и .csrf().disable() не совсем валиден. Подскажите, как выкрутиться?
//
//@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.configurationSource(request -> {
//                    var corsConfiguration = new CorsConfiguration();
//                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
//                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                    corsConfiguration.setAllowedHeaders(List.of("*"));
//                    corsConfiguration.setAllowCredentials(true);
//                    return corsConfiguration;
//                }))
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/user/log").permitAll()
//                        .requestMatchers("/user/logpost").permitAll()
//                        .requestMatchers("/user/reg").permitAll()
//                        .requestMatchers("/").hasAnyRole("ADMIN","USER")
//                        .requestMatchers("/user").permitAll()
//                        .requestMatchers("/admin").hasRole("ADMIN")
//                        .anyRequest().authenticated())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                )
//
//                .httpBasic(Customizer.withDefaults())
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();



//    @EnableGlobalMethodSecurity уже устарел, вместо него нужно воспользоваться аннотацией  @EnableMethodSecurity, csrf тоже стал deprecated, поэтому заменим его лямбда выражение. Потом еще устарели методы authorizeRequests() и antMatchers() и and(). Итоговый код выглядит вот так :
//public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//        .csrf(AbstractHttpConfigurer::disable)
//        .cors(AbstractHttpConfigurer::disable)
//        .authorizeHttpRequests(authz -> authz
//        .requestMatchers("/secured").authenticated()
//        .requestMatchers("/info").authenticated()
//        .requestMatchers("/admin").hasRole("ADMIN")
//        .anyRequest().permitAll())
//        .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//        .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
//        //addFilterBefore()
//        return http.build();