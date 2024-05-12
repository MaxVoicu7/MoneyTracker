package com.moneyTracker.Models;

import com.moneyTracker.Views.AccountType;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public boolean createSpending(int accId, String description, String message, double amount) {
        PreparedStatement pstmt = null;
        PreparedStatement updateStmt = null;

        String insertSql = "INSERT INTO Spending (description, amount, date, message, accountId) VALUES (?, ?, ?, ?, ?);";
        String updateSql = "UPDATE Account SET balance = balance - ? WHERE id = ?;";

        try {
            // Begin transaction
            connection.setAutoCommit(false);

            // Insert the spending entry
            pstmt = connection.prepareStatement(insertSql);
            pstmt.setString(1, description);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, LocalDate.now().toString());
            pstmt.setString(4, message);
            pstmt.setInt(5, accId);
            int affectedRows = pstmt.executeUpdate();

            // Update the account balance
            updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setDouble(1, amount);
            updateStmt.setInt(2, accId);
            int updatedRows = updateStmt.executeUpdate();

            // Commit or rollback based on the success of the operations
            if (affectedRows > 0 && updatedRows > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException se) {
                System.err.println("Rollback error: " + se.getMessage());
            }
            return false;
        } finally {
            try {
                // Close prepared statements
                if (pstmt != null) pstmt.close();
                if (updateStmt != null) updateStmt.close();
                // Reset auto-commit to true
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException se) {
                System.err.println("SQL close error: " + se.getMessage());
            }
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
                        rs.getInt("id"),
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

    public List<Spending> getAllSpendingsForUser(int userId) {
        List<Account> accounts = getAccountsByUserId(userId);
        List<Integer> accountIds = accounts.stream()
                .map(Account::getId)
                .collect(Collectors.toList());

        return getSpendingsByAccountIds(accountIds);
    }

    public List<Spending> getSpendingsByAccountIds(List<Integer> accountIds) {
        List<Spending> spendings = new ArrayList<>();
        if (accountIds.isEmpty()) {
            return spendings;
        }

        // Construct SQL IN clause dynamically based on account IDs size
        String inClause = accountIds.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", ", "(", ")"));

        String sql = "SELECT * FROM Spending WHERE accountId IN " + inClause;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int index = 1;
            for (Integer id : accountIds) {
                pstmt.setInt(index++, id);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Spending spending = new Spending(
                        rs.getString("description"),
                        rs.getDouble("amount"),
                        LocalDate.parse(rs.getString("date")),
                        rs.getString("message")
                );
                spendings.add(spending);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spendings;
    }
}
