package org.estudo.dao;

import org.estudo.db.DbConnection;
import org.estudo.domain.Pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {

    public void save(Pet pet) {
        final String SQL = "INSERT INTO pet(nome, tipo, sexo, endereco, idade, peso, raca) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DbConnection.openConnection()) {
            PreparedStatement s = c.prepareStatement(SQL);
            s.setString(1, pet.getNome() != null ? pet.getNome().getPrimeiroNome() + " " + pet.getNome().getSobrenome() : null);
            s.setString(2, pet.getTipo().toString());
            s.setString(3, pet.getSexo().toString());
            s.setString(4, pet.getEndereco().getCidade() + ", " + pet.getEndereco().getRua() + ", " + pet.getEndereco().getNumeroCasa());
            s.setDouble(5, pet.getIdade());
            s.setDouble(6, pet.getPeso());
            s.setString(7, pet.getRaca());

            if (s.executeUpdate() > 0) {
                System.out.println("PET SALVO COM SUCESSO");
            } else {
                System.out.println("ERRO AO SALVAR O PET NO BANCO DE DADOS");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet listAllPets() {
        final String SQL = "SELECT * FROM pet";
        try (Connection c = DbConnection.openConnection()) {
            PreparedStatement s = c.prepareStatement(SQL);
            return s.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ResultSet listFilteredPets(List<String> filtros) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM pet");

        if (filtros != null && !filtros.isEmpty()) {
            sql.append(" WHERE ");

            List<String> conditions = new ArrayList<>();

            for (int i = 0; i < filtros.size(); i++) {
                conditions.add("""
                            (
                                nome ILIKE ? OR
                                tipo ILIKE ? OR
                                sexo ILIKE ? OR
                                endereco ILIKE ? OR
                                raca ILIKE ? OR
                                CAST(idade AS TEXT) ILIKE ? OR
                                CAST(peso AS TEXT) ILIKE ?
                            )
                        """);
            }
            sql.append(String.join(" AND ", conditions));
        }

        try (Connection c = DbConnection.openConnection()) {
            PreparedStatement stmt = c.prepareStatement(sql.toString());
            int paramIndex = 1;

            for (String filtro : filtros) {
                String likeValue = "%" + filtro + "%";

                for (int i = 0; i < 7; i++) {
                    stmt.setString(paramIndex++, likeValue);
                }
            }

            return stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ResultSet listPetById(int id) {
        final String SQL = "SELECT * FROM pet WHERE id = ?";
        try (Connection c = DbConnection.openConnection()) {
            PreparedStatement s = c.prepareStatement(SQL);
            s.setInt(1, id);
            return s.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void updatePet(Pet pet) {
        final String SQL = "UPDATE pet SET nome = ?, tipo = ?, sexo = ?, endereco = ?, idade = ?, peso = ?, raca = ? WHERE id = ?";
        try (Connection c = DbConnection.openConnection()) {
            PreparedStatement s = c.prepareStatement(SQL);
            s.setString(1, pet.getNome() != null ? pet.getNome().getPrimeiroNome() + " " + pet.getNome().getSobrenome() : null);
            s.setString(2, pet.getTipo().toString());
            s.setString(3, pet.getSexo().toString());
            s.setString(4, pet.getEndereco().getCidade() + ", " + pet.getEndereco().getRua() + ", " + pet.getEndereco().getNumeroCasa());
            s.setDouble(5, pet.getIdade());
            s.setDouble(6, pet.getPeso());
            s.setString(7, pet.getRaca());
            s.setInt(8, pet.getId());

            if (s.executeUpdate() > 0) {
                System.out.println("PET SALVO COM SUCESSO");
            } else {
                System.out.println("ERRO AO SALVAR O PET NO BANCO DE DADOS");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePet(int id) {
        final String SQL = "DELETE FROM pet WHERE id = ?";
        try (Connection c = DbConnection.openConnection()) {
            PreparedStatement s = c.prepareStatement(SQL);
            s.setInt(1, id);
            if (s.executeUpdate() > 0) {
                System.out.println("PET DELETADO COM SUCESSO");
            } else {
                System.out.println("ERRO AO DELETAR O PET NO BANCO DE DADOS");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
