package com.moneyTracker.Models;

import com.moneyTracker.Views.AccountType;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDriver {
    private Connection connection;

    public DatabaseDriver() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:moneyTracker.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUserData(String username, String password) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM User WHERE username='"+username+"' AND password ='"+password+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public boolean createUser(String username, String password) {
        String sql = "INSERT INTO User (username, password) VALUES (?, ?);";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createAccount(int userId, String accId, String accOwner, String expDate, AccountType accType, double balance) {
        String sql = "INSERT INTO Account (number, owner, expirationDate, type, balance, user_ID) VALUES (?, ?, ?, ?, ?, ?);";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, accId);
            pstmt.setString(2, accOwner);
            pstmt.setString(3, expDate);
            pstmt.setString(4, accType.name());
            pstmt.setDouble(5, balance);
            pstmt.setInt(6, userId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> getAccountsByUserId(int userId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Account WHERE user_ID = ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                YearMonth expDate = YearMonth.parse(rs.getString("expirationDate"), formatter);
                Account account = new Account(
                        rs.getString("owner"),
                        rs.getString("number"),
                        rs.getString("type"),
                        expDate,
                        rs.getDouble("balance")
                );
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
