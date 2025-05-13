import { BrowserRouter, Routes, Route } from 'react-router-dom';
import PreviewPage from './pages/PreviewPage';
import PostPage from './pages/PostPage';
import CreatePost from './pages/CreatePost';
import LoginPage from './pages/LoginPage';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<PreviewPage />} />
        <Route path="/post/:id" element={<PostPage />} />
        <Route path="/create" element={<CreatePost />} />
        <Route path="/login" element={<LoginPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
