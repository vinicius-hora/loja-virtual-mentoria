package jdev.lojavirtual;

import jdev.lojavirtual.util.ValidaCNPJ;
import jdev.lojavirtual.util.ValidaCPF;

public class testeCpfCnpj {

    public static void main(String[] args) {
       boolean isCnpj = ValidaCNPJ.isCNPJ("22.278.540/0001-74");

       System.out.println("Cnpj valido : " + isCnpj);

       boolean isCpf = ValidaCPF.isCPF("496.023.650-72");

        System.out.println("Cpf valido : " + isCpf);

    }
}
