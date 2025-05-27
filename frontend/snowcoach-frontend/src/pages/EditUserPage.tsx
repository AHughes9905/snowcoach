import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";



function EditUserPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const { user } = useAuth();
    const [eUser, setEUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const [password, setPassword] = useState("");

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/user/${id}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    credentials: "include",
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch user details");
                }

                const data = await response.json();
                setEUser(data);
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchUser();
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEUser((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleRoleChange = (e) => {
        setEUser((prev) => ({
            ...prev,
            roles: [e.target.value],
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(null);

        if (!window.confirm("Are you sure you want to save these changes?")) {
            return;
        }

        // Prepare payload
        const payload = { ...eUser };
        if (password) {
            payload.password = password;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/user/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include",
                body: JSON.stringify(payload),
            });
            if (!response.ok) {
                throw new Error("Failed to update user");
            }
            setSuccess("User updated successfully!");
            // Optionally, navigate back or refresh
            // navigate("/list-users");
        } catch (err) {
            setError(err.message);
        }
    };

    const handleDelete = async () => {
        if (!window.confirm("Are you sure you want to delete this user? This action cannot be undone.")) {
            return;
        }
        setError(null);
        setSuccess(null);
        try {
            const response = await fetch(`http://localhost:8080/api/user/${id}`, {
                method: "DELETE",
                credentials: "include",
            });
            if (!response.ok) {
                throw new Error("Failed to delete user");
            }
            setSuccess("User deleted successfully!");
            setTimeout(() => {
                navigate("/list-users");
            }, 1000);
        } catch (err) {
            setError(err.message);
        }
    };

    if (loading) return <p>Loading user details...</p>;
    if (error) return <p>Error: {error}</p>;
    if (!eUser) return <p>No user data available.</p>;

    return (
        <div className="edit-user-page">
            <h1>Edit User</h1>
            <form onSubmit={handleSubmit} className="edit-user-form">
                <div>
                    <label>User ID:</label>
                    <input
                        type="text"
                        name="id"
                        value={eUser.id || ""}
                        disabled
                    />
                </div>
                <div>
                    <label>Username:</label>
                    <input
                        type="text"
                        name="username"
                        value={eUser.username || ""}
                        onChange={handleChange}
                        disabled
                    />
                </div>
                <div>
                    <label>Update Password:</label>
                    <input
                        type="password"
                        name="password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        placeholder="Enter new password"
                    />
                </div>
                <div>
                    <label>Roles:</label>
                    <input
                        type="text"
                        name="role"
                        value={eUser.roleNames[0] || ""}
                        onChange={handleChange}
                        disabled
                    />
                </div>
                <button type="submit">Save Changes</button>
                <button type="button" style={{ marginLeft: "1em", color: "red" }} onClick={handleDelete}>
                    Delete User
                </button>
                {success && <p style={{ color: "green" }}>{success}</p>}
                {error && <p style={{ color: "red" }}>{error}</p>}
            </form>
        </div>
    );
}

export default EditUserPage;