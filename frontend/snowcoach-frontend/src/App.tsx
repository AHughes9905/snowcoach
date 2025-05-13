import { BrowserRouter, Routes, Route } from 'react-router-dom';
import PreviewPage from './pages/PreviewPage';
import PostPage from './pages/PostPage';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<PreviewPage />} />
        <Route path="/post/:id" element={<PostPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
