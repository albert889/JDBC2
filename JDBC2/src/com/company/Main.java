package com.company;

import java.sql.*;

public class Main {

    private static Connection conn = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static final String TABLE_LAPTOP = "laptop";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_MODEL = "model";
    private static final String COLUMN_QTY = "qty";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_YEARS = "year";



    public static void main(String[] args) {

        try{
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/computer_shop", "root","");
            statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS laptop (id INT KEY, brand VARCHAR(30), " +
                    "model VARCHAR(30), qty INT, price FLOAT)");

            String search[] = {"SELECT model FROM laptop WHERE id=1",
            "SELECT model FROM laptop WHERE LOWER(model) LIKE LOWER('Asus_%')",
            "SELECT model FROM laptop WHERE LOWER(model) LIKE LOWER('Asus%')"};

            for(String s: search){
                resultSet = statement.executeQuery(s);
                System.out.println("Models for query " + s + " are");
                while(resultSet.next()){
                    String model = resultSet.getString(COLUMN_MODEL);
                    System.out.println(model + " ");
                }
                System.out.println();
            }

            String sort = "SELECT * FROM laptop ORDER BY model";
            String sort1 = "SELECT * FROM laptop ORDER BY year , model";

            resultSet = statement.executeQuery(sort);
            System.out.println("Table content sorted by Model");
            System.out.println("ID" + " || " + "BRAND" + " || " + "MODEL" + " || " + "YEARS" + " || " + "QTY" + " || " +
                    "PRICE");

            while(resultSet.next()){
                System.out.println(resultSet.getInt(COLUMN_ID) + " || " +
                        resultSet.getString(COLUMN_BRAND) + " || " +
                        resultSet.getString(COLUMN_MODEL) + " || " +
                        resultSet.getString(COLUMN_YEARS) + " || " +
                        resultSet.getInt(COLUMN_QTY) + " || " +
                        resultSet.getFloat(COLUMN_PRICE));
            }

            resultSet = statement.executeQuery(sort1);
            System.out.println("Table content sorted by Year & Model");
            System.out.println("ID" + " || " + "BRAND" + " || " + "MODEL" + " || " + "YEARS" + " || " + "QTY" + " || " +
                    "PRICE");

            while(resultSet.next()){
                System.out.println(resultSet.getInt(COLUMN_ID) + " || " +
                        resultSet.getString(COLUMN_BRAND) + " || " +
                        resultSet.getString(COLUMN_MODEL) + " || " +
                        resultSet.getString(COLUMN_YEARS) + " || " +
                        resultSet.getInt(COLUMN_QTY) + " || " +
                        resultSet.getFloat(COLUMN_PRICE));
            }

        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
        }finally {
            try {
                resultSet.close();
                statement.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }
}
