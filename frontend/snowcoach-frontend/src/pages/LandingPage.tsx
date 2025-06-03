import React from "react";
import { Link } from "react-router-dom";

function LandingPage() {
    return (
        <div className="landing-page">
            <h1>Welcome to SnowCoach!</h1>
            <p>
                SnowCoach connects you with ski and snowboard coaches to help you improve your skiing and riding.
                Whether you're a beginner or an advanced rider, SnowCoach can connected you to a coach who can help you take your skills to the next level.
            </p>
            <div>
                <Link to="/login" className="btn">Login</Link>
                <span style={{ margin: "0 10px" }}></span>
                <Link to="/register" className="btn">Register</Link>
            </div>
        </div>
    );
}

export default LandingPage;