import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";



function EditUserPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const { user } = useAuth();
    const [eUser, setEUser] = useState({newUsername: "", newRole: "", newPassword: ""});
    const [oUser, setOUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const [password, setPassword] = useState("");
    const [showEditUsername, setShowEditUsername] = useState(false);
    const [showEditPassword, setShowEditPassword] = useState(false);
    const [confirmPassword, setConfirmPassword] = useState("");
    const [showEditRole, setShowEditRole] = useState(false);

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
                setOUser(data);
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchUser();
    }, [id]);

    const handleUsernameChange = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/user/update/username/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include",
                body: eUser.newUsername,
            });
            if (!response.ok) {
                throw new Error("Failed to update username");
            }
            alert("Username updated successfully!");
        } catch (err) {
            setError(err.message);
        }
    };

    const handlePasswordChange = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/user/update/password/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include",
                body: eUser.newPassword,
            });
            if (!response.ok) {
                throw new Error("Failed to update password");
            }
            alert("Password updated successfully!");
        } catch (err) {
            setError(err.message);
        }
    };

    const handleRoleChange = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/user/update/role/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include",
                body: eUser.newRole,
            });
            if (!response.ok) {
                throw new Error("Failed to update role");
            }
            alert("Role updated successfully!");
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
            <form className="edit-user-form">
                <div>
                    <label>User ID:</label>
                    <input
                        type="text"
                        name="id"
                        value={oUser.id || ""}
                        disabled
                    />
                </div>
                {/*Edit username section*/}
                <div>
                    <label>Username:</label>
                    <input
                        type="text"
                        name="username"
                        value={oUser.username || ""}
                        disabled
                    />
                    <div>
                        {showEditUsername && (
                            <div>
                                <input
                                    type="text"
                                    name="newUsername"
                                    value={eUser.newUsername || ""}
                                    onChange={e => setEUser({ ...eUser, newUsername: e.target.value })}
                                    placeholder="Enter new username"
                                />
                                <button
                                    type="button"
                                    onClick={() =>
                                        eUser.newUsername === ""
                                            ? alert("Username must not be blank")
                                            : handleUsernameChange()
                                    }
                                >
                                    Save Username
                                </button>
                            </div>
                        )}
                        <button type="button" onClick={() => setShowEditUsername(!showEditUsername)}>
                            {showEditUsername ? "Cancel" : "Edit Username"}
                        </button>
                    </div>
                </div>
                {/*Edit password section*/}
                <div>
                    <label>Password:</label>
                    <input
                        type="text"
                        name="password"
                        value={"********"}
                        disabled
                    />
                    <div>
                        {showEditPassword && (
                            <div>
                                <input
                                    type="text"
                                    name="newPassword"
                                    value={eUser.newPassword || ""}
                                    onChange={e => setEUser({ ...eUser, newPassword: e.target.value })}
                                    placeholder="Enter new Password"
                                />
                                <input
                                    type="text"
                                    name="newPassword"
                                    value={confirmPassword || ""}
                                    onChange={e => setConfirmPassword(e.target.value)}
                                    placeholder="Confirm new Password"
                                />
                                <button
                                    type="button"
                                    onClick={() =>
                                        eUser.newPassword === "" || confirmPassword !== eUser.newPassword
                                            ? alert("Password must not be blank and must match")
                                            : handlePasswordChange()
                                    }
                                >
                                    Save Password
                                </button>
                            </div>
                        )}
                        <button type="button" onClick={() => setShowEditPassword(!showEditPassword)}>
                            {showEditPassword ? "Cancel" : "Edit Password"}
                        </button>
                    </div>
                </div>
                {/*Edit role section*/}
                <div>
                    <label>Roles:</label>
                    <input
                        type="text"
                        name="role"
                        value={oUser.roleNames[0] || ""}
                        onChange={handleRoleChange}
                        disabled
                    />
                    {showEditRole && (
                            <div>
                                
                                <select id="newRole" value={eUser.newRole} onChange={e => setEUser({ ...eUser, newRole: e.target.value })}>
                                    <option value="">Select Role</option>
                                    <option value="ROLE_USER">User</option>
                                    <option value="ROLE_COACH1">Coach 1</option>
                                    <option value="ROLE_COACH2">Coach 2</option>
                                    <option value="ROLE_COACH3">Coach 3</option>
                                </select>
                                <button
                                    type="button"
                                    onClick={() =>
                                        eUser.newRole === ""
                                            ? alert("Must select a role")
                                            : handleRoleChange()
                                    }
                                >
                                    Save Role
                                </button>
                            </div>
                        )}
                    <button type="button" onClick={() => setShowEditRole(!showEditRole)}>
                            {showEditRole ? "Cancel" : "Change Role"}
                    </button>
                </div>

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