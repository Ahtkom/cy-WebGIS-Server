package com.servlet;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;

public class ProvinceLoaderServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        ArrayList<Province> arrayList = new ArrayList<Province>();

        DataSource ds = null;
        Connection conn = null;

        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("jdbc/postgres");
            conn = ds.getConnection();
            if (conn != null) {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT name, id FROM prov;");
                while (rs.next()) {
                    arrayList.add(new Province(rs.getString(1), rs.getInt(2)));
                }
                rs.close();
                st.close();
                conn.close();
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String json = gson.toJson(arrayList);

        resp.getWriter().println(json);
    }
}


class Province {
    String name;
    int id;

    Province(String name, int id) {
        this.name = name;
        this.id = id;
    }
}