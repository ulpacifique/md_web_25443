package com.auca.library.dao;

import com.auca.library.model.Member;
import com.auca.library.model.MembershipRequest;
import com.auca.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MembershipDAO {

    // Retrieves all members with a pending approval status
    public List<Member> getPendingMembers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Member> query = session.createQuery(
                "FROM Member WHERE approvalStatus = :status", Member.class);
            query.setParameter("status", "PENDING");
            return query.list();
        }
    }

    // Updates the approval status and approval flag for a member
    public void updateMembershipStatus(int memberId, String status) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Retrieve the member by ID
            Member member = session.get(Member.class, memberId);
            if (member != null) {
                // Set the new status and update isApproved flag
                member.setApprovalStatus(status);
                member.setIsApproved("APPROVED".equals(status)); // Update isApproved based on status
                session.update(member);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error updating membership status", e);
        }
    }

    // Save a new membership request
    public void saveMembershipRequest(MembershipRequest membershipRequest) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(membershipRequest);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error saving membership request", e);
        }
    }

    // You can remove or implement these methods if needed
    public List<MembershipRequest> getPendingRequests() {
        // Implementation if needed
        return null;
    }

    public void updateMembershipStatus(int requestId, String status, String membershipType) {
        // Implementation if needed
    }
}