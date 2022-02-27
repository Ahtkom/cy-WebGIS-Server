package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountCheckServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean accountValid = true;
        if (req.getParameter("account") == "ahtkom") {
            accountValid = false;
        }

        PrintWriter writer = resp.getWriter();
        if (accountValid) {
            writer.println("Account is valid");
        } else {
            writer.println("Account is invalid");
        }
    }
}
