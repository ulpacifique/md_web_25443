package com.auca.library.dao;

import com.auca.library.model.District;
import com.auca.library.model.Location;
import com.auca.library.util.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DistrictDAO {
    public void saveDistrict(District district) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(district);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

	
	

	public List<Location> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}