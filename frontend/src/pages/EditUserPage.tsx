import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import type { User } from "../types/User";

interface EditUserState {
    newUsername: string;
    newRole: string;
    newPassword: string;
}

function EditUserPage() {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const [eUser, setEUser] = useState<EditUserState>({ newUsername: "", newRole: "", newPassword: "" });
    const [oUser, setOUser] = useState<User | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState<string | null>(null);
    const [showEditUsername, setShowEditUsername] = useState<boolean>(false);
    const [showEditPassword, setShowEditPassword] = useState<boolean>(false);
    const [confirmPassword, setConfirmPassword] = useState<string>("");
    const [showEditRole, setShowEditRole] = useState<boolean>(false);

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

                const data: User = await response.json();
                setOUser(data);
            } catch (error) {
                if (error instanceof Error) {
                    setError(error.message);
                } else {
                    setError("Unknown error");
                }
            } finally {
                setLoading(false);
            }
        };

        fetchUser();
    }, [id]);

    const handleUsernameChange = async () => {
        setError(null);
        try {
            const response = await fetch(`http://localhost:8080/api/user/update/username/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include",
                body: JSON.stringify({ username: eUser.newUsername }),
            });
            if (!response.ok) {
                throw new Error("Failed to update username");
            }
            setSuccess("Username updated successfully!");
        } catch (err) {
            if (err instanceof Error) setError(err.message);
            else setError("Unknown error");
        }
    };

    const handlePasswordChange = async () => {
        setError(null);
        try {
            const response = await fetch(`http://localhost:8080/api/user/update/password/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include",
                body: JSON.stringify({ password: eUser.newPassword }),
            });
            if (!response.ok) {
                throw new Error("Failed to update password");
            }
            setSuccess("Password updated successfully!");
        } catch (err) {
            if (err instanceof Error) setError(err.message);
            else setError("Unknown error");
        }
    };

    const handleRoleChange = async () => {
        setError(null);
        try {
            const response = await fetch(`http://localhost:8080/api/user/update/role/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include",
                body: JSON.stringify({ role: eUser.newRole }),
            });
            if (!response.ok) {
                throw new Error("Failed to update role");
            }
            setSuccess("Role updated successfully!");
        } catch (err) {
            if (err instanceof Error) setError(err.message);
            else setError("Unknown error");
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
            if (err instanceof Error) setError(err.message);
            else setError("Unknown error");
        }
    };

    if (loading) return <p>Loading user details...</p>;
    if (error) return <p>Error: {error}</p>;
    if (!oUser) return <p>No user data available.</p>;

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
                                    name="confirmPassword"
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
                        value={oUser.roles[0] || ""}
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