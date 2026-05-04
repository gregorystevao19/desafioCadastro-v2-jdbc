package dao;

import org.estudo.db.DbConnection;
import org.estudo.domain.Pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetDAO {

    public void save(Pet pet){
        final String SQL = "INSERT INTO pet(nome, tipo, sexo, endereco, idade, peso, raca) VALUES(?, ?, ?, ?, ?, ?, ?, )";
        try(Connection c = DbConnection.openConnection()){
            PreparedStatement s = c.prepareStatement(SQL);
            s.setString(1, pet.getNome().getPrimeiroNome() + " " + pet.getNome().getSobrenome());
            s.setString(2, pet.getTipo().toString());
            s.setString(3, pet.getSexo().toString());
            s.setString(4, pet.getEndereco().getCidade() + ", " + pet.getEndereco().getRua() + ", " + pet.getEndereco().getNumeroCasa());
            s.setDouble(5, pet.getIdade());
            s.setDouble(6, pet.getPeso());
            s.setString(7, pet.getRaca());

            if(s.executeUpdate(SQL) > 0){
                System.out.println("PET SALVO COM SUCESSO");
            }
            else {
                System.out.println("ERRO AO SALVAR O PET NO BANCO DE DADOS");
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ResultSet listAllPets(){
        final String SQL = "SELECT * FROM pet";
        try(Connection c = DbConnection.openConnection()){
            PreparedStatement s = c.prepareStatement(SQL);
            return s.executeQuery();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
