package org.estudo.dao;

import org.estudo.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PerguntaDAO {
    public ResultSet listAllPerguntas() {
        final String SQL = "SELECT * FROM pergunta ORDER BY id ASC ";
        try (Connection c = DbConnection.openConnection()) {
            PreparedStatement s = c.prepareStatement(SQL);
            return s.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
