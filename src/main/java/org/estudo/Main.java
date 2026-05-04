package org.estudo;

import dao.PetDAO;
import org.estudo.ui.Menu;

import java.sql.SQLException;

public class Main {

    public static void start() {
        Menu.startMenu();
        int userInputStartMenu = Menu.handleUserStartMenuInput();
        switch (userInputStartMenu) {
            case 1 -> gerenciarPetMenu();
            case 2 -> {
                System.out.println("=================================");
                System.out.println("      PROGRAMA FINALIZADO        ");
                System.out.println("=================================");
                System.exit(0);
            }
        }
    }

    public static void gerenciarPetMenu() {
        PetDAO petDAO = new PetDAO();
        while (true) {
            Menu.gerenciarPetMenu();
            int userInputGerenciarPetMenu = Menu.handleUserGerenciarPetMenuInput();
            switch (userInputGerenciarPetMenu) {
                case 1 -> {
                    try {
                        Menu.listAllPets(petDAO.listAllPets());
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> System.out.println(" ");
                case 3 -> System.out.println(" ");
                case 4 -> System.out.println(" ");
                case 5 -> System.out.println(" ");
                case 6 -> start();
                case 7 -> {
                    System.out.println("=================================");
                    System.out.println("      PROGRAMA FINALIZADO        ");
                    System.out.println("=================================");
                    System.exit(0);
                }
            }
        }
    }


    static void main() {
        start();
    }
}
