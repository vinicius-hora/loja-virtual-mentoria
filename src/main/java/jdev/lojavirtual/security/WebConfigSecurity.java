package jdev.lojavirtual.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties.ConfigureAction;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jdev.lojavirtual.service.ImplementacaoUserDetailsService;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) 
public class WebConfigSecurity extends WebSecurityConfigurerAdapter
implements HttpSessionListener {

    @Autowired
    private ImplementacaoUserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // web.ignoring().antMatchers(HttpMethod.GET, "/salvarAcesso")
        // .antMatchers(HttpMethod.POST, "/salvarAcesso")
        // .antMatchers(HttpMethod.DELETE, "/salvarAcesso");
        //ignorando urls
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//   http.cors().and().antMatcher("/h2-console/").authorizeRequests()
        //   .antMatchers("**/h2-console/**").permitAll()
 		//   .antMatchers("/h2-console/**").permitAll();
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .disable().authorizeRequests().antMatchers("/").permitAll()
        .antMatchers("/index").permitAll()
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

        //redireciiona ou da retorno para o index
        .anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
        //mapeia o logout
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        //filtra as rquisições para login
        .and().addFilterAfter(new JWTLoginFilter("/login", authenticationManager()), 
        UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    //consultar user no banco com spring secutity
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
    }

    
  
}
