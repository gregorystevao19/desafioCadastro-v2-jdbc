package org.estudo.ui;

import org.estudo.domain.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    static Scanner input = new Scanner(System.in);

    public static void startMenu() {
        System.out.println("=================================");
        System.out.println("         LOJA DOS PETS          ");
        System.out.println("=================================");
        System.out.println("[1] Gerenciar Pet");
        System.out.println("[2] Sair do sistema");
        System.out.println("=================================");
        System.out.print("Escolha uma opção: ");
    }

    public static int handleUserStartMenuInput() {

        String userInput = input.nextLine();

        while (true) {
            try {
                int intUserIput = Integer.parseInt(userInput);
                boolean isValid = intUserIput >= 1 && intUserIput <= 2;
                if (isValid) {
                    return intUserIput;
                } else {
                    System.out.print("OPÇÃO NÃO DISPONÍVEL, INFORME NOVAMENTE: ");
                    userInput = input.nextLine();
                }
            } catch (RuntimeException e) {
                System.out.print("VALOR INVÁLIDO, INFORME NOVAMENTE: ");
                userInput = input.nextLine();
            }
        }
    }

    public static void gerenciarPetMenu() {
        System.out.println("======================================");
        System.out.println("         GERENCIAR PETS               ");
        System.out.println("======================================");
        System.out.println("[1] Listar todos os pets");
        System.out.println("[2] Listar pets por filtro");
        System.out.println("[3] Cadastrar pet");
        System.out.println("[4] Atualizar dados do pet");
        System.out.println("[5] Excluir pet");
        System.out.println("[6] Voltar ao menu principal");
        System.out.println("[7] Sair do sistema");
        System.out.println("======================================");
        System.out.print("Escolha uma opção: ");
    }

    public static int handleUserGerenciarPetMenuInput() {

        String userInput = input.nextLine();

        while (true) {
            try {
                int intUserIput = Integer.parseInt(userInput);
                boolean isValid = intUserIput >= 1 && intUserIput <= 7;
                if (isValid) {
                    return intUserIput;
                } else {
                    System.out.print("OPÇÃO NÃO DISPONÍVEL, INFORME NOVAMENTE: ");
                    userInput = input.nextLine();
                }
            } catch (RuntimeException e) {
                System.out.print("VALOR INVÁLIDO, INFORME NOVAMENTE: ");
                userInput = input.nextLine();
            }
        }
    }

    public static void listAllPets(ResultSet pets) throws SQLException {

        List<Pet> allPets = new ArrayList<>();

        while (pets.next()) {
            String nome = pets.getString("nome");
            String tipo = pets.getString("tipo");
            String sexo = pets.getString("sexo");
            String endereco = pets.getString("endereco");
            double idade = pets.getDouble("idade");
            double peso = pets.getDouble("peso");
            String raca = pets.getString("raca");

            NomePet n = new NomePet(nome.split(" ")[0], nome.split(" ")[1]);
            Endereco en = new Endereco(endereco.split(", ")[0], endereco.split(", ")[1], Integer.parseInt(endereco.split(", ")[2]));

            Pet p = new Pet(n, TipoPet.valueOf(tipo), SexoPet.valueOf(sexo), en, idade, peso, raca);
            allPets.add(p);
        }

        System.out.println("════════════════════════════════════════════");
        System.out.println("           PETS ENCONTRADOS                 ");
        System.out.println("════════════════════════════════════════════");

        allPets.forEach(p -> {
            System.out.println("┌──────────────────────────────────────────┐");
            System.out.printf(" Nome: %s %s%n",
                    p.getNome().getPrimeiroNome(),
                    p.getNome().getSobrenome());

            System.out.printf(" Tipo: %s%n", p.getTipo());
            System.out.printf(" Sexo: %s%n", p.getSexo());
            System.out.printf(" Raça: %s%n", p.getRaca());
            System.out.printf(" Idade: %.1f anos%n", p.getIdade());
            System.out.printf(" Peso: %.2f kg%n", p.getPeso());

            System.out.printf(
                    " Endereço: %s, %s, Nº %d%n",
                    p.getEndereco().getCidade(),
                    p.getEndereco().getRua(),
                    p.getEndereco().getNumeroCasa()
            );

            System.out.println("└──────────────────────────────────────────┘");
        });

        System.out.println("════════════════════════════════════════════");
    }

    public static List<String> petsFiltradosMenu() {
        System.out.println("======================================");
        System.out.println("         FILTRAR PETS                 ");
        System.out.println("======================================");
        System.out.print("INFORME AS PALAVRAS CHAVES (SEPARADAS POR ESPAÇO): ");

        List<String> filtros = new ArrayList<>();
        String keyWords = input.nextLine();
        for (String p: keyWords.split((" "))){
            filtros.add(p);
        }
        return filtros;
    }
}
