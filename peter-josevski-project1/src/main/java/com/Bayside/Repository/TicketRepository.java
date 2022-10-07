package com.Bayside.Repository;

import com.Bayside.exception.TicketNotFound;
import com.Bayside.Model.Ticket;
import com.Bayside.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketRepository {
    public static final int PENDING = 1;
    public static final int APPROVED = 2;
    public static final int DENIED = 3;

    UserRepository ur = new UserRepository();

    public TicketRepository(){

    }

    public void addEmployeeRequest(User submitter, Ticket newTicket) throws SQLException, TicketNotFound {
        try (Connection conn = ConnectionFactory.createConnection()){
            String sql = "INSERT INTO tickets(employee, amount, description) VALUES (?, ?, ?);";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ConnectionFactory cf = new ConnectionFactory();

            pstmt.setInt(1, ur.getUserByUsername(UserRepository.un));
            pstmt.setDouble(2, newTicket.getAmount());
            pstmt.setString(3, newTicket.getDescription());

            pstmt.executeUpdate();
        }

    }

    public String getAllTickets() throws SQLException {
        try (Connection conn = ConnectionFactory.createConnection()){
            String sql = "SELECT id, current_status, approver_name, amount, submitter_name, reason FROM tickets_full ORDER BY id_status, id;";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            String tableStr = "_".repeat(257) + "\n" + String.format("|%4s | %-10s| %-60s| %-10s| %-60s| %-100s|\n", "ID" , "Status" , "Approving Manager", "Amount", "Requesting Employee", "Reason") + "|" + "=".repeat(255) + "|\n";
            while(rs.next()) {
                tableStr += String.format("|%4d | %-10s| %-60s| %-10s| %-60s| %-100s|\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) );
            }
            tableStr += "|" + "_".repeat(255) + "|";
            return tableStr;
        }
    }

    public String getAllTicketsOfType(int status) throws SQLException {
        try (Connection conn = ConnectionFactory.createConnection()){
            String sql = "SELECT id, current_status, approver_name, amount, submitter_name, reason FROM tickets_full WHERE status=? ORDER BY id_status, id;";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, status);

            ResultSet rs = pstmt.executeQuery();

            String tableStr = "_".repeat(257) + "\n" + String.format("|%4s | %-10s| %-60s| %-10s| %-60s| %-100s|\n", "ID" , "Status" , "Approving Manager", "Amount", "Requesting Employee", "Reason") + "|" + "=".repeat(255) + "|\n";
            while(rs.next()) {
                tableStr += String.format("|%4d | %-10s| %-60s| %-10s| %-60s| %-100s|\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) );
            }
            tableStr += "|" + "_".repeat(255) + "|";
            return tableStr;
        }
    }

    public String getAllEmployeeTickets(User employee) throws SQLException {
        try (Connection conn = ConnectionFactory.createConnection()){
            String sql = "SELECT amount, current_status, reason FROM tickets_full WHERE submitter=? ORDER BY id_status, id;";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, ur.getEmployeeId(employee.getUsername()));

            ResultSet rs = pstmt.executeQuery();

            String tableStr = "_".repeat(127) + "\n" + String.format("|%10s | %-10s| %-100s|\n", "Amount" , "Status" , "Reason") + "|" + "=".repeat(125) + "|\n";
            while(rs.next()) {
                tableStr += String.format("|%10s | %-10s| %-100s|\n", rs.getString(1), rs.getString(2), rs.getString(3) );
            }
            tableStr += "|" + "_".repeat(125) + "|";
            return tableStr;
        }
    }

    public String getAllEmployeeTicketsOfType(User employee, int status) throws SQLException {
        try (Connection conn = ConnectionFactory.createConnection()){
            String sql = "SELECT amount, current_status, reason FROM tickets_full WHERE submitter=? AND status=? ORDER BY id_status, id;";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, ur.getId(employee.getUsername()));
            pstmt.setInt(2, status);

            ResultSet rs = pstmt.executeQuery();

            String tableStr = "_".repeat(127) + "\n" + String.format("|%10s | %-10s| %-100s|\n", "Amount" , "Status" , "Reason") + "|" + "=".repeat(125) + "|\n";
            while(rs.next()) {
                tableStr += String.format("|%10s | %-10s| %-100s|\n", rs.getString(1), rs.getString(2), rs.getString(3) );
            }
            tableStr += "|" + "_".repeat(125) + "|";
            return tableStr;
        }
    }

    public void changeStatus(int ticketID, int status, int manager_id) throws SQLException {
        try (Connection conn = ConnectionFactory.createConnection()){
            String sql = "UPDATE tickets SET status = ?, approver = ? WHERE id=?;";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, status);
            pstmt.setInt(2, manager_id);
            pstmt.setInt(3, ticketID);

            pstmt.executeUpdate();
        }
    }

    public boolean isPending(int id) throws SQLException { //Thrown if ticket does not exist
        try (Connection conn = ConnectionFactory.createConnection()){
            String sql = "SELECT status FROM tickets_full WHERE id=?;";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            rs.next();
            return rs.getInt(1)==PENDING;
        }
    }
}


