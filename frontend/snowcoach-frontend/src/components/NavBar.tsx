import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function NavBar() {
    const { user, logout } = useAuth();

    // Helper to check for a role
    const hasRole = (role: string) => user?.roles?.includes(role);

    return (
        <nav className="navbar">
            <div className="navbar-links">
                <Link to="/">Home</Link>
                <Link to="/create">Create Post</Link>
                {hasRole("ROLE_COACH1") && <Link to="/claimed-posts">My Claimed Posts</Link>}
            </div>
            <div className="navbar-auth">
                {user ? (
                    <>
                        <span>Welcome, {user.username}</span>
                        <button onClick={logout}>Logout</button>
                    </>
                ) : (
                    <Link to="/login">Login</Link>
                )}
            </div>
        </nav>
    );
}

export default NavBar;