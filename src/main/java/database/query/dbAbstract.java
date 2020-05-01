package database.query;

import database.dbConnection;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the functionalities of building, calling the queries and creating objects based on the results of the queries.
 * @param <T> The object to be worked with
 */

public class dbAbstract<T> {

    private final Class<T> type;

    /**
     * The constructor
     */
    @SuppressWarnings("unchecked")
    public dbAbstract() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * This method creates a SELECT query from the field given as argument.
     * @param field the database field
     * @return complete SELECT query
     */
    protected String createSelectQuery(String field) {
        return "SELECT * FROM " + type.getSimpleName() + " WHERE " + field + " = ?";
    }

    /**
     * This method creates a SELECT query from the fields given as argument.
     * @param fields the database fields
     * @return complete SELECT query
     */
    protected String createSelectQuery(String[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append(" * ");
        sb.append("FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        for (int i = 0; i < fields.length; i++) {
            sb.append(fields[i]);
            sb.append(" = ?");
            if (i < fields.length - 1) {
                sb.append(" AND ");
            }
        }
        return sb.toString();
    }

    /**
     * This method creates a INSERT query for the database.
     * @return complete INSERT query
     */
    public String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        Field[] f = type.getDeclaredFields();
        sb.append(" (");
        for (int i = 1; i < f.length; i++) {
            sb.append(f[i].getName());
            if (i < f.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        sb.append(" VALUES ");
        sb.append("(");
        for (int i = 1; i < f.length; i++) {
            sb.append("?");
            if (i < f.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * This method creates a DELETE query for the database.
     * @return complete INSERT query
     */
    protected String createDeleteQuery() {
        return "DELETE FROM " + type.getSimpleName() + " WHERE id = ?";
    }

    /**
     * This method creates a UPDATE query for the database with the given fields.
     * @param fields fields to be updated
     * @param id the row to be updated
     * @return
     */
    private String createUpdateQuery(Field[] fields, int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for (int i = 1; i < fields.length; i++) {
            sb.append(fields[i].getName());
            sb.append(" = ?");
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(" WHERE ");
        sb.append("id = ").append(id);
        return sb.toString();
    }

    /**
     * This method creates objects from the results of the query.
     * @param resultSet result of the query
     * @return list of objects expected from the given ResultSet
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * This method returns the object with the given id
     * @param id object's id
     * @return object found at the given id or null
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = dbConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close(resultSet);
            dbConnection.close(statement);
            dbConnection.close(connection);
        }
        return null;
    }

    /**
     * This method returns a list of objects from the table
     * @return list of objects from the database
     */
    public List<T> findAll() {
        List<T> res = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        try {
            connection = dbConnection.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            res = createObjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * This method inserts a given object in the database
     * @param object given object
     * @return id if successful or -1 if unsuccessful
     */
    public int insert(T object) {
        Connection dbConn = dbConnection.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConn.prepareStatement(createInsertQuery(), Statement.RETURN_GENERATED_KEYS);
            Field[] f = object.getClass().getDeclaredFields();
            for (int i = 1; i < f.length; i++) {
                f[i].setAccessible(true);
                Object value = f[i].get(object);
                if (value.getClass().getName().equals("java.lang.Integer")) {
                    insertStatement.setInt(i, f[i].getInt(object));
                } else {
                    if (value.getClass().getName().equals("java.lang.String")) {
                        insertStatement.setString(i, (String) f[i].get(object));
                    } else {
                        if (value.getClass().getName().equals("java.lang.Float")) {
                            insertStatement.setFloat(i, f[i].getFloat(object));
                        }
                    }
                }
            }
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close(insertStatement);
            dbConnection.close(dbConn);
        }

        return insertedId;
    }

    /**
     * This method deletes the object with the given id from the database
     * @param id object's id
     * @return return true if successful and false if unsuccessful
     */
    public boolean delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery();
        boolean status = false;
        try {
            connection = dbConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * This method updates the fields of the given object as the first parameter with the information from the object given as the second parameter.
     * @param object object to be updated
     * @param newObject new object
     * @return object's id if successful and -1 if unsuccessful
     */
    public int update(T object, T newObject) {
        Connection dbConn = dbConnection.getConnection();
        PreparedStatement updateStatement = null;
        int updatedId = -1;
        try {
            Field[] f = object.getClass().getDeclaredFields();
            Field[] nf = newObject.getClass().getDeclaredFields();
            nf[0].setAccessible(true);
            updateStatement = dbConn.prepareStatement(createUpdateQuery(nf, nf[0].getInt(object)), Statement.RETURN_GENERATED_KEYS);
            for (int i = 1; i < f.length; i++) {
                nf[i].setAccessible(true);
                Object value = nf[i].get(newObject);
                if (value.getClass().getName().equals("java.lang.Integer")) {
                    updateStatement.setInt(i, nf[i].getInt(newObject));
                } else {
                    if (value.getClass().getName().equals("java.lang.String")) {
                        updateStatement.setString(i, (String) nf[i].get(newObject));
                    } else {
                        if (value.getClass().getName().equals("java.lang.Float")) {
                            updateStatement.setFloat(i, nf[i].getFloat(newObject));
                        }
                    }
                }
            }
            updateStatement.executeUpdate();

            ResultSet rs = updateStatement.getGeneratedKeys();
            if (rs.next()) {
                updatedId = rs.getInt(1);
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            dbConnection.close(updateStatement);
            dbConnection.close(dbConn);
        }

        return updatedId;
    }
}
