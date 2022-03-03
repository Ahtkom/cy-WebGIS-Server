package com.servlet;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.google.gson.Gson;


public class BilliardRoomLoaderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        double lon = Double.parseDouble(req.getParameter("lon"));
        double lat = Double.parseDouble(req.getParameter("lat"));
        String tableName = "eleschool";

        ArrayList<RespBilliardRoom> rooms = new ArrayList<RespBilliardRoom>();

        DataSource ds = null;
        Connection conn = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/postgres");
            conn = ds.getConnection();
            if (conn != null) {
                Statement st = conn.createStatement();

                String sql = "select name, st_x(geom), st_y(geom), " +
                        "st_distance(geom, 'POINT(" + lon + " " + lat + ")'::geography, true) " +
                        "from " + tableName +
                        " where st_dwithin(geom, 'POINT(" + lon + " " + lat + ")'::geography, 10000);";

                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    rooms.add(
                        new RespBilliardRoom(
                            rs.getString(1),
                            rs.getDouble(2),
                            rs.getDouble(3),
                            rs.getDouble(4)
                        )
                    );
                }
                rs.close();
                st.close();
                conn.close();
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String json = gson.toJson(rooms);

        resp.getWriter().println(json);
    }
}


class BilliardRoom {
    String name;
    double lon, lat;

    BilliardRoom(String name, double lon, double lat) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }

    // double distanceFrom(double lon, double lat) {
    // }
}

class RespBilliardRoom extends BilliardRoom {
    double dist;

    RespBilliardRoom(String name, double lon, double lat, double dist) {
        super(name, lon, lat);
        this.dist = dist;
    }
}