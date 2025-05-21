import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

function PostPage() {
    const { id } = useParams(); // Extract the post ID from the URL
    const [post, setPost] = useState(null); // State to store the post data
    const [loading, setLoading] = useState(true); // State to handle loading
    const [error, setError] = useState(null); // State to handle errors
    const [claimMessage, setClaimMessage] = useState(""); // State to handle claim success or error messages

    useEffect(() => {
        const fetchPost = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/posts/${id}`, { credentials: "include" });
                if (!response.ok) {
                    throw new Error("Failed to fetch post data");
                }
                const data = await response.json();
                console.log("Fetched Post:", data); // Debug log
                setPost(data); // Set the fetched post data, including replies
            } catch (err) {
                setError(err.message); // Set the error message
            } finally {
                setLoading(false); // Stop loading
            }
        };

        fetchPost();
    }, [id]);

    const handleClaimPost = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/posts/${id}/claim`, {
                method: "PUT",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                throw new Error("Failed to claim the post");
            }

            const result = await response.json();
            setClaimMessage("Post claimed successfully!"); // Set success message
            console.log("Claim Result:", result);
        } catch (err) {
            setClaimMessage("Failed to claim the post. Please try again."); // Set error message
            console.error("Error claiming post:", err);
        }
    };

    if (loading) {
        return <p>Loading post...</p>; // Show a loading message while fetching
    }

    if (error) {
        return <p>Error: {error}</p>; // Show an error message if fetching fails
    }

    if (!post) {
        console.log("Post is null or undefined"); // Debug log
        return <p>No post data available.</p>; // Handle the case where no post is found
    }

    console.log("Post State:", post); // Debug log

    return (
        <div className="post-page">
            <h1>{post.title}</h1>
            <h2>Level: {post.level}</h2>
            <p>Topic: {post.topic}</p>
            {/* Display media if present */}
            {post.mediaUrl && (
                <div className="post-media">
                    {/\.(mp4|webm|ogg)$/i.test(post.mediaUrl) ? (
                        <video controls width="480">
                            <source src={`http://localhost:8080${post.mediaUrl}`} />
                            Your browser does not support the video tag.
                        </video>
                    ) : (
                        <img
                            src={`http://localhost:8080${post.mediaUrl}`}
                            alt="Post media"
                            style={{ maxWidth: "480px", maxHeight: "360px" }}
                        />
                    )}
                </div>
            )}
            <p>{post.body}</p>
            <button onClick={handleClaimPost} className="claim-post-btn">
                Claim Post
            </button>
            {claimMessage && <p>{claimMessage}</p>} {/* Display claim success or error message */}

            {/* Display replies */}
            <div className="replies-section">
                <h3>Replies</h3>
                {post.replies && post.replies.length > 0 ? (
                    post.replies.map((reply) => (
                        <div key={reply.id} className="reply">
                            <p><strong>{reply.username}:</strong> {reply.content}</p>
                        </div>
                    ))
                ) : (
                    <p>No replies yet.</p>
                )}
            </div>
        </div>
    );
}

export default PostPage;