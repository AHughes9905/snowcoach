import type { User } from "../types/User.ts";

interface UserPreviewProps {
    user: User;
    buttonLabel: string; // Label for the button
    buttonAction: (userId: number) => void; // Action to perform when the button is clicked
}

function UserPreview({ user, buttonLabel, buttonAction }: UserPreviewProps) {
    return (
        <div className="user-preview">
            <h2>{user.username}</h2>
            <p>Roles: {user.roleNames}</p>
            <p>ID: {user.id}</p>
            <button onClick={() => buttonAction(user.id)}>{buttonLabel}</button>
        </div>
    );
}

export default UserPreview;