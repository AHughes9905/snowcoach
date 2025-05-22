import React, { useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function AdminPage() {
    const { user } = useAuth();
    const navigate = useNavigate();

    // Helper to check for a role
    const hasRole = (role: string) => user?.roles?.includes(role);

    useEffect(() => {
        if (!hasRole("ROLE_ADMIN")) {
            navigate("/"); // Redirect to home page if not an admin
        }
    }, [user, navigate]);

    if (!user) return null; // Prevent flicker

    return (
        <div className="admin-page">
            <h1>Admin Page</h1>
            <ul>
                <li><Link to="/list-users">List Users</Link></li>
            </ul>
        </div>
    );
}

export default AdminPage;