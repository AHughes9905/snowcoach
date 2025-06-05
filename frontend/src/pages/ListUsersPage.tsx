import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import UserPreview from "../components/UserPreview";
import type { User } from "../types/User";


function ListUsersPage() {
    const [users, setUsers] = useState<User[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();
    const [searchQuery, setSearchQuery] = useState<string>("");

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/user/all", {
                    method: "GET",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });
                if (!response.ok) {
                    throw new Error("Failed to fetch users");
                }
                const result: User[] = await response.json();
                setUsers(result);
            } catch (err) {
                if (err instanceof Error) {
                    setError(err.message);
                } else {
                    setError("Unknown error");
                }
            } finally {
                setLoading(false);
            }
        };

        fetchUsers();
    }, []);

    const handleViewUser = (userId: number): void => {
        navigate(`/edit-user/${userId}`); //need to implement view user page
    };

    if (loading) return <p>Loading users...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div className="list-users-page">
            <h1>List of Users</h1>
            <input
                type="text"
                placeholder="Search users..."
                className="search-input"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
            />
            <div className="list-users">
                {users.length > 0 ? (
                    users
                        .filter((user) =>
                            user.username.toLowerCase().startsWith(searchQuery.toLowerCase())
                        )
                        .map((user) => (
                            <UserPreview
                                user={user}
                                key={user.id}
                                buttonLabel="Edit User"
                                buttonAction={handleViewUser}
                            />
                        ))
                ) : (
                    <p>No users found.</p>
                )}
            </div>
        </div>
    );
}

export default ListUsersPage;