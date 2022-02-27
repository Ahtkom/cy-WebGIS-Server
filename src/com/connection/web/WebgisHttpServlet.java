package com.connection.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/cc")
public class WebgisHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);

        resp.setContentType("text/html");
		try (PrintWriter out = resp.getWriter()) {
            out.println("<html><body>");
            out.println("<h1>Hello Readers</h1>");
            out.println("</body></html>");
        }
    }
}
