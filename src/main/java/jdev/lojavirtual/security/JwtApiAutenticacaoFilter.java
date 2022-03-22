package jdev.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/*Filtro onde todas as requisicoes serão capturadas para autenticar*/
public class JwtApiAutenticacaoFilter extends GenericFilterBean {

	private static final Logger log = LoggerFactory.getLogger(JwtApiAutenticacaoFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		/*Estabele a autenticao do user*/
		try {
			Authentication authentication = new JwtTokenAutenticacaoService().
			getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);
			
			/*Coloca o processo de autenticacao para o spring secutiry*/
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			chain.doFilter(request, response);
		} catch (Exception e) {
			log.error("Erro: {}, causa: {}", e.getMessage(), e.getCause());
			response.getWriter().write("Erro interno no sistema: " + e.getMessage());
		}
		
		
	}
	
	
	

}
