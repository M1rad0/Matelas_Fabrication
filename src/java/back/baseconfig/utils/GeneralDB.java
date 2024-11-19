package back.baseconfig.utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import back.baseconfig.annotations.*;
import java.lang.reflect.Method;

public class GeneralDB<T> {
    private final Class<T> type;

    public GeneralDB(Class<T> type) {
        this.type = type;
    }

    // Fonction globale pour récupérer un objet de type T à partir d'un ResultSet
    public T getObject(ResultSet resultSet) throws Exception {
        T instance = type.getDeclaredConstructor().newInstance();
        Field[] fields = allFields(type);

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                String columnName = column.name();
                String setterName = "set" + capitalize(field.getName());
                Method setter = type.getMethod(setterName, field.getType());
                
                Object value;
                if (field.getType() == int.class) {
                    value = resultSet.getInt(columnName);
                } else if (field.getType() == double.class) {
                    value = resultSet.getDouble(columnName);
                } else {
                    value = resultSet.getObject(columnName);
                }
                
                setter.invoke(instance, value);
            }
        }
        return instance;
    }

    public ArrayList<T> getObjects(ResultSet rs) throws Exception{
        ArrayList<T> toReturn=new ArrayList<T>();

        try (ResultSet resultSet = rs) {
            while (resultSet.next()) {
                T instance = getObject(resultSet);
                toReturn.add(instance);
            }
        }

        return toReturn;
    }

    public ArrayList<T> getObjects(Connection conn, String query, Object... params) throws SQLException {
        ArrayList<T> results = new ArrayList<>();
    
        try (PreparedStatement stat = conn.prepareStatement(query)) {
            // Assigner les paramètres dans la requête préparée
            for (int i = 0; i < params.length; i++) {
                stat.setObject(i + 1, params[i]);
            }
    
            try (ResultSet rs = stat.executeQuery()) {
                // Parcourir le ResultSet et utiliser getObject pour chaque enregistrement
                while (rs.next()) {
                    T instance = getObject(rs); // Utilise la fonction getObject pour mapper le ResultSet en objet T
                    results.add(instance);
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error retrieving objects: " + e.getMessage(), e);
        }
    
        return results;
    }
    

    // Récupérer la colonne qui est primary key
    static String getPrimaryKeyColumn(Connection conn, String table) throws SQLException {
        try (ResultSet rs = conn.getMetaData().getPrimaryKeys(null, null, table)) {
            if (rs.next()) {
                return rs.getString("COLUMN_NAME");
            }
        }
        return null;
    }

    // Récupérer tous les fields des mères
    static Field[] allFields(Class<?> objClass) {
        ArrayList<Field> lsFields = new ArrayList<>();
        while (objClass != Object.class) {
            for (Field field : objClass.getDeclaredFields()) {
                lsFields.add(field);
            }
            objClass = objClass.getSuperclass();
        }
        return lsFields.toArray(new Field[0]);
    }

    // CREATE: Ajout d'une nouvelle entrée dans la base de données
    public void create(Connection conn, T entity) throws SQLException, IllegalAccessException {
        Table table = type.getAnnotation(Table.class);
        if (table == null) throw new RuntimeException("No Table annotation present.");

        StringBuilder query = new StringBuilder("INSERT INTO " + table.name() + " (");
        StringBuilder values = new StringBuilder("VALUES (");

        Field[] fields = allFields(type);
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
            if (column != null && defaultValue == null) {
                query.append(column.name()).append(",");
                values.append("?,");

            }
        }

        query.setLength(query.length() - 1); // Remove the last comma
        values.setLength(values.length() - 1); // Remove the last comma
        query.append(") ").append(values).append(")");

        try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
                if (column != null && defaultValue == null) {
                    stmt.setObject(index++, field.get(entity));
                }
            }
            stmt.executeUpdate();
        }
    }
    
    // CREATE: Ajout d'une nouvelle entrée dans la base de données
    public void create(Connection conn, List<T> entities,int batchSize) throws SQLException, IllegalAccessException {
        Table table = type.getAnnotation(Table.class);
        if (table == null) throw new RuntimeException("No Table annotation present.");

        StringBuilder query = new StringBuilder("INSERT INTO " + table.name() + " (");
        StringBuilder values = new StringBuilder("VALUES (");

        Field[] fields = allFields(type);
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
            if (column != null && defaultValue == null) {
                query.append(column.name()).append(",");
                values.append("?,");

            }
        }

        query.setLength(query.length() - 1); // Remove the last comma
        values.setLength(values.length() - 1); // Remove the last comma
        query.append(") ").append(values).append(")");

        try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int count=0;
            for(T entity:entities){
                int index = 1;

                for (Field field : fields) {
                    field.setAccessible(true);
                    Column column = field.getAnnotation(Column.class);
                    DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
                    if (column != null && defaultValue == null) {
                        stmt.setObject(index++, field.get(entity));
                    }
                }
                
                stmt.addBatch();
                System.out.println(count);
                count++;

                if(count%batchSize==0){
                    stmt.executeBatch();
                }
            }
            
            stmt.executeBatch();
        }
    }

    // UPDATE: Mise à jour d'une entrée dans la base de données
    public void update(Connection conn, T entity, String id) throws Exception {
        Table table = type.getAnnotation(Table.class);
        if (table == null) throw new RuntimeException("No Table annotation present.");

        StringBuilder query = new StringBuilder("UPDATE " + table.name() + " SET ");
        Field[] fields = allFields(type);

        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                query.append(column.name()).append(" = ?,");
            }
        }

        query.setLength(query.length() - 1); // Remove the last comma
        query.append(" WHERE " + "id_bloc" + " = ?");
        
        System.out.println(query);
        
        try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    stmt.setObject(index++, field.get(entity));
                }
            }
            stmt.setString(index, id); // Set the primary key value
            stmt.executeUpdate();
        }
    }

    // DELETE: Suppression d'une entrée
    public void delete(Connection conn, String id) throws SQLException {
        Table table = type.getAnnotation(Table.class);
        if (table == null) throw new RuntimeException("No Table annotation present.");

        String query = "DELETE FROM " + table.name() + " WHERE " + getPrimaryKeyColumn(conn, table.name()) + " = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    // Méthode pour récupérer tous les enregistrements avec une condition WHERE
    public List<T> getWhere(Connection conn, String whereClause, Object... params) throws Exception {
        List<T> results = new ArrayList<>();
        String tableName = type.getAnnotation(Table.class).name();
        String sql = "SELECT * FROM " + tableName + " WHERE " + whereClause;

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    T instance = getObject(resultSet);
                    results.add(instance);
                }
            }
        }
        return results;
    }

    // READ: Lecture d'une entrée à partir de son identifiant
    public T read(Connection conn, String id) throws Exception {
        Table table = type.getAnnotation(Table.class);
        if (table == null) throw new RuntimeException("No Table annotation present.");

        String query = "SELECT * FROM " + table.name() + " WHERE " + getPrimaryKeyColumn(conn, table.name()) + " = ?";
        T entity = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    entity = getObject(resultSet);
                }
            }
        }
        return entity;
    }

    // FIND ALL: Récupération de toutes les entrées
    public List<T> findAll(Connection conn) throws Exception {
        Table table = type.getAnnotation(Table.class);
        if (table == null) throw new RuntimeException("No Table annotation present.");

        String query = "SELECT * FROM " + table.name();
        List<T> results = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                T instance = getObject(resultSet);
                results.add(instance);
            }
        }
        return results;
    }

    // Méthode auxiliaire pour capitaliser la première lettre
    private String capitalize(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}