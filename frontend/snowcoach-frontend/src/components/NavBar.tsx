import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function NavBar() {
    const { user, logout } = useAuth(); // Access user info and logout function

    return (
        <nav className="navbar">
            <div className="navbar-links">
                <Link to="/">Home</Link>
                <Link to="/create">Create Post</Link>
                <Link to="/claimed-posts">My Claimed Posts</Link>
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