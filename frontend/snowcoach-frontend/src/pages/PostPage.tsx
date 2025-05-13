import { useLocation } from 'react-router-dom';

function PostPage() {
    const location = useLocation();
    const { post } = location.state || {}; // Access the post object from state

    if (!post) {
        return <p>No post data available.</p>; // Handle the case where no post is passed
    }

    return (
        <div className="post-page">
            <h1>{post.title}</h1>
            <h2>Level: {post.level}</h2>
            <p>Topic: {post.topic}</p>
        </div>
    );
}

export default PostPage;