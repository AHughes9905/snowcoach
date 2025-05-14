import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import PreviewPage from './pages/PreviewPage';
import PostPage from './pages/PostPage';
import CreatePost from "./pages/CreatePost";
import LoginPage from "./pages/LoginPage";
import './App.css';

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<PreviewPage />} />
                    <Route path="/post/:id" element={<PostPage />} />
                    <Route path="/create" element={<CreatePost />} />
                    <Route path="/login" element={<LoginPage />} />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;
