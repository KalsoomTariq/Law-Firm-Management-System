package application;

import java.sql.*;

public class Database {
    private static Database instance;

    private String connectionString = "jdbc:mysql://localhost:3306/LawFirmMS?user=root&password=0000";
    private Connection connection = null;

    private Database() {}

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public boolean open() {
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    public boolean close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean insertData(String tableName, String[] columns, Object[] values) {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
        queryBuilder.append(tableName).append(" (");
        
        for (int i = 0; i < columns.length; i++) {
        	queryBuilder.append("`").append(columns[i]).append("`");
            if (i < columns.length - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(") VALUES (");

        for (int i = 0; i < values.length; i++) {
            queryBuilder.append("?");
            if (i < values.length - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(")");
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {        	
        	for (int i = 0; i < values.length; i++) {
        		preparedStatement.setObject(i + 1, values[i]);
        	}

            preparedStatement.executeUpdate();
            
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
        	return false;
        }
    }

    public ResultSet readData(String tableName, String[] columns, String whereClause) {
        StringBuilder queryBuilder = new StringBuilder("SELECT ");

        for (int i = 0; i < columns.length; i++) {
            queryBuilder.append(columns[i]);
            if (i < columns.length - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(" FROM ").append(tableName);

        if (whereClause != null && !whereClause.isEmpty()) {
            queryBuilder.append(" WHERE ").append(whereClause);
        }

        try {
            Statement statement = connection.createStatement();
            
            return statement.executeQuery(queryBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateData(String tableName, String[] columns, Object[] values, String whereClause) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE ");
        queryBuilder.append(tableName).append(" SET ");

        for (int i = 0; i < columns.length; i++) {
            queryBuilder.append(columns[i]).append(" = ?");
            if (i < columns.length - 1) {
                queryBuilder.append(", ");
            }
        }

        if (whereClause != null && !whereClause.isEmpty()) {
            queryBuilder.append(" WHERE ").append(whereClause);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }
            
            preparedStatement.executeUpdate();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteData(String tableName, String whereClause) {
        String query = "DELETE FROM " + tableName;

        if (whereClause != null && !whereClause.isEmpty()) {
            query += " WHERE " + whereClause;
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getRowCount(String tableName) {
        String query = "SELECT COUNT(*) FROM " + tableName;
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public boolean printResultSet(ResultSet rs) {
    	try {
    		ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnName = rsmd.getColumnName(i);
                System.out.print(columnName);
            }
            System.out.println("");

            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }
            
            return true;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

}
