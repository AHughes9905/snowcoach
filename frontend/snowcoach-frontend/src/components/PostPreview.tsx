import { useNavigate } from 'react-router-dom';

function PostPreview({ post }) {
    const navigate = useNavigate();

    const handleClick = () => {
        navigate(`/post/${post.id}`, { state: { post } }); // Pass the post object in state
    };

    return (
        <div className="post-preview">
            <div className="post-title">
                <h2>{post.title}</h2>
                <button className="view-post-btn" onClick={handleClick}>
                    <p>Details</p>
                </button>
            </div>
            <div className="post-summary">
                <h3>{post.level}</h3>
                <p>{post.topic}</p>
            </div>
        </div>
    );
}

export default PostPreview;