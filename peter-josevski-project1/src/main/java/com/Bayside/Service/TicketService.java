package com.Bayside.Service;

import com.Bayside.Repository.TicketRepository;

import com.Bayside.Model.Ticket;

import java.sql.SQLException;
import java.util.List;

public class TicketService {

    private TicketRepository TicketRepository = new TicketRepository();

    public List<Ticket> getAllTickets() throws SQLException {
        return TicketRepository.getAllTickets();
    }

    public List<Ticket> getAllTickets(int Id) throws SQLException {
        return TicketRepository.getAllTickets(Id);
    }

        // Check if Ticket is processed
    public String checkIfProcessed(int id, String status) {
        if (status == "approved"){
            return "Ticket has been approved";
            else if (status == "denied"){
                return "Ticket has been denied";
            } else {
                throw new RuntimeException("invalid entry");
            }
    }
        // Check if Ticket exists
        public String checkIfExists(int id) throw SQLException{
            if (id) {

            }
        }


        // Already Processed


        // Ticket awaiting processing

    }
}
