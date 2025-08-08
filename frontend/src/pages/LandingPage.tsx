import { Link } from "react-router-dom";

function LandingPage() {
    return (
        <div className="landing-page">
            <h1>Welcome to SnowCoach!</h1>
            <p>
                SnowCoach connects you with ski and snowboard coaches to help you improve your skiing and riding.
                Whether you're a beginner or experienced, SnowCoach can connected you to a coach who can help you take your skills to the next level.
            </p>
            <div>
                <button className="btn" onClick={() => window.location.href = "/login"}>
                    Login
                </button>
                <span style={{ margin: "0 10px" }}></span>
                <button className="btn" onClick={() => window.location.href = "/register"}>
                    Register
                </button>
            </div>
            <Link to="/demo-login" className="btn">Continue with Demo Account</Link>
        </div>
    );
}

export default LandingPage;