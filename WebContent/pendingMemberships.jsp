<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.auca.library.model.Member" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pending Memberships</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        body {
            background: #f0f4f8;
            color: #333;
            padding: 2rem;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 6px 18px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            font-size: 2rem;
            color: #2c3e50;
            margin-bottom: 1.5rem;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1.5rem;
        }

        th, td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #3498db;
            color: white;
            font-weight: 600;
        }

        tr:nth-child(even) {
            background: #f8f9fa;
        }

        tr:hover {
            background: #e3f2fd;
        }

        .status {
            padding: 0.3rem 0.6rem;
            border-radius: 4px;
            font-weight: 500;
            display: inline-block;
            text-align: center;
        }

        .status.pending {
            background: #f39c12;
            color: white;
        }

        .status.approved {
            background: #2ecc71;
            color: white;
        }

        .status.rejected {
            background: #e74c3c;
            color: white;
        }

        .action-buttons {
            display: flex;
            gap: 0.5rem;
        }

        .btn {
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 0.9rem;
            color: white;
            text-decoration: none;
            display: inline-block;
        }

        .btn-approve {
            background: #3498db;
        }

        .btn-reject {
            background: #e74c3c;
        }

        .btn:hover {
            transform: scale(1.05);
        }

        @media (max-width: 768px) {
            th, td {
                padding: 0.8rem;
                font-size: 0.9rem;
            }

            .btn {
                padding: 0.6rem 1.2rem;
            }
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>Pending Membership Requests</h1>

        <table>
            <thead>
                <tr>
                    <th>Member ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Membership Type</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Retrieve the pending members
                    List<Member> pendingMembers = (List<Member>) request.getAttribute("pendingMembers");
                    for (Member member : pendingMembers) {
                %>
                <tr>
                    <td><%= member.getId() %></td>
                    <td><%= member.getFirstName() %></td>
                    <td><%= member.getLastName() %></td>
                    <td><%= member.getEmail() %></td>
                    <td><%= member.getMembershipType() %></td>
                    <td>
                        <span class="status <%= member.getApprovalStatus().toLowerCase() %>">
                            <%= member.getApprovalStatus() %>
                        </span>
                    </td>
                    <td>
                        <form method="post" action="pendingMemberships">
                            <input type="hidden" name="memberId" value="<%= member.getId() %>"/>
                            <button type="submit" name="action" value="Approve" class="btn btn-approve">Approve</button>
                            <button type="submit" name="action" value="Reject" class="btn btn-reject">Reject</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

</body>
</html>
