package com.auca.library.dao;

import com.auca.library.model.Location;
import com.auca.library.model.Province;
import com.auca.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProvinceDAO {

    public void saveProvince(Province province) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(province);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Province> getAllProvinces() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Province", Province.class).list();
        }
    }

	public List<Location> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}