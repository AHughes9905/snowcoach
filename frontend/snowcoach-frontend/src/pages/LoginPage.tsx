import { useState } from "react";

function LoginPage() {
    const [formData, setFormData] = useState({
        username: "",
        password: "",
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            console.log("Form Data being sent:", JSON.stringify(formData)); // Log the form data

            const response = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formData), // Send username and password
                credentials: "include", // Include cookies in the request
            });

            if (!response.ok) {
                throw new Error("Login failed. Please check your credentials.");
            }

            const result = await response.json();
            console.log("Login successful:", result);
            alert("Login successful!");

            // The JWT should now be stored in a cookie by the backend
        } catch (error) {
            console.error("Error during login:", error);
            alert("Failed to log in. Please try again.");
        }
    };

    return (
        <div className="login-page">
            <h1>Login</h1>
            <form onSubmit={handleSubmit} className="login-form">
                <div className="form-group">
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit" className="submit-btn">Login</button>
            </form>
        </div>
    );
}

export default LoginPage;