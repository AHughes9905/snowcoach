import React, { createContext, useContext, useState } from "react";

interface User {
    roles: string[];
    username: string;
}

interface AuthContextType {
    user: User | null;
    login: (userData: User) => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [user, setUser] = useState<User | null>(() => {
        // Load user from localStorage when the app initializes
        const storedUser = localStorage.getItem("user");
        return storedUser ? JSON.parse(storedUser) : null;
    });

    const login = (userData: User) => {
        setUser(userData); // Store user info in state
        localStorage.setItem("user", JSON.stringify(userData)); // Persist user info in localStorage
    };

    const logout = () => {
        setUser(null); // Clear user info in state
        localStorage.removeItem("user"); // Remove user info from localStorage
    };

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
};