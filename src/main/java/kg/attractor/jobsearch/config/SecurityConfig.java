package kg.attractor.jobsearch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/swagger-ui/").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                                .requestMatchers("/avatars/").permitAll()
                                .requestMatchers("/static/").permitAll()
                                .requestMatchers(HttpMethod.GET, "/auth/profile").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/applicants/register").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/users/upload-avatar").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.GET, "/users/{id}").hasAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/users/").hasAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/users").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/employers/vacancies/active").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/employers/vacancies/category/{category}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/employers/{employerId}").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.GET, "/employers/download-avatar/{filename}").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.POST, "/employers/resumes").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.POST, "/employers/vacancies/{vacancyId}/apply").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.POST, "/employers/upload-avatar").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.POST, "/employers/vacancy").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.PUT, "/employers/{id}").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.PUT, "/employers/resumes/{id}").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.DELETE, "/employers/{id}").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.DELETE, "/employers/resumes/{id}").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.GET, "/applicants/resumes").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/applicants/{id}").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.GET, "/applicants/vacancy/{vacancyId}/applicants").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.GET, "/applicants/vacancies").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/applicants/vacancies/category/{category}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/applicants/get-user-resumes/{user_id}").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.GET, "/applicants/resume/{id}").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.GET, "/applicants/user/{userId}").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.POST, "/applicants/resume").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.PUT, "/applicants/resume/{id}").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.DELETE, "/applicants/resume/{id}").hasAnyAuthority("ADMIN", "USER")
                                .anyRequest().permitAll()
                );
        return http.build();
    }
}