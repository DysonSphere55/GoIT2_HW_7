package goit.database;

import goit.pojo.*;
import goit.prefs.Prefs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private final Prefs prefs;
    private final Connection connection;

    public DatabaseQueryService(Connection connection) {
        this.connection = connection;
        prefs = new Prefs();
    }

    public List<LongestProject> findLongestProject() {
        List<LongestProject> result = new ArrayList<>();
        try {
            String sqlQuery = getSqlQueryFromFileName(Prefs.FIND_LONGEST_PROJECT_QUERY_FILE);
            PreparedStatement preparedSt = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                result.add(new LongestProject(
                        rs.getString("name"),
                        rs.getInt("month_count")));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<MaxSalaryWorker> findMaxSalaryWorker() {
        List<MaxSalaryWorker> result = new ArrayList<>();
        try {
            String sqlQuery = getSqlQueryFromFileName(Prefs.FIND_MAX_SALARY_WORKER_QUERY_FILE);
            PreparedStatement preparedSt = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                result.add(new MaxSalaryWorker(
                        rs.getString("name"),
                        rs.getLong("salary")));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<MaxProjectsClient> findMaxProjectsClient() {
        List<MaxProjectsClient> result = new ArrayList<>();
        try {
            String sqlQuery = getSqlQueryFromFileName(Prefs.FIND_MAX_PROJECTS_CLIENT_QUERY_FILE);
            PreparedStatement preparedSt = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                result.add(new MaxProjectsClient(
                        rs.getString("name"),
                        rs.getInt("count_project")));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<EldestWorker> findEldestWorker() {
        List<EldestWorker> result = new ArrayList<>();
        try {
            String sqlQuery = getSqlQueryFromFileName(Prefs.FIND_YOUNGEST_ELDEST_WORKER_QUERY_FILE);
            PreparedStatement preparedSt = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                if (rs.getString("type").equals("Eldest")) {
                    result.add(new EldestWorker(
                            rs.getString("type"),
                            rs.getString("name"),
                            LocalDate.parse(rs.getString("birthday"))));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<YoungestWorker> findYoungestWorker() {
        List<YoungestWorker> result = new ArrayList<>();
        try {
            String sqlQuery = getSqlQueryFromFileName(Prefs.FIND_YOUNGEST_ELDEST_WORKER_QUERY_FILE);
            PreparedStatement preparedSt = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                if (rs.getString("type").equals("Youngest")) {
                    result.add(new YoungestWorker(
                            rs.getString("type"),
                            rs.getString("name"),
                            LocalDate.parse(rs.getString("birthday"))));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<ProjectPrice> getProjectPrices() {
        List<ProjectPrice> result = new ArrayList<>();
        try {
            String sqlQuery = getSqlQueryFromFileName(Prefs.GET_PROJECT_PRICE_QUERY_FILE);
            PreparedStatement preparedSt = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedSt.executeQuery();
            while (rs.next()) {
                result.add(new ProjectPrice(
                        rs.getString("name"),
                        rs.getLong("price")
                ));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getSqlQueryFromFileName(String prefsKey) throws IOException {
        String fileName = prefs.getValue(prefsKey);
        return String.join("\n", Files.readAllLines(Paths.get(fileName)));
    }
}
