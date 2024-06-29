//package org.example.liquibase;
//
//import liquibase.Liquibase;
//import liquibase.database.Database;
//import liquibase.database.DatabaseFactory;
//import liquibase.database.jvm.JdbcConnection;
//import liquibase.exception.LiquibaseException;
//import liquibase.resource.FileSystemResourceAccessor;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class LiquibaseRunner {
//
//    private static final String URL = "jdbc:postgresql://postgres_container:5432/efficient_work";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "password";
//    private static final String CHANGELOG_PATH = "/liquibase/changelog/changelog.xml";
//
//    public void runMigrations() {
//
//        try {
//            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            Database database =
//                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
//            Liquibase liquibase =
//                    new Liquibase(CHANGELOG_PATH, new FileSystemResourceAccessor(), database);
//
//            liquibase.update();
//            System.out.println("Migration is completed successfully");
//        }
//        catch (SQLException | LiquibaseException e) {
//            System.out.println("SQL Exception in migration " + e.getMessage());
//        }
//    }
//}
