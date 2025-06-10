import { useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";

function AuthenticatePage() {
    const navigate = useNavigate();

    useEffect(() => {
        // Check if the user is already authenticated
        const checkAuth = async () => {
            const response = await fetch("/api/auth/check", {
                method: "GET",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (response.ok) {
                navigate("/"); // Redirect to home if authenticated
            }
        };

        checkAuth();
    }, [navigate]);

    return (
        <div className="authenticate-page">
            <h1>Authenticate</h1>
            <p>Please log in or register</p>
            <li><Link to="/login">Login</Link></li>
            <li><Link to="/register">Register</Link></li>
        </div>
    );
}

export default AuthenticatePage;