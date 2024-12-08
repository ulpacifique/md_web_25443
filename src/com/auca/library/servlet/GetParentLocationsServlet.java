package com.auca.library.servlet;
import com.auca.library.dao.*;
import com.auca.library.model.*;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getParentLocations")
public class GetParentLocationsServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProvinceDAO provinceDAO;
    private DistrictDAO districtDAO;
    private SectorDAO sectorDAO;
    private CellDAO cellDAO ;
    private VillageDAO villageDAO;

    @Override
    public void init() {
        provinceDAO = new ProvinceDAO();
        districtDAO = new DistrictDAO();
        sectorDAO = new SectorDAO();
        cellDAO = new CellDAO();
        villageDAO = new VillageDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String locationType = req.getParameter("locationType");
        List<Location> parentLocations = getParentLocations(locationType);
        String json = new Gson().toJson(parentLocations);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    private List<Location> getParentLocations(String locationType) {
        switch (locationType) {
            case "district":
                return districtDAO.getAll();
            case "sector":
                return sectorDAO.getAll();
            case "cell":
                return cellDAO.getAll();
            case "village":
                return villageDAO.getAll();
            default:
                return provinceDAO.getAll();
        }
    }
}