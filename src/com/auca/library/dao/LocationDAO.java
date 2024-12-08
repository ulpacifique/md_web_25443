package com.auca.library.dao;

import com.auca.library.model.Location;
import com.auca.library.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO {

	private static List<Location> fetchLocations(String query, int parentId) {
	    List<Location> locations = new ArrayList<>();
	    String level = "";  // Define the level here as per the type of location

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        if (parentId != 0) stmt.setInt(1, parentId);
	        ResultSet rs = stmt.executeQuery();

	        // Determine level based on the query used
	        if (query.contains("provinces")) level = "Province";
	        else if (query.contains("districts")) level = "District";
	        else if (query.contains("sectors")) level = "Sector";
	        else if (query.contains("cells")) level = "Cell";
	        else if (query.contains("villages")) level = "Village";

	        while (rs.next()) {
	            locations.add(new Location(rs.getInt("id"), rs.getString("name"), level));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return locations;
	}

	public static List<Location> getProvinces() {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Location> getDistrictsByProvince(int provinceId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Location> getSectorsByDistrict(int districtId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Location> getCellsBySector(int sectorId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Location> getVillagesByCell(int cellId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Location> getLocationsByLevel(String level) {
		// TODO Auto-generated method stub
		return null;
	}

}
