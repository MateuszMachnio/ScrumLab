package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admins;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminsDao {

    private static final String CREATE_ADMINS_QUERY = "INSERT INTO admins(first_name, last_name, email, password, superadmin, enable) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String READ_ADMINS_QUERY = "SELECT * FROM admins WHERE id = ?";
    private static final String UPDATE_ADMINS_QUERY = "UPDATE admins SET first_name = ?, last_name = ?, email = ?, password = ?, superadmin = ?, enable = ? WHERE id = ?;";
    private static final String DELETE_ADMINS_QUERY = "DELETE FROM admins WHERE id = ?";
    private static final String FIND_ALL_ADMINS_QUERY = "SELECT * FROM admins";

    public Admins create(Admins admins) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_ADMINS_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, admins.getFirstName());
            statement.setString(2, admins.getLastName());
            statement.setString(3, admins.getEmail());
            statement.setString(4, hashPassword(admins.getPassword()));
            statement.setInt(5, admins.getSuperadmin());
            statement.setInt(6, admins.getEnable());
            int result = statement.executeUpdate();
            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.first()) {
                    admins.setId(resultSet.getInt(1));
                    return admins;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public Admins read(int adminId) {
        Admins admins = new Admins();  
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ADMINS_QUERY);
            preparedStatement.setInt(1, adminId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    admins.setFirstName(resultSet.getString("first_name"));
                    admins.setLastName(resultSet.getString("last_name"));
                    admins.setEmail(resultSet.getString("email"));
                    admins.setPassword(resultSet.getString("password"));
                    admins.setSuperadmin(resultSet.getInt("superadmin"));
                    admins.setEnable(resultSet.getInt("enable"));
                    admins.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    public void update(Admins admins) {   
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_ADMINS_QUERY);
            statement.setString(1, admins.getFirstName());
            statement.setString(2, admins.getLastName());
            statement.setString(3, admins.getEmail());
            statement.setString(4, hashPassword(admins.getPassword()));
            statement.setInt(5, admins.getSuperadmin());
            statement.setInt(6, admins.getEnable());
            statement.setInt(7, admins.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADMINS_QUERY);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            boolean deleted = preparedStatement.execute();
            if (!deleted) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Admins> findAll() {
        List<Admins> adminsList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_ADMINS_QUERY);
            while (resultSet.next()) {
                Admins admins = new Admins();
                admins.setFirstName(resultSet.getString("first_name"));
                admins.setLastName(resultSet.getString("last_name"));
                admins.setEmail(resultSet.getString("email"));
                admins.setPassword(resultSet.getString("password"));
                admins.setSuperadmin(resultSet.getInt("superadmin"));
                admins.setEnable(resultSet.getInt("enable"));
                admins.setId(resultSet.getInt("id"));
                adminsList.add(admins);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminsList;
    }

    public boolean isEnable(int adminId){
        Admins userToCheck = new Admins();
        userToCheck = read(adminId);
        if (userToCheck.getEnable() == 1){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isSuperAdmin(int adminId) {
        Admins userToCheck = new Admins();
        userToCheck = read(adminId);
        if (userToCheck.getSuperadmin() == 1) {
            return true;
        } else {
            return false;
        }


    }
}
