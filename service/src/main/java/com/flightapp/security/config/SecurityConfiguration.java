package com.flightapp.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/home/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                )
                .permitAll()
                /*.requestMatchers("/operations/**").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/operations/{id}").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/operations/all").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())
                .requestMatchers(POST, "/operations/create").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/operations/change").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/operations/{id}/changeName").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/operations/{id}/changeDescription").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/operations/{id}/changePrice").hasAnyRole(ADMIN.name())
                .requestMatchers(DELETE, "/operations/{id}/delete").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/operations/{id}/restore").hasAnyRole(ADMIN.name())

                .requestMatchers("/user/**").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/user/{id}").hasAnyRole(ADMIN.name(), DOCTOR.name())
                .requestMatchers(GET, "/user/allPatients").hasAnyRole(ADMIN.name(), DOCTOR.name())
                .requestMatchers(GET, "/user/allDoctors").hasAnyRole(ADMIN.name(), DOCTOR.name(), DOCTOR.name())
                .requestMatchers(GET, "/user/allAdmins").hasAnyRole(ADMIN.name())
                .requestMatchers(POST, "/user/createDoctor").hasAnyRole(ADMIN.name())
                .requestMatchers(POST, "/user/createAdmin").hasAnyRole(ADMIN.name())

                .requestMatchers("/appointments/**").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/appointments/{id}").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/appointments/allUncompleted/{patientId}").hasAnyRole(ADMIN_READ.name(), DOCTOR.name())
                .requestMatchers(GET, "/appointments/allUnpaid/{patientId}").hasAnyRole(ADMIN.name(), DOCTOR.name())
                .requestMatchers(GET, "/appointments/allMyAppointments").hasAnyRole(DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/appointments/allMyCompleted").hasAnyRole(DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/appointments/allMyUncompleted").hasAnyRole(DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/appointments/allMyPaid").hasAnyRole(DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/appointments/allMyUnpaid").hasAnyRole(DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/appointments/all/{date}").hasAnyRole(DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/appointments/allMylNext").hasAnyRole(DOCTOR.name(), PATIENT.name())
                .requestMatchers(POST, "/appointments/createByPatient").hasAnyRole(PATIENT.name())
                .requestMatchers(POST, "/appointments/createByDoctor").hasAnyRole(DOCTOR.name())
                .requestMatchers(PUT, "/appointments/{id}/changeDate").hasAnyRole(DOCTOR.name())
                .requestMatchers(PUT, "/appointments/{id}/changeCompletionStatus").hasAnyRole(DOCTOR.name())
                .requestMatchers(PUT, "/appointments/{id}/changePaymentStatus").hasAnyRole(DOCTOR.name())
                .requestMatchers(DELETE, "/appointments/{id}/delete").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())

                .requestMatchers("/category/**").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/category/{id}").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/category/all").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())
                .requestMatchers(POST, "/category/create").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/category/{id}/change").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/category/{id}/changeName").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/category/{id}/changeDiscountPercentage").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/category/{id}/delete").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/category/{id}/restore").hasAnyRole(ADMIN.name())

                .requestMatchers("/offers/**").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/offers/{id}").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())
                .requestMatchers(GET, "/offers/all").hasAnyRole(ADMIN.name(), DOCTOR.name(), PATIENT.name())
                .requestMatchers(POST, "/offers/create").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/offers/{id}/changeName").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/offers/{id}/changeDate").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/offers/{id}/changePrice").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/offers/{id}/addOperations").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/offers/{offerId}/removeOperation/{operationId}").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/offers/{id}/addCategories").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/offers/{offerId}/removeCategory/{categoryId}").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/offers/{id}/delete").hasAnyRole(ADMIN.name())
                .requestMatchers(PUT, "/offers/{id}/restore").hasAnyRole(ADMIN.name())*/

                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/home/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;
        return http.build();
    }
}