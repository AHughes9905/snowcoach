import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import PreviewPage from './pages/PreviewPage';
import PostPage from './pages/PostPage';
import CreatePost from "./pages/CreatePost";
import LoginPage from "./pages/LoginPage";
import ClaimedPostsPage from "./pages/ClaimedPreviewPage";
import NavBar from "./components/NavBar";
import HomePage from './pages/HomePage';
import MyPostsPreviewPage from "./pages/MyPostsPreviewPage";
import RegisterPage from "./pages/RegisterPage";
import AuthenticatePage from "./pages/AuthenticatePage";
import ListUsersPage from "./pages/ListUsersPage";
import AdminPage from "./pages/AdminPage";
import EditUserPage from "./pages/EditUserPage";
import LandingPage from "./pages/LandingPage";

import './App.css';
import React from "react";
import { useAuth } from "./context/AuthContext";

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <NavBar />
                <Routes>
                    <Route path="/" element={
                        <RequireAuth>
                            <HomePage />
                        </RequireAuth>
                    } />
                    <Route path="/preview" element={<PreviewPage />} />
                    <Route path="/post/:id" element={<PostPage />} />
                    <Route path="/create" element={<CreatePost />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/claimed-posts" element={<ClaimedPostsPage />} />
                    <Route path="/my-posts" element={<MyPostsPreviewPage />} />
                    <Route path="/register" element={<RegisterPage />} />
                    <Route path="/authenticate" element={<AuthenticatePage />} />
                    <Route path="*" element={<div>404 Not Found</div>} />
                    <Route path="/admin" element={<AdminPage />} />
                    <Route path="/list-users" element={<ListUsersPage />} />
                    <Route path="/edit-user/:id" element={<EditUserPage />} />
                    <Route path="/user/:id" element={<div>User Details Place Holder</div>} />
                    <Route path="/landing" element={<LandingPage />} />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;

// Component to protect routes that require authentication
function RequireAuth({ children }: { children: React.ReactNode }) {
    const { user } = useAuth();
    if (!user) {
        return <LandingPage />;
    }
    return <>{children}</>;
}
