package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DbController {
    private Connection conn = null;

    public DbController() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/base.db");
        } catch (SQLException ex) {
            System.out.println("Connection failed.");
        }
    }

    public void createTestDb() {
        try (Scanner scanner = new Scanner(new File("src/main/resources/newDb.sql"))) {
            while (scanner.hasNextLine()) {
                String sql = scanner.nextLine();
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.execute();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTestUsers() {
        try (Scanner scanner = new Scanner(new File("src/main/resources/testUsers.sql"))) {
            while (scanner.hasNextLine()) {
                String sql = scanner.nextLine();
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.execute();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void newUser(String name, String email) {
        String sql = "INSERT INTO users(name,email) VALUES (?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);

            statement.execute();
        } catch (SQLException e) {

        }
    }
    public void deleteUser(int id){
        String sql = "DELETE FROM users WHERE id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException ex){

        }
    }
    public void updateUser(int id, String name, String email){
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?;";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,name);
            statement.setString(2, email);
            statement.setInt(3, id);
            statement.execute();
        } catch (SQLException ex){

        }
    }
    public ArrayList<String> readUser(int id){
        String sql = "SELECT name FROM users WHERE id = ?;";
        ArrayList<String> user = new ArrayList<>();
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                user.add(resultSet.getString("name"));
            }
        } catch (SQLException ex){
            throw new RuntimeException();
        }
        return user;
    }
}
