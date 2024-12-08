package com.auca.library.servlet;

import com.auca.library.dao.*;
import com.auca.library.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createLocation")
public class CreateLocationServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProvinceDAO provinceDAO;
    private DistrictDAO districtDAO;
    private SectorDAO sectorDAO;
    private CellDAO cellDAO;
    private VillageDAO villageDAO;

    public void init() {
        provinceDAO = new ProvinceDAO();
        districtDAO = new DistrictDAO();
        sectorDAO = new SectorDAO();
        cellDAO = new CellDAO();
        villageDAO = new VillageDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String locationType = request.getParameter("locationType");
        String name = request.getParameter("locationName");
        boolean success = false;
        String message = "";

        try {
            switch (locationType) {
            
                case "province":
                    Province province = new Province(name);
                    provinceDAO.saveProvince(province);
                    success = true;
                    message = "Location created successfully!";
                    break;
                case "district":
                    int provinceIdForDistrict = Integer.parseInt(request.getParameter("parentId"));
                    Province provinceForDistrict = new Province();
                    provinceForDistrict.setId(provinceIdForDistrict);
                    District district = new District(name, provinceForDistrict);
                    districtDAO.saveDistrict(district);
                    success = true;
                    message = "Location created successfully!";
                    break;
                case "sector":
                    int districtIdForSector = Integer.parseInt(request.getParameter("parentId"));
                    District districtForSector = new District();
                    districtForSector.setId(districtIdForSector);
                    Sector sector = new Sector(name, districtForSector);
                    sectorDAO.saveSector(sector);
                    success = true;
                    message = "Location created successfully!";
                    break;
                case "cell":
                    int sectorIdForCell = Integer.parseInt(request.getParameter("parentId"));
                    Sector sectorForCell = new Sector();
                    sectorForCell.setId(sectorIdForCell);
                    Cell cell = new Cell(name, sectorForCell);
                    cellDAO.saveCell(cell);
                    success = true;
                    message = "Location created successfully!";
                    break;
                case "village":
                    int cellIdForVillage = Integer.parseInt(request.getParameter("parentId"));
                    Cell cellForVillage = new Cell();
                    cellForVillage.setId(cellIdForVillage);
                    Village village = new Village(name, cellForVillage);
                    villageDAO.saveVillage(village);
                    success = true;
                    message = "Location created successfully!";
                    break;
                default:
                    // Handle invalid location type
                    break;
                   
                }
            } catch (Exception e) {
                    message = "An error occurred while creating the location.";
                }

                // Return JSON response
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String json = "{\"success\": " + success + ", \"message\": \"" + message + "\"}";
                response.getWriter().write(json);
    }        
}