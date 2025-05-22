import React, { useState } from "react";
import { useAuth } from "../context/AuthContext";

function RegisterPage() {
    const { login, user } = useAuth();
    const [formData, setFormData] = useState({ username: "", password: "", password2: "" });
    const [regData, setRegData] = useState({ username: "", password: ""});

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        if (formData.password !== formData.password2) {
            alert("Passwords do not match");
            return;
        }
        regData.username = formData.username;
        regData.password = formData.password;
        e.preventDefault();

        try {
            const response = await fetch("http://localhost:8080/api/auth/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(regData),
                credentials: "include",
            });

            if (!response.ok) {
                throw new Error("Registration failed.");
            }

            const result = await response;
            alert("Registration successful!");
            window.location.href = "/"; // Redirect to home page after successful registration
        } catch (error) {
            console.error("Error during registration:", error);
            alert("Failed to register. Please try again.");
        }
    };

    return (
        <div className="register-page">
            <h1>Register</h1>
            <form onSubmit={handleSubmit}>
                <div>
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
                <div>
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
                <div>
                    <label htmlFor="password">Confirm Password:</label>
                    <input
                        type="password"
                        id="password2"
                        name="password2"
                        value={formData.password2}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Register</button>
            </form>
        </div>
    );
}

export default RegisterPage;