
import Utils.Driver;

import java.sql.*;
import java.util.ArrayList;

public class Connector {
    Connector(String url, Driver driver, String usr, String pwd){
        if (url!=null) this.url += url;
        if (driver!=null)this.driver = driver;
        if (usr!=null)this.usr = usr;
        if (pwd!=null)this.pwd = pwd;
        res = false;
        try {
            Class.forName(driver.getFullName());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            this.connection = DriverManager.getConnection(url, usr, pwd);
            res = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    Connector(String url, Driver driver){
        this(url, driver, null, null);
    }

    public boolean Done() {
        return res;
    }

    public ArrayList<String> getTableNames() {
        try {
            ArrayList mas = new ArrayList<String>();
            DatabaseMetaData dbm = this.connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = null;
            rs = dbm.getTables(null, null, "%", types);
            while (rs.next()) {
                String table = rs.getString("TABLE_NAME");
                mas.add(table);
            }
            return mas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Table getTable(String tablename) {
        Table table = new Table();
        table.setName(tablename);
        try {
            ArrayList pkeys = new ArrayList();
            DatabaseMetaData dbm = this.connection.getMetaData();
            ResultSet rs = dbm.getPrimaryKeys("", "", tablename);
            while (rs.next()) {
                pkeys.add(rs.getString("COLUMN_NAME"));
            }
            table.setPrimaryKeys(pkeys);

            //getting foreign keys
            ArrayList fkeys = new ArrayList();
            rs = dbm.getExportedKeys("", "", tablename);
            while (rs.next()) {
                fkeys.add(rs.getString("COLUMN_NAME"));
            }
            table.setForeignKeys(fkeys);

            //getting line count
            Statement st = cn.createStatement();
            int lineCount = 0;
            rs = st.executeQuery("select count(*) from " + tablename);
            while (rs.next()) {
                lineCount = rs.getInt(1);
            }
            table.setLineCount(lineCount);

            //getting column count
            rs = st.executeQuery("select * from " + tablename);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = 0;
            columnCount = md.getColumnCount();
            table.setColumnCount(columnCount);

            //getting columnTypes
            ArrayList types = new ArrayList();
            for (int i = 0; i < columnCount; i++) {
                String type = md.getColumnTypeName(i + 1);
//                if (type.indexOf("VARCHAR") >= 0) {
//                    type += "(30)";
//                }
                types.add(type);
            }
            table.setColumnTypes(types);

            //getting column names
            ArrayList names = new ArrayList();
            names = new ArrayList();
            for (int i = 0; i < columnCount; i++) {
                names.add(md.getColumnName(i + 1));
            }
            table.setColumnNames(names);

        } catch (Exception ex) {
            //ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException ex) {
                //ex.printStackTrace();
            }
        }
        return table;
    }


    private String url= "jdbc:mysql://localhost:3306/";
    private Driver driver = Driver.h2;
    private String usr = "root";
    private String pwd = "";
    private Connection connection = null;
    private boolean res;
}
