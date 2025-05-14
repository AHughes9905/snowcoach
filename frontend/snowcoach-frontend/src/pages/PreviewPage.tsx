import React, { useEffect, useState } from "react";
import PostPreview from "../components/PostPreview";

function PreviewPage() {
    const [posts, setPosts] = useState([]); // State to store the list of unclaimed posts
    const [loading, setLoading] = useState(true); // State to handle loading
    const [error, setError] = useState(null); // State to handle errors

    useEffect(() => {
        const fetchUnclaimedPosts = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/posts/unclaimed", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    credentials: "include",
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch unclaimed posts");
                }

                const result = await response.json();
                setPosts(result); // Store the fetched posts in state
            } catch (error) {
                console.error("Error retrieving unclaimed posts:", error);
                setError(error.message); // Set the error message
            } finally {
                setLoading(false); // Stop loading
            }
        };

        fetchUnclaimedPosts();
    }, []); // Empty dependency array ensures this runs only once when the component mounts

    if (loading) {
        return <p>Loading posts...</p>; // Show a loading message while fetching
    }

    if (error) {
        return <p>Error: {error}</p>; // Show an error message if fetching fails
    }

    return (
        <div className="preview-page">
            <h1>Available Posts to Claim</h1>
            <div className="preview-list">
                {posts.length > 0 ? (
                    posts.map((post) => <PostPreview post={post} key={post.id} />)
                ) : (
                    <p>No unclaimed posts available.</p>
                )}
            </div>
        </div>
    );
}

export default PreviewPage;