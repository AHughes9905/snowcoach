import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import PreviewPage from './pages/PreviewPage';
import PostPage from './pages/PostPage';
import CreatePost from "./pages/CreatePost";
import LoginPage from "./pages/LoginPage";
import ClaimedPostsPage from "./pages/ClaimedPreviewPage";
import ReplyPage from "./pages/ReplyPage";
import NavBar from "./components/NavBar"; // Import the NavBar component
import HomePage from './pages/HomePage';
import './App.css';

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <NavBar />
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/preview" element={<PreviewPage />} />
                    <Route path="/post/:id" element={<PostPage />} />
                    <Route path="/create" element={<CreatePost />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/claimed-posts" element={<ClaimedPostsPage />} />
                    <Route path="/post/:id/reply" element={<ReplyPage />} />
                    {/* Placeholder for My Posts */}
                    <Route path="/my-posts" element={<div>My Posts Page (to implement)</div>} />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;
