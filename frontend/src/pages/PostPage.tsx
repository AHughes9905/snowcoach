import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import RepliesSection from "../components/RepliesSection";
import type { Post } from "../types/Post";


function PostPage() {
    const { id } = useParams<{ id: string }>();
    const [post, setPost] = useState<Post | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [claimMessage, setClaimMessage] = useState<string>("");

    useEffect(() => {
        const fetchPost = async () => {
            try {
                const response = await fetch(`/api/posts/${id}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    credentials: "include",
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch post details");
                }
                const data: Post = await response.json();
                setPost(data);
            } catch (error) {
                if (error instanceof Error) {
                    setError(error.message);
                } else {
                    setError("Unknown error");
                }
            } finally {
                setLoading(false);
            }
        };

        fetchPost();
    }, [id]);

    const handleClaimPost = async () => {
        try {
            const response = await fetch(`/api/posts/${id}/claim`, {
                method: "PUT",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
            });
            if (!response.ok) {
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
            setClaimMessage("Post claimed successfully!");
            console.log("Claim Result:", result);
        } catch (err) {
            if (err instanceof Error) {
                setClaimMessage(err.message);
                console.error("Error claiming post:", err);
            } else {
                setClaimMessage("Failed to claim the post. Please try again.");
            }
        }
    };

    const handleCompletePost = async () => {
        try {
            const response = await fetch(`/api/posts/${id}/complete`, {
                method: "PUT",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
            });
            if (!response.ok) {
                throw new Error("Failed to mark post as completed");
            }
            await response.json();
            alert("Post marked as completed!");
        } catch (error) {
            console.error("Error completing post:", error);
            alert("Error completing post. Please try again.");
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

    return (
        <div className="post-page">
            <h1>{post.title}</h1>
            <h2>Level: {post.level}</h2>
            <p>Topic: {post.topic}</p>
            <p>Sport: {post.sport}</p>
            {/* Display post media if present */}
            {post.mediaUrl && (
                <div className="post-media">
                    {/\.(mp4|webm|ogg)$/i.test(post.mediaUrl) ? (
                        <video controls width="480">
                            <source src={`${post.mediaUrl}`} />
                            Your browser does not support the video tag.
                        </video>
                    ) : (
                        <img
                            src={`${post.mediaUrl}`}
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
            {claimMessage && <p>{claimMessage}</p>}

            {/* Display Replies or Button if Claimer*/}
            {post.claimer && (
                <div>
                    <div className="replies-section">
                        <RepliesSection post={post} />
                    </div>
                    <div>
                        {!(post.visibility === "completed") && (
                            <button onClick={handleCompletePost} className="complete-post-btn">
                                Mark as Completed
                            </button>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
}

export default PostPage;
