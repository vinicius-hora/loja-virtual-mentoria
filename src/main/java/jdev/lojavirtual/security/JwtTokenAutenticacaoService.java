package jdev.lojavirtual.security;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//criar autenticacao e retornar autenticacao jwt
@Service
@Component
public class JwtTokenAutenticacaoService {

    private static final long EXPIRATION_TIME = 959990000; // 11 dias

    //chave secreta para o jwt
    private static final String SECRET = "jdev.lojavirtual.secret";

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    //gerar token e dar resposta
    public void addAuthentication(HttpServletResponse response, String username) throws Exception {
        //montagem do token
        String JWT = Jwts.builder() //chama o gerador de token
            .setSubject(username)// adiciona o user
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //tempo de expiração
            .signWith(SignatureAlgorithm.ES512, SECRET)
            .compact();

        String token = TOKEN_PREFIX +" "+JWT;

        //da a resposta para a tela
        response.addHeader(HEADER_STRING, token);

        response.getWriter().write("{\"Authorization\":\""+token+"\"}");
    }
        

    
}
