package org.example.lab07_20192434.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.sql.DataSource;
@Configuration
public class WebSecurityConfig {
    final DataSource dataSource;

    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Bean para la codificación de contraseñas usando BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración de UserDetailManager para obtener usuarios desde la base de datos
    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        // Consulta para loguearse, obteniendo el email y la contraseña del usuario
        String sql1 = "SELECT email, password, enabled FROM usuario WHERE email = ?";
        // Consulta para obtener las autorizaciones (roles) del usuario
        String sql2 = "SELECT u.email, r.nombre FROM usuario u " +
                "INNER JOIN roles r ON u.roleId = r.id " +
                "WHERE u.email = ?";
        users.setUsersByUsernameQuery(sql1);
        users.setAuthoritiesByUsernameQuery(sql2);
        return users;
    }

    // Configuración del SecurityFilterChain para el manejo de la seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        // Permitir acceso a recursos estáticos
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/libs/**", "/loginForm/*").permitAll()


                        // Permisos basados en roles
                        .requestMatchers("/funciones/**").hasAuthority("admin") // Solo admin puede gestionar funciones
                        .requestMatchers("/salas/**", "/obras/**").hasAuthority("gerente") // Solo gerente puede gestionar salas y obras
                        .requestMatchers("/reservas/**").hasAnyAuthority("admin", "cliente") // Admin y cliente pueden ver y gestionar reservas
                        .requestMatchers("/obras/**").hasAnyAuthority("cliente", "gerente", "admin") // Todos pueden ver las obras

                        .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
                )

                // Configuración del login
                .formLogin(form -> form
                        .loginPage("/loginForm")
                        .loginProcessingUrl("/loginForm")
                        .defaultSuccessUrl("/", true) // Redirige al home después de login exitoso
                        .failureUrl("/loginForm?error=true") // Redirige a login con error si falla
                )

                // Configuración del logout
                .logout(logout -> logout
                        .logoutSuccessUrl("/loginForm?logout=true") // Redirige a la página de login después de cerrar sesión
                        .deleteCookies("JSESSIONID") // Borra cookies de sesión
                        .invalidateHttpSession(true) // Invalida la sesión actual
                ); // Puedes habilitar o deshabilitar CSRF según tu necesidad

        return http.build();
    }
}
