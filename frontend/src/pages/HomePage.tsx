import { useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import "./HomePage.css";

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
            <div className="home-options">
                <div className="home-option-card">
                    <Link to="/claimed-posts">My Claimed Posts</Link>
                </div>
                <div className="home-option-card">
                    <Link to="/my-posts">My Posts</Link>
                </div>
                <div className="home-option-card">
                    <Link to="/preview">Preview Page</Link>
                </div>
                <div className="home-option-card">
                    <Link to="/create">Create Post</Link>
                </div>
                {hasRole("ROLE_ADMIN") && (
                    <div className="home-option-card">
                        <Link to="/admin">Admin Page</Link>
                    </div>
                )}
            </div>
        </div>
    );
}

export default HomePage;