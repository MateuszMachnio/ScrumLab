package pl.coderslab.dao;

import pl.coderslab.model.Recipe;
import pl.coderslab.model.otherInformations;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class otherInformationsDao {

    private static final String READ_INFORMATIONS ="SELECT * from otherInformations where id = ?;";

    public otherInformations read(int id) {
        otherInformations otherInformations = new otherInformations();
        try (Connection connection = DbUtil.getConnection();

             PreparedStatement readInformations = connection.prepareStatement(READ_INFORMATIONS)) {
            readInformations.setInt(1, id);
            try (ResultSet resultSet = readInformations.executeQuery()) {
                while (resultSet.next()) {
                    otherInformations.setId(resultSet.getInt("id"));
                    otherInformations.setCompanyName(resultSet.getString("companyName"));
                    otherInformations.setCompanyAdress(resultSet.getString("companyAdress"));
                    otherInformations.setCompanyEmail(resultSet.getString("companyEmail"));
                    otherInformations.setCompanyPhone(resultSet.getString("companyPhone"));
                    otherInformations.setAuthors(resultSet.getString("authors"));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return otherInformations;
    }
}
