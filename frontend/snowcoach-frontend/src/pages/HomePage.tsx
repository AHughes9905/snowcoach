import React, { useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function HomePage() {
    const { user } = useAuth();
    const navigate = useNavigate();

    // Helper to check for a role
    const hasRole = (role: string) => user?.roles?.includes(role);

    useEffect(() => {
        if (!user) {
            navigate("/authenticate"); // Redirect to authenticate page if not logged in
        }
    }, [user, navigate]);

    if (!user) return null; // Prevent flicker

    return (
        <div className="home-page">
            <h1>Welcome, {user.username}!</h1>
            <ul>
                <li><Link to="/claimed-posts">My Claimed Posts</Link></li>
                <li><Link to="/my-posts">My Posts</Link></li>
                <li><Link to="/preview">Preview Page</Link></li>
                <li><Link to="/create">Create Post</Link></li>
                {hasRole("ROLE_ADMIN") && <Link to="/admin">Admin Page</Link>}
            </ul>
        </div>
    );
}

export default HomePage;