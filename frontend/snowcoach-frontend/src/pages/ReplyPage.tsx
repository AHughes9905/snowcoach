import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function ReplyPage() {
    const { id } = useParams(); // Extract the post ID from the URL
    const [post, setPost] = useState(null); // State to store the post details
    const [reply, setReply] = useState({
        content: "",
        postId: id,
        username: "",
        id: null,
    }); // State to store the reply
    const [loading, setLoading] = useState(true); // State to handle loading
    const [error, setError] = useState(null); // State to handle errors

    useEffect(() => {
        const fetchPost = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/posts/${id}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    credentials: "include",
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch post details");
                }

                const data = await response.json();
                setPost(data); // Store the fetched post details
            } catch (error) {
                console.error("Error fetching post details:", error);
                setError(error.message); // Set the error message
            } finally {
                setLoading(false); // Stop loading
            }
        };

        fetchPost();
    }, [id]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await fetch(`http://localhost:8080/api/posts/${id}/reply`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include",
                body: JSON.stringify(reply), // Send the `reply` object as is
            });

            if (!response.ok) {
                throw new Error("Failed to submit reply");
            }

            const newReply = await response.json();
            setPost((prevPost) => ({
                ...prevPost,
                replies: [...prevPost.replies, newReply], // Add the new reply to the replies array
            }));
            setReply((prevReply) => ({
                ...prevReply,
                content: "", // Clear only the `content` field after submission
            }));
        } catch (error) {
            console.error("Error submitting reply:", error);
            alert("Failed to submit reply. Please try again.");
        }
    };

    if (loading) {
        return <p>Loading post details...</p>; // Show a loading message while fetching
    }

    if (error) {
        return <p>Error: {error}</p>; // Show an error message if fetching fails
    }

    if (!post) {
        return <p>No post data available.</p>; // Handle the case where no post is found
    }

    const { replies } = post; // Destructure replies from post

    return (
        <div className="reply-page">
            <h1>Reply to Post</h1>
            {/* Display post details */}
            <div className="post-details">
                <h2>{post.title}</h2>
                <p>Level: {post.level}</p>
                <p>Topic: {post.topic}</p>
                {/* Display post media if present */}
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
            </div>

            {/* Display replies */}
            <div className="replies-section">
                <h3>Replies</h3>
                {replies && replies.length > 0 ? (
                    replies.map((reply) => (
                        <div key={reply.id} className="reply">
                            <p><strong>{reply.username}:</strong> {reply.content}</p>
                            {/* Removed media display for replies */}
                        </div>
                    ))
                ) : (
                    <p>No replies yet.</p>
                )}
            </div>

            {/* Reply form */}
            <form onSubmit={handleSubmit}>
                <textarea
                    value={reply.content} // Bind to the `content` property
                    onChange={(e) =>
                        setReply((prevReply) => ({
                            ...prevReply,
                            content: e.target.value, // Update only the `content` property
                        }))
                    }
                    placeholder="Write your reply here..."
                    required
                />
                <button type="submit">Submit Reply</button>
            </form>
        </div>
    );
}

export default ReplyPage;