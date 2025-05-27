import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import RepliesSection from "../components/RepliesSection";

function PostPage() {
    const { id } = useParams();
    const { user } = useAuth();
    const [post, setPost] = useState(null);
    const [reply, setReply] = useState({
        content: "",
        postId: id,
        username: user?.username || "",
        id: null,
        mediaUrl: "",
    });
    const [replyMedia, setReplyMedia] = useState<File | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [claimMessage, setClaimMessage] = useState(""); // State to handle claim success or error messages

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
                setPost(data);
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
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
                // Try to read error message from backend
                let errorMsg = "Failed to claim the post";
                try {
                    const errorData = await response.json();
                    if (errorData && errorData.message) {
                        errorMsg = errorData.message;
                    }
                } catch {
                    // Ignore JSON parse errors, use default message
                }
                throw new Error(errorMsg);
            }

            const result = await response.json();
            setClaimMessage("Post claimed successfully!"); // Set success message
            console.log("Claim Result:", result);
        } catch (err) {
            setClaimMessage(err.message || "Failed to claim the post. Please try again."); // Set error message
            console.error("Error claiming post:", err);
        }
    };

    const handleReplyMediaChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files.length > 0) {
            setReplyMedia(e.target.files[0]);
        } else {
            setReplyMedia(null);
        }
    };

    const handleReplySubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            let response;
            if (replyMedia) {
                const data = new FormData();
                data.append(
                    "reply",
                    new Blob(
                        [JSON.stringify({
                            content: reply.content,
                            postId: id,
                            username: user?.username || "",
                        })],
                        { type: "application/json" }
                    )
                );
                data.append("file", replyMedia);

                response = await fetch(`http://localhost:8080/api/posts/${id}/reply/media`, {
                    method: "POST",
                    credentials: "include",
                    body: data,
                });
            } else {
                response = await fetch(`http://localhost:8080/api/posts/${id}/reply`, {
                    method: "POST",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        content: reply.content,
                        postId: id,
                        username: user?.username || "",
                    }),
                });
            }

            if (!response.ok) {
                throw new Error("Failed to submit reply");
            }

            const newReply = await response.json();
            setPost((prevPost) => ({
                ...prevPost,
                replies: [...prevPost.replies, newReply],
            }));
            setReply((prevReply) => ({
                ...prevReply,
                content: "",
                mediaUrl: "",
            }));
            setReplyMedia(null);
        } catch (error) {
            alert("Failed to submit reply. Please try again.");
        }
    };

    if (loading) {
        return <p>Loading post details...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    if (!post) {
        return <p>No post data available.</p>;
    }

    const { replies } = post;

    return (
        <div className="post-page">
            <h1>{post.title}</h1>
            <h2>Level: {post.level}</h2>
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
            {/* Only show the claim button if there is no claimer */}
            {!post.claimer && (
                <button onClick={handleClaimPost} className="claim-post-btn">
                    Claim Post
                </button>
            )}
            {claimMessage && <p>{claimMessage}</p>} {/* Display claim success or error message */}

            {/* Display Rpelies or Claim Button*/}
            {post.claimer && (
                <div className="replies-section">
                <RepliesSection post={post} /> 
            </div>
            ) }
            



            {/* Reply form only if post is claimed */}
            {post.claimer === user?.username ? (
                <form onSubmit={handleReplySubmit} className="create-post-form">
                    <div className="form-group">
                        <label htmlFor="reply-content">Reply:</label>
                        <textarea
                            id="reply-content"
                            name="content"
                            value={reply.content}
                            onChange={(e) =>
                                setReply((prevReply) => ({
                                    ...prevReply,
                                    content: e.target.value,
                                }))
                            }
                            placeholder="Write your reply here..."
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="reply-media">Media (photo or video):</label>
                        <input
                            type="file"
                            id="reply-media"
                            name="media"
                            accept="image/*,video/*"
                            onChange={handleReplyMediaChange}
                        />
                    </div>
                    <button type="submit">Submit Reply</button>
                </form>
            ) : post.claimed ? (
                <p>Only the claimer can reply to this post.</p>
            ) : (
                <p>This post must be claimed before you can reply.</p>
            )}
        </div>
    );
}

export default PostPage;