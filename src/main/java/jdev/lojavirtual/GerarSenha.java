package jdev.lojavirtual;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarSenha {
    public static void main(String[] args) {
        String senha = "123456";
        System.out.println("senha: " + new BCryptPasswordEncoder().encode(senha));
    }
    
}
