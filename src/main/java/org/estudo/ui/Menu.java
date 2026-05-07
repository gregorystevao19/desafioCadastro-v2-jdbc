package org.estudo.ui;

import org.estudo.dao.PerguntaDAO;
import org.estudo.dao.PetDAO;
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
            int id = pets.getInt("id");
            String nome = pets.getString("nome");
            String tipo = pets.getString("tipo");
            String sexo = pets.getString("sexo");
            String endereco = pets.getString("endereco");
            double idade = pets.getDouble("idade");
            double peso = pets.getDouble("peso");
            String raca = pets.getString("raca");

            NomePet n = new NomePet(nome.split(" ")[0], nome.split(" ")[1]);
            Endereco en = new Endereco(endereco.split(", ")[0], endereco.split(", ")[1], Integer.parseInt(endereco.split(", ")[2]));

            Pet p = new Pet(id, n, TipoPet.valueOf(tipo), SexoPet.valueOf(sexo), en, idade, peso, raca);
            allPets.add(p);
        }

        System.out.println("════════════════════════════════════════════");
        System.out.println("           PETS ENCONTRADOS                 ");
        System.out.println("════════════════════════════════════════════");

        allPets.forEach(p -> {
            System.out.println("┌──────────────────────────────────────────┐");
            System.out.printf(" ID DO PET NA LISTAGEM: [%d]%n", p.getId());
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
        for (String p : keyWords.split((" "))) {
            filtros.add(p);
        }
        return filtros;
    }

    public static void handlePetCadastro(ResultSet perguntas) throws SQLException {
        List<Pergunta> allPerguntas = new ArrayList<>();

        while (perguntas.next()) {
            String descricao = perguntas.getString("descricao");
            boolean isEditavel = perguntas.getBoolean("editavel");

            Pergunta p = new Pergunta(descricao, isEditavel);
            allPerguntas.add(p);
        }

        Pet p = new Pet();
        for (int contador = 1; contador <= allPerguntas.size(); contador++) {

            System.out.print(allPerguntas.get(contador - 1).getDescricao() + ": ");
            switch (contador) {
                case 1 -> {
                    String nome = input.nextLine();
                    if (nome.trim().isEmpty()) {
                        p.setNome(null);
                    } else {
                        while (nome.split(" ").length != 2) {
                            System.out.print("O NOME DEVE CONTER NOME E SOBRENOME DO PET, SEPARADOS POR ESPAÇO: ");
                            nome = input.nextLine();
                        }
                        NomePet n = new NomePet(nome.split(" ")[0], nome.split(" ")[1]);
                        p.setNome(n);
                    }
                }
                case 2 -> {
                    String tipo = input.nextLine();
                    while (true) {
                        try {
                            TipoPet tipoPet = TipoPet.valueOf(tipo.toUpperCase());
                            break;

                        } catch (IllegalArgumentException e) {
                            System.out.print("INFORME UM TIPO DE PET VÁLIDO (CACHORRO / GATO): ");
                            tipo = input.nextLine();
                        }
                    }
                    p.setTipo(TipoPet.valueOf(tipo.toUpperCase()));
                }
                case 3 -> {
                    String sexo = input.nextLine();
                    while (true) {
                        try {
                            SexoPet sexoPet = SexoPet.valueOf(sexo.toUpperCase());
                            break;

                        } catch (IllegalArgumentException e) {
                            System.out.print("INFORME UM SEXO VÁLIDO (MACHO / FEMEA): ");
                            sexo = input.nextLine();
                        }
                    }
                    p.setSexo(SexoPet.valueOf(sexo.toUpperCase()));
                }
                case 4 -> {
                    System.out.print("\nCIDADE: ");
                    String cidade = input.nextLine();

                    System.out.print("BAIRRO: ");
                    String bairro = input.nextLine();

                    System.out.print("NUMERO DA CASA: ");
                    String numero = input.nextLine();
                    if (numero.trim().isEmpty()) numero = "0";

                    int numeroNumeric;

                    while (true) {
                        try {
                            numeroNumeric = Integer.parseInt(numero);
                            if (numeroNumeric < 0) throw new IllegalArgumentException();
                            break;
                        } catch (NumberFormatException e) {
                            System.out.print("POR FAVOR, INFORME UM NÚMERO VÁLIDO: ");
                            numero = input.nextLine();
                        } catch (IllegalArgumentException e) {
                            System.out.print("POR FAVOR, INFORME UM NÚMERO VÁLIDO: ");
                            numero = input.nextLine();
                        }
                    }

                    Endereco e = new Endereco(cidade, bairro, numeroNumeric);
                    p.setEndereco(e);
                }
                case 5 -> {
                    String idade = input.nextLine();
                    if (idade.trim().isEmpty()) idade = "0";

                    double idadeNumeric;
                    while (true) {
                        try {
                            idadeNumeric = Double.parseDouble(idade.replace(",", "."));
                            if (idadeNumeric < 0) throw new IllegalArgumentException();

                            break;
                        } catch (NumberFormatException e) {
                            System.out.print("POR FAVOR, INFORME UM NÚMERO VÁLIDO: ");
                            idade = input.nextLine();
                        } catch (IllegalArgumentException e) {
                            System.out.print("POR FAVOR, INFORME UM NÚMERO VÁLIDO: ");
                            idade = input.nextLine();
                        }
                    }
                    p.setIdade(idadeNumeric);
                }
                case 6 -> {
                    String peso = input.nextLine();
                    if (peso.trim().isEmpty()) peso = "0";

                    double pesoNumeric;
                    while (true) {
                        try {
                            pesoNumeric = Double.parseDouble(peso.replace(",", "."));
                            if (pesoNumeric < 0) throw new IllegalArgumentException();
                            break;
                        } catch (NumberFormatException e) {
                            System.out.print("POR FAVOR, INFORME UM NÚMERO VÁLIDO: ");
                            peso = input.nextLine();
                        } catch (IllegalArgumentException e) {
                            System.out.print("POR FAVOR, INFORME UM NÚMERO VÁLIDO: ");
                            peso = input.nextLine();
                        }
                    }
                    p.setPeso(pesoNumeric);
                }
                case 7 -> {
                    String raca = input.nextLine();
                    if (raca.trim().isEmpty()) raca = null;
                    p.setRaca(raca);
                }
            }
        }
        PetDAO petDAO = new PetDAO();
        petDAO.save(p);
    }

    public static void handleEditarPet() throws SQLException {
        PetDAO petDAO = new PetDAO();
        ResultSet resultFilter = petDAO.listFilteredPets(petsFiltradosMenu());
        listAllPets(resultFilter);

        System.out.print("INFORME O ID DO PET QUE DESEJA EDITAR: ");
        String userInput = input.nextLine();
        int userInputNumeric;

        while (true) {
            try {
                userInputNumeric = Integer.parseInt(userInput);
                break;
            } catch (NumberFormatException e) {
                System.out.print("INFORME UM NÚMERO VÁLIDO: ");
                userInput = input.nextLine();
            }
        }

        ResultSet petById = petDAO.listPetById(userInputNumeric);
        Pet p = new Pet();
        while (petById.next()) {
            int id = petById.getInt("id");
            p.setId(id);
            String nome = petById.getString("nome");
            String tipo = petById.getString("tipo");
            String sexo = petById.getString("sexo");
            String endereco = petById.getString("endereco");
            double idade = petById.getDouble("idade");
            double peso = petById.getDouble("peso");
            String raca = petById.getString("raca");

            String novoNome;
            String novoTipo;
            String novoSexo;
            String novaCidade;
            String novoBairro;
            String novoNumero;
            String novaIdade;
            String novoPeso;
            String novaRaca;


            System.out.println("INFORME OS NOVOS VALORES DOS CAMPOS QUE DESEJA ALTERAR, CASO QUEIRA MANTER O VALOR ORIGINAL BASTA ENVIAR UM VALOR VAZIO.");

            System.out.printf("NOME ATUAL: %s | NOVO VALOR: ", nome);
            novoNome = input.nextLine();
            while (true) {
                if (novoNome.trim().isEmpty()) {
                    NomePet n = new NomePet(nome.split(" ")[0], nome.split(" ")[1]);
                    p.setNome(n);
                    break;
                } else if (novoNome.split(" ").length != 2) {
                    System.out.println("FORMATO DE NOME INVÁLIDO, DEVE SER NOME E SOBRENOME SEPARADOS POR ESPAÇO VAZIO. INFORME NOVAMENTE: ");
                    novoNome = input.nextLine();
                    continue;
                } else {
                    NomePet n = new NomePet(novoNome.split(" ")[0], novoNome.split(" ")[1]);
                    p.setNome(n);
                    break;
                }
            }

            System.out.printf("TIPO ATUAL: %s | NOVO VALOR: ", tipo);
            novoTipo = input.nextLine();
            while (true) {
                try {
                    if (novoTipo.trim().isEmpty()) {
                        TipoPet tipoPet = TipoPet.valueOf(tipo.toUpperCase());
                        p.setTipo(tipoPet);
                        break;

                    } else {
                        TipoPet tipoPet = TipoPet.valueOf(novoTipo.toUpperCase());
                        p.setTipo(tipoPet);
                        break;
                    }

                } catch (IllegalArgumentException e) {
                    System.out.print("INFORME UM TIPO DE PET VÁLIDO (CACHORRO / GATO): ");
                    novoTipo = input.nextLine();
                }
            }

            System.out.printf("SEXO ATUAL: %s | NOVO VALOR: ", sexo);
            novoSexo = input.nextLine();
            while (true) {
                try {
                    if (novoSexo.trim().isEmpty()) {
                        SexoPet sexoPet = SexoPet.valueOf(sexo.toUpperCase());
                        p.setSexo(sexoPet);
                        break;

                    } else {
                        SexoPet sexoPet = SexoPet.valueOf(novoSexo.toUpperCase());
                        p.setSexo(sexoPet);
                        break;
                    }

                } catch (IllegalArgumentException e) {
                    System.out.print("INFORME UM SEXO DE PET VÁLIDO (MACHO / FEMEA): ");
                    novoSexo = input.nextLine();
                }
            }

            System.out.printf("ENDEREÇO ATUAL: %s | NOVO VALOR: ", endereco);
            System.out.print("\nCIDADE: ");
            novaCidade = input.nextLine();
            System.out.print("BAIRRO: ");
            novoBairro = input.nextLine();
            System.out.print("NUMERO: ");
            novoNumero = input.nextLine();
            int novoNumeroNumeric;

            while (true) {
                if (novaCidade.trim().isEmpty()) novaCidade = endereco.split(", ")[0];
                if (novoBairro.trim().isEmpty()) novoBairro = endereco.split(", ")[1];
                if (novoNumero.trim().isEmpty()) novoNumero = endereco.split(", ")[2];

                while (true) {
                    try {
                        novoNumeroNumeric = Integer.parseInt(novoNumero);
                        if (novoNumeroNumeric < 0) throw new IllegalArgumentException();
                        break;
                    } catch (NumberFormatException e) {
                        System.out.print("POR FAVOR, INFORME UM NÚMERO VÁLIDO: ");
                        novoNumero = input.nextLine();
                    } catch (IllegalArgumentException e) {
                        System.out.print("POR FAVOR, INFORME UM NÚMERO VÁLIDO: ");
                        novoNumero = input.nextLine();
                    }
                }

                Endereco e = new Endereco(novaCidade, novoBairro, novoNumeroNumeric);
                p.setEndereco(e);
                break;
            }

            System.out.printf("IDADE ATUAL: %s | NOVO VALOR: ", idade);
            novaIdade = input.nextLine();
            double novaIdadeNumeric;
            if (novaIdade.trim().isEmpty()) {
                p.setIdade(idade);

            } else {
                while (true) {
                    try {
                        novaIdadeNumeric = Double.parseDouble(novaIdade.replace(",", "."));
                        if (novaIdadeNumeric < 0) throw new IllegalArgumentException();
                        break;
                    } catch (NumberFormatException e) {
                        System.out.print("POR FAVOR, INFORME UM NÚMERO VÁLIDO: ");
                        novaIdade = input.nextLine();
                    } catch (IllegalArgumentException e) {
                        System.out.print("POR FAVOR, INFORME UM NÚMERO VÁLIDO: ");
                        novaIdade = input.nextLine();
                    }
                }
                p.setIdade(novaIdadeNumeric);
            }

            System.out.printf("PESO ATUAL: %s | NOVO VALOR: ", peso);
            novoPeso = input.nextLine();
            double novoPesoNumeric;
            if (novoPeso.trim().isEmpty()) {
                p.setPeso(peso);

            } else {
                while (true) {
                    try {
                        novoPesoNumeric = Double.parseDouble(novoPeso.replace(",", "."));
                        if (novoPesoNumeric < 0) throw new IllegalArgumentException();
                        break;
                    } catch (NumberFormatException e) {
                        System.out.print("POR FAVOR, INFORME UM NÚMERO VÁLIDO: ");
                        novoPeso = input.nextLine();
                    } catch (IllegalArgumentException e) {
                        System.out.print("POR FAVOR, INFORME UM NÚMERO VÁLIDO: ");
                        novoPeso = input.nextLine();
                    }
                }
                p.setPeso(novoPesoNumeric);
            }

            System.out.printf("RACA ATUAL: %s | NOVO VALOR: ", raca);
            novaRaca = input.nextLine();
            if (novaRaca.trim().isEmpty()) {
                p.setRaca(raca);
            } else {
                p.setRaca(novaRaca);
            }
        }

        petDAO.updatePet(p);
    }

    public static void handleDeletarPet() throws SQLException{
        PetDAO petDAO = new PetDAO();
        ResultSet resultFilter = petDAO.listFilteredPets(petsFiltradosMenu());
        listAllPets(resultFilter);

        System.out.print("INFORME O ID DO PET QUE DESEJA DELETAR: ");
        String userInput = input.nextLine();
        int userInputNumeric;

        while (true) {
            try {
                userInputNumeric = Integer.parseInt(userInput);
                break;
            } catch (NumberFormatException e) {
                System.out.print("INFORME UM NÚMERO VÁLIDO: ");
                userInput = input.nextLine();
            }
        }

        petDAO.deletePet(userInputNumeric);
    }

}
