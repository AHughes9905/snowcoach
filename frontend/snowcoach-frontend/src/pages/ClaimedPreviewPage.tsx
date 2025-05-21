import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PostPreview from "../components/PostPreview";

function ClaimedPostsPage() {
    const [posts, setPosts] = useState([]); // State to store the list of claimed posts
    const [loading, setLoading] = useState(true); // State to handle loading
    const [error, setError] = useState(null); // State to handle errors
    const navigate = useNavigate(); // Hook to navigate between pages

    useEffect(() => {
        const fetchClaimedPosts = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/posts/claimed", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    credentials: "include", // Include the JWT cookie
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch claimed posts");
                }

                const result = await response.json();
                setPosts(result); // Store the fetched posts in state
            } catch (error) {
                console.error("Error retrieving claimed posts:", error);
                setError(error.message); // Set the error message
            } finally {
                setLoading(false); // Stop loading
            }
        };

        fetchClaimedPosts();
    }, []); // Empty dependency array ensures this runs only once when the component mounts

    if (loading) {
        return <p>Loading posts...</p>; // Show a loading message while fetching
    }

    if (error) {
        return <p>Error: {error}</p>; // Show an error message if fetching fails
    }

    const handleReply = (postId: number) => {
        navigate(`/post/${postId}`); // Navigate to the post page instead of the reply page
    };

    return (
        <div className="claimed-posts-page">
            <h1>Your Claimed Posts</h1>
            <div className="claimed-posts-list">
                {posts.length > 0 ? (
                    posts.map((post) => (
                        <PostPreview
                            post={post}
                            key={post.id}
                            buttonLabel="View Post"
                            buttonAction={handleReply}
                        />
                    ))
                ) : (
                    <p>You have not claimed any posts yet.</p>
                )}
            </div>
        </div>
    );
}

export default ClaimedPostsPage;