package com.billiard.servlet;

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

public class PointRequestServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        ArrayList<String> points = new ArrayList<String>();

        DataSource ds = null;
        Connection conn = null;

        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("jdbc/postgres");
            conn = ds.getConnection();
            if (conn != null) {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT st_asgeojson(geom) FROM eleschool;");
                while (rs.next()) {
                    points.add(rs.getString(1));
                }
                rs.close();
                st.close();
                conn.close();
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String json = gson.toJson(points);

        resp.getWriter().println(json);
    }
}


// class Point {
//     double lon, lat;

//     Point(double lon, double lat) {
//         this.lon = lon;
//         this.lat = lat;
//     }
// }