package com.auca.library.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.auca.library.model.Member;
import com.auca.library.model.MembershipRequest;
import com.auca.library.util.HibernateUtil;

import java.util.List;

public class MemberDao {

    /**
     * Saves a new member and creates an associated membership request.
     * @param member the member to be saved.
     */
    public void saveMember(Member member) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Save the member
            session.save(member);
            
            // Create and save the membership request
            MembershipRequest request = new MembershipRequest();
            request.setMember(member);
            request.setMembershipType(member.getMembershipType());
            request.setStatus("PENDING");
            session.save(request);
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a member by their ID.
     * @param id the member's ID.
     * @return the Member object, or null if not found.
     */
    public Member getMemberById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Member.class, id);
        }
    }

    /**
     * Retrieves a list of membership requests with a "PENDING" status.
     * @return list of pending membership requests.
     */
    public List<MembershipRequest> getPendingRequests() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MembershipRequest> query = session.createQuery(
                "FROM MembershipRequest WHERE status = :status", MembershipRequest.class);
            query.setParameter("status", "PENDING");
            return query.list();
        }
    }

    /**
     * Updates the status of a membership request and approves the associated member if needed.
     * @param requestId the ID of the membership request.
     * @param status the new status to set (e.g., "APPROVED", "REJECTED").
     */
    public void updateMembershipStatus(int requestId, String status) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            MembershipRequest request = session.get(MembershipRequest.class, requestId);
            if (request != null) {
                // Update request status
                request.setStatus(status);
                
                // Approve the member if the request is approved
                Member member = request.getMember();
                boolean isApproved = "APPROVED".equalsIgnoreCase(status);
                member.setApproved(isApproved);
                member.setApprovalStatus(status);
                
                // Update both the request and the member in the database
                session.update(request);
                session.update(member);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Checks if a given email is already registered in the database.
     * @param email the email to check.
     * @return true if the email is registered, false otherwise.
     */
    public boolean isEmailRegistered(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(m) FROM Member m WHERE m.email = :email", Long.class);
            query.setParameter("email", email);
            return query.uniqueResult() > 0;
        }
    }

	public void saveMember(Member member, Session session) {
		// TODO Auto-generated method stub
		
	}
}
