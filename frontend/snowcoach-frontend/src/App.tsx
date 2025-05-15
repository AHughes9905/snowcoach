import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import PreviewPage from './pages/PreviewPage';
import PostPage from './pages/PostPage';
import CreatePost from "./pages/CreatePost";
import LoginPage from "./pages/LoginPage";
import ClaimedPostsPage from "./pages/ClaimedPostsPage";
import NavBar from "./components/NavBar"; // Import the NavBar component
import './App.css';

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <NavBar /> {}
                <Routes>
                    <Route path="/" element={<PreviewPage />} />
                    <Route path="/post/:id" element={<PostPage />} />
                    <Route path="/create" element={<CreatePost />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/claimed-posts" element={<ClaimedPostsPage />} />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;
