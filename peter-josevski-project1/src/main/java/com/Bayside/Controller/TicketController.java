package com.Bayside.Controller;

import com.Bayside.Model.Ticket;
import com.Bayside.Model.User;
import com.Bayside.Service.TicketService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;
import java.util.List;

public class TicketController {
    private TicketService ts = new TicketService();



        public void mapEndpoints(Javalin app) {

            // Create Ticket
            app.get("/Ticket/new", (ctx) -> {
                ctx.result("Enter your requested amount and the reason as shown below.");
                ctx.status(200);
            });

            app.post("/Ticket/new", (ctx) -> {

                HttpSession httpSession = ctx.req.getSession();
                User user = (User) httpSession.getAttribute("user");

                if (user == null) {
                    ctx.result("You must be logged in to submit a ticket");
                    ctx.status(400);
                    return;
                }

                Ticket newTicket;

                try {
                    newTicket = ctx.bodyAsClass(Ticket.class);
                } catch (Exception e) {
                    ctx.result("The input could not be read by our system.");
                    ctx.status(400);
                    return;
                }

                if (!ts.Description(newTicket.getDescription())) {
                    ctx.result("The reason must be under 100 characters in length");
                    ctx.status(400);
                    return;
                }

                int status = ts.addEmployeeTicket(user, newTicket);
                switch (status) {
                    case 200:
                        ctx.result("Your Ticket has been submitted");
                        break;
                    case 400:
                        ctx.result("You did not enter an amount.");
                        break;
                    case 500:
                        ctx.result("The system has encountered an unexpected error");
                        break;
                    default:
                        ctx.result("The system has encountered a VERY unexpected error");
                }
                ctx.status(status);

            });


            // See tickets

            app.get("/reimbursements", (ctx) -> {
                HttpSession httpSession = ctx.req.getSession();
                User user = (User) httpSession.getAttribute("user");

                if (user == null) {
                    ctx.result("You must be logged in to ask for a reimbursement");
                    ctx.status(400);
                    return;
                }

                String table;
                String statusParam = ctx.queryParam("status");
                if (statusParam != null) {
                    if (user.isAdmin()) {
                        table = rs.viewAllTicketsOfType(statusParam);
                    } else {
                        table = rs.viewEmployeeTicketsOfType(user, statusParam);
                    }
                } else {
                    if (user.isAdmin()) {
                        table = rs.viewAllTickets();
                    } else {
                        table = rs.viewEmployeeTickets(user);
                    }
                }


                if (table.isEmpty()) {
                    ctx.result("An unexpected error has occurred");
                    ctx.status(500);
                } else if (table.equals("-s")) {
                    ctx.result("We could not understand Path Parameter for status:" + statusParam);
                    ctx.status(400);
                } else {
                    ctx.result(table);
                    ctx.status(200);
                }
            });


            // Handle requests (approve or deny)

            app.get("/reimbursements/management", (ctx) -> {
                ctx.result("Enter the ID and new status of the ticket you wish to handle." +
                        "Enter it as \n\nTicket_ID\nNew_Status");
                ctx.status(200);
            });

            app.patch("/reimbursements/management", (ctx) -> {

                HttpSession httpSession = ctx.req.getSession();
                User manager = (User) httpSession.getAttribute("user");

                if (manager == null || !manager.isAdmin()) {
                    ctx.result("You must be logged in as manager to approve reimbursements.");
                    ctx.status(400);
                    return;
                }

                // Get input of body
                String input = ctx.body();
                String[] idAndStatus = input.split("\n", 0);

                //Check for valid input (2-items, username and password, before passing the info on.
                if (idAndStatus.length != 2) {
                    ctx.result("Improper formatting: please enter both the ID and new status, and nothing else. Use get for more information.");
                    ctx.status(400);
                    return; //Empty Return to bypass the following code
                }

                int status = ts.handleRequest(idAndStatus[0], idAndStatus[1], manager);

                switch (status) {
                    case 200:
                        ctx.result("The Ticket has been updated successfully.");
                        break;
                    case 400:
                        ctx.result("The ticket ID you entered is not in the system.");
                        break;
                    case 409:
                        ctx.result("Ticket has already been handled");
                        break;
                    case 1:
                        ctx.result("We could not understand your request. Unclear whether ticket is approved or denied. Your entry:" + idAndStatus[1] + " ");
                        status = 406;
                        break;
                    case 2:
                        ctx.result("Ticket is already pending.");
                        status = 406;
                        break;
                    case 3:
                        ctx.result("Your entry for Ticket ID could not be recognized by our system: " + idAndStatus[0]);
                        status = 406;
                        break;
                    case 500:
                        ctx.result("Unexpected error.")
                        status = 404;
                    default:
                        ctx.result("Ticket Pending");
                }
                ctx.status(status);
            });

        }
    }
