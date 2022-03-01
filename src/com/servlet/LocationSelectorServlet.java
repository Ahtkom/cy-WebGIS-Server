package com.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.*;

import com.google.gson.Gson;

public class LocationSelectorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String provinceID = req.getParameter("provinceID");
        String cityID = req.getParameter("cityID");
        String provinceName = null;
        // String cityName = null;

        DataSource ds = null;
        Connection conn = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/postgres");
            conn = ds.getConnection();
            if (conn != null) {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT name FROM prov where id = "+provinceID+";");
                while (rs.next()) {
                    // Get province name from database by id
                    provinceName = rs.getString(1);
                }

                if (cityID.isEmpty()) { // Response all cities in given province 
                    ArrayList<City> cities = new ArrayList<City>();

                    rs = st.executeQuery("select name, id from "+provinceName+";");
                    while (rs.next()) {
                        cities.add(new City(rs.getString(1), rs.getInt(2)));
                    }

                    Gson cityGson = new Gson();
                    Gson provinceGson = new Gson();
                    String citiesJson = cityGson.toJson(cities);
                    String provinceJson = provinceGson.toJson(
                        new Province(provinceName, Integer.parseInt(provinceID)));

                    resp.getWriter().println("{\"province\":"+provinceJson+",\"cities\":"+citiesJson+"}");
                } else { // Response the sepcific city info
                    rs = st.executeQuery("select name, id from "+provinceName+" where id = "+cityID+";");
                    City city = null;

                    while (rs.next()) {
                        city = new City(rs.getString(1), rs.getInt(2));
                    }

                    Gson cityGson = new Gson();
                    resp.getWriter().println(cityGson.toJson(city));
                }
                rs.close();
                st.close();
                conn.close();
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }
}


class City {
    String name;
    int id;

    City(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
