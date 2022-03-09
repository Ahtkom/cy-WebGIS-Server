package com.dbconn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @apiNote DBConn encapsulated the initialization of database context
 *          and datasource, the releases of connection, statement and resultset.
 *          Providing an easy method to execute sql query based on java.sql API.
 * 
 * @example
 *          DBConn dbConn = new DBConn();
 *          dbConn.connect();
 *          String result = dbConn.getQueryResult(sql: String);
 *          dbConn.close();
 */
public class DBConn {

    private Connection connection = null;
    private DataSource dataSource = null;
    private boolean isConnected = false;

    // Initialize connection from java database context
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

    // Release connection
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

    // Each row in ArrayList while each column in String[]
    public ArrayList<String[]> getQueryResult(String sql) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        if (isConnected) {
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql);

                int columns = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    String[] row = new String[columns];
                    for (int i = 1; i <= columns; i++) {
                        // Array.set(row, i - 1, rs.getString(i));
                        row[i - 1] = rs.getString(i);
                    }
                    result.add(row);
                }

                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}