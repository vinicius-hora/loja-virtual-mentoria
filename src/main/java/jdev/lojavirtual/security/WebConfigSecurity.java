package jdev.lojavirtual.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        web.ignoring().antMatchers(HttpMethod.GET, "/salvarAcesso")
        .antMatchers(HttpMethod.POST, "/salvarAcesso")
        .antMatchers(HttpMethod.DELETE, "/salvarAcesso");
        //ignorando urls
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	  http.cors().and().antMatcher("/h2-console/").authorizeRequests()
          .antMatchers("**/h2-console/**").permitAll()
 		  .antMatchers("/h2-console/**").permitAll();
    }
    //consultar user no banco com spring secutity
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
    }

  
}
