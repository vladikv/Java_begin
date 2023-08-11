package org.example;

import java.sql.*;
import java.util.Scanner;


public class Main {
    static String databaseName="java_salo";
    public static void main(String[] args) {
        System.out.println("Привіт козаки");
        //createDatabase();
        //createTable();
        insertDataTable();
        ReadDataTable();
        UpdateDataTable();
        DeleteDataTable();

    }
    public static void createDatabase() {
        String url = "jdbc:mariadb://localhost:3306";
        String user="root";
        String password="";
        String dbName = "";
        try(Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Вкажіть назву БД:");
            Scanner in = new Scanner(System.in);
            dbName = in.nextLine();
            // Створюємо Statement обєкт щоб виконувати SQL запити
            Statement stmt = con.createStatement();
            // створюєио зміну яка містить SQL запит щоб перевірити чи існує БД
            String checkDbQuery = "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME = '" + dbName + "'";
            // Виконуємо запит щоб перевірити чи існує така БД
            ResultSet rs = stmt.executeQuery(checkDbQuery);
            // Перевіряємо результат
            boolean databaseExists = rs.next();
            rs.close();
            if (databaseExists) {
                System.out.println("-------База з даним ім'ям уже існує-------.");
            } else {
                // SQL запит для створення нової БД
                String createDbQuery = "CREATE DATABASE " + dbName;
                // Виконуємо цей запит
                stmt.executeUpdate(createDbQuery);
                System.out.println("Базу даних створнео успішно :)");
            }
            // Закриваємо Statement і підключення до БД
            stmt.close();
        }
        catch(Exception ex) {
            System.out.println("Problem connection database "+ ex.getMessage());
        }
    }
    public static void createTable() {
        String url = "jdbc:mariadb://localhost:3306/"+databaseName;
        String user="root";
        String password="";
        String dbName = "";
        try(Connection con = DriverManager.getConnection(url, user, password)) {
            // Створюємо об’єкт Statement для виконання операторів SQL
            Statement stmt = con.createStatement();

            // SQL запит для створення таблиці
            String createTableQuery = "CREATE TABLE users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "email VARCHAR(100) NOT NULL" +
                    ")";


            // Виконуємо попередній запит
            stmt.executeUpdate(createTableQuery);
            System.out.println("Таблиця користувачі створено успішно");
            //Закриваємо Statement і підключення до БД
            stmt.close();
        }
        catch(Exception ex) {
            System.out.println("Problem connection database "+ ex.getMessage());
        }
    }
    public static void ReadDataTable(){
        String url = "jdbc:mariadb://localhost:3306/"+databaseName;
        String user="root";
        String password="";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String selectQuery = "SELECT * FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    System.out.println("ID: " + id + ", Username: " + username + ", Email: " + email + "\n");
                }
            }
        }
        catch (Exception ex){
            System.out.println("Problem connection database"+ ex.getMessage());
        }
    }
    public static void insertDataTable() {
        String url = "jdbc:mariadb://localhost:3306/"+databaseName;
        String user="root";
        String password="";
        try(Connection con = DriverManager.getConnection(url, user, password)) {
            // Створюємо об’єкт Statement для виконання операторів SQL
            Statement stmt = con.createStatement();
            // Створюємо statement
            PreparedStatement statement = con.prepareStatement("INSERT INTO users (username, email) VALUES (?, ?)");
            // Задаємо параметри
            statement.setString(1, "ivan");
            statement.setString(2, "ivan@example.com");
            // Виконуємо запит
            int rowsAffected = statement.executeUpdate();
            System.out.println("Успішно додано дані");
            stmt.close();
        }
        catch(Exception ex) {
            System.out.println("Problem connection database "+ ex.getMessage());
        }
    }

    public static void UpdateDataTable(){
        String url = "jdbc:mariadb://localhost:3306/"+databaseName;
        String user="root";
        String password="";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String updateQuery = "UPDATE users SET email = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, "ivanl@example.com");
            preparedStatement.setString(2, "ivan");

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println("updated successfully!");
        }
        catch (Exception ex){
            System.out.println("Problem connection database "+ ex.getMessage());
        }
    }

    public static void DeleteDataTable(){
        String url = "jdbc:mariadb://localhost:3306/"+databaseName;
        String user="root";
        String password="";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String deleteQuery = "DELETE FROM users WHERE username = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, "ivan");

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("deleted successfully!");
        }
        catch (Exception ex){
            System.out.println("Problem connection database "+ ex.getMessage());
        }
    }

}