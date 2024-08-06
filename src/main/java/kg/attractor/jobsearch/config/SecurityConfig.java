package kg.attractor.jobsearch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DataSource dataSource;

    private static final String USER_QUERY =
            """
            SELECT EMAIL, PASSWORD, ENABLED
            FROM USERS
            WHERE EMAIL=?
            """;

    private static final String AUTHORITIES_QUERY =
            """
            select USERS.EMAIL, A.ROLE
            from USERS
            inner join ROLES UA on USERS.ID = UA.USER_ID
            inner join AUTHORITIES A on A.ID = UA.AUTHORITY_ID
            where EMAIL=?;
            """;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/applicants/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/upload-avatar").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/users/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/employers/vacancies/active").permitAll()
                        .requestMatchers(HttpMethod.GET, "/employers/vacancies/category/{category}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/employers/{employerId}").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/employers/download-avatar/{filename}").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/employers/resumes").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/employers/vacancies/{vacancyId}/apply").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/employers/upload-avatar").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/employers/vacancy").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/employers/{id}").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/employers/resumes/{id}").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/employers/{id}").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/employers/resumes/{id}").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/applicants/resumes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/applicants/{id}").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/applicants/vacancy/{vacancyId}/applicants").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/applicants/vacancies").permitAll()
                        .requestMatchers(HttpMethod.GET, "/applicants/vacancies/category/{category}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/applicants/get-user-resumes/{user_id}").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/applicants/resume/{id}").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/applicants/user/{userId}").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/applicants/resume").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/applicants/resume/{id}").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/applicants/resume/{id}").hasAnyAuthority("ADMIN", "USER")
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}