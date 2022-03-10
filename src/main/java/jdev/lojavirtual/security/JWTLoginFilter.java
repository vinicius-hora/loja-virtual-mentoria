package jdev.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jdev.lojavirtual.model.Usuario;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter{

    //configurando o gerenciador de autenticação
   public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
        //obriga a autenticar a url
        super(new AntPathRequestMatcher(url));
        //gerenciador de autenticação
        setAuthenticationManager(authenticationManager);
    }

    protected JWTLoginFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
        //TODO Auto-generated constructor stub
    }
    //retor o usuário processado na autenticação
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        //obtem usuario
        Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);        
        //retorna o usuario  com login e senha
        return getAuthenticationManager()
        .authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        
        try {
            new JwtTokenAutenticacaoService().addAuthentication(response, authResult.getName());
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }
    
}
