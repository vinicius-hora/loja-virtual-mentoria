package jdev.lojavirtual.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdev.lojavirtual.Context.ApplicationContextLoad;
import jdev.lojavirtual.model.Usuario;
import jdev.lojavirtual.repository.UsuarioRepository;

//criar autenticacao e retornar autenticacao jwt
@Service
@Component
public class JwtTokenAutenticacaoService {

    private static final long EXPIRATION_TIME = 959990000; // 11 dias

    //chave secreta para o jwt
    private static final String SECRET = "secreto";

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    //gerar token e dar resposta
    public void addAuthentication(HttpServletResponse response, String username) throws Exception {
        //montagem do token
        String JWT = Jwts.builder() //chama o gerador de token
            .setSubject(username)// adiciona o user
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //tempo de expiração
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();

        String token = TOKEN_PREFIX +" "+JWT;

        //da a resposta para a tela
        response.addHeader(HEADER_STRING, token);

        liberacaoCors(response);

        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }

    //retorna usuario validado pelo token
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response){

        String token = request.getHeader(HEADER_STRING);

        if(token != null) {
            
            String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
            //FAZER VERIFICAÇÃO DO TOKEN e obtem o usuario
            String user = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(tokenLimpo)
            .getBody()
            .getSubject();
            
            if(user != null){
               Usuario usuario = ApplicationContextLoad.getApplicationContext()
               .getBean(UsuarioRepository.class).findUserbyLogin(user);

               if(usuario != null){
                   return new UsernamePasswordAuthenticationToken(
                        usuario.getLogin(), 
                        usuario.getSenha(), 
                        usuario.getAuthorities()
                    );
                   
               }
            }
        }

        return null;

    }

    //liberação de cors
    private void liberacaoCors(HttpServletResponse response) {
        // response.addHeader("Access-Control-Allow-Origin", "*");
        // response.addHeader("Access-Control-Allow-Credentials", "true");
        // response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // response.addHeader("Access-Control-Max-Age", "3600");
        // response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        if(response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        if(response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }
        if(response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }
        if(response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }
       
    }
        

    
}
