package com.billiard.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import com.billiard.entity.PointInfo;
import com.billiard.operator.DistanceSelector;
import com.dbconn.DBConn;


public class BilliardRoomLoaderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        double lon = Double.parseDouble(req.getParameter("lon"));
        double lat = Double.parseDouble(req.getParameter("lat"));
        String tableName = "eleschool";

        ArrayList<RespBilliardRoom> rooms = new ArrayList<RespBilliardRoom>();

        DBConn dbConn = new DBConn();
        dbConn.connect();
        String sql = "select name, st_astext(geom) from " + tableName + ";";
        ArrayList<String[]> queryResult = dbConn.getQueryResult(sql);
        dbConn.close();

        ArrayList<PointInfo> schools = new ArrayList<PointInfo>();
        for (int i = 0; i < queryResult.size(); i++) {
            schools.add(new PointInfo(queryResult.get(i)[0], queryResult.get(i)[1]));
        }

        DistanceSelector distanceSelector = new DistanceSelector(lon, lat, 0.05, schools);

        resp.getWriter().println(distanceSelector.select());

        // DataSource ds = null;
        // Connection conn = null;
        // //#endregion
        // try {
        //     //#region
        //     Context initContext = new InitialContext();
        //     Context envContext = (Context) initContext.lookup("java:/comp/env");
        //     ds = (DataSource) envContext.lookup("jdbc/postgres");
        //     conn = ds.getConnection();
        //     //#endregion
        //     if (conn != null) {
        //         Statement st = conn.createStatement();

        //         String sql = "select name, st_astext(geom) from " + tableName + ";";
        //         ResultSet rs = st.executeQuery(sql);

        //         ArrayList<PointInfo> schools = new ArrayList<PointInfo>();
        //         while (rs.next()) {
        //             schools.add(new PointInfo(rs.getString(1), rs.getString(2)));
        //         }
        //         // Gson gson = new Gson();
        //         DistanceSelector distanceSelector = new DistanceSelector(lon, lat, 0.05, schools);

        //         resp.getWriter().println(distanceSelector.select());

        //         // String sql = "select name, st_x(geom), st_y(geom), " +
        //         //         "st_distance(geom, 'POINT(" + lon + " " + lat + ")'::geography, true) " +
        //         //         "from " + tableName +
        //         //         " where st_dwithin(geom, 'POINT(" + lon + " " + lat + ")'::geography, 10000);";

        //         // ResultSet rs = st.executeQuery(sql);
        //         // while (rs.next()) {
        //         //     rooms.add(
        //         //         new RespBilliardRoom(
        //         //             rs.getString(1),
        //         //             rs.getDouble(2),
        //         //             rs.getDouble(3),
        //         //             rs.getDouble(4)
        //         //         )
        //         //     );
        //         // }
        //         rs.close();
        //         st.close();
        //         conn.close();
        //     }
        // } catch (NamingException | SQLException e) {
        //     e.printStackTrace();
        // }

        // Gson gson = new Gson();
        // String json = gson.toJson(rooms);

        // resp.getWriter().println(json);
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
}

class RespBilliardRoom extends BilliardRoom {
    double dist;

    RespBilliardRoom(String name, double lon, double lat, double dist) {
        super(name, lon, lat);
        this.dist = dist;
    }
}


// class School {
//     String name;
//     double lon, lat, dist;

//     School(String name, double lon, double lat, double dist) {
//         this.name = name;
//         this.lon = lon;
//         this.lat = lat;
//         this.dist = dist;
//     }
// }