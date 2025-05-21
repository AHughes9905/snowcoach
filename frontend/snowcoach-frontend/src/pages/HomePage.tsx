import React, { useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import MyPostsPreviewPage from "./MyPostsPreviewPage";

function HomePage() {
    const { user } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (!user) {
            navigate("/login");
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
            </ul>
        </div>
    );
}

export default HomePage;