package org.jakartaeerecipes.rosterui.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jakartaeerecipes.rosterui.model.Roster;

@WebServlet(name = "BuilderServlet", urlPatterns = {"/builderServlet"})
public class BuilderServlet extends HttpServlet implements Servlet {
    // Retrieve our executor instance.

    @Resource(name = "concurrent/BuilderExecutor")
    ManagedExecutorService mes;
    RosterInfo rosterInfoHome;

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            PrintWriter out = resp.getWriter();
            // Create the task instances
            List<Callable<RosterInfo>> builderTasks = List.of(
                    new RosterTask(1),
                    new RosterTask(2));

            // Submit the tasks and wait.
            List<Future<RosterInfo>> taskResults = mes.invokeAll(builderTasks);
            List<RosterInfo> results = new ArrayList<>();
            for (Future<RosterInfo> result : taskResults) {
                out.write("Processing Results...");
                results.add(result.get());
            }
            out.write("** Results Processed Successfully **");
            for (RosterInfo result : results) {
                if (result != null) {
                    System.out.println("===========================");
                    System.out.println("Team: " + result.team());
                    System.out.println("===========================");
                    for (Roster roster : result.players()) {
                        System.out.println(roster.getFirstName() + " " +
                                roster.getLastName() + " - " + roster.getPosition());
                    }
                }
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(BuilderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
