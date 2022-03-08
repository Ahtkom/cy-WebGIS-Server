package com.dbconn;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConn {
    String sql;
    Connection connection = null;
    DataSource dataSource = null;
    boolean isConnected = false;

    public void connect() {
        if (!isConnected) {
            try {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                dataSource = (DataSource) envContext.lookup("jdbc/postgres");
                connection = dataSource.getConnection();
                isConnected = true;
            } catch (NamingException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        if (isConnected) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            isConnected = false;
        }
    }

    public ArrayList<String[]> getQueryResult(String sql) {
        ArrayList<String[]> res = new ArrayList<String[]>();
        if (isConnected) {
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql);

                int columns = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    String[] row = new String[columns];
                    for (int i = 1; i <= columns; i++) {
                        Array.set(row, i - 1, rs.getString(i));
                    }
                    res.add(row);
                }
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
