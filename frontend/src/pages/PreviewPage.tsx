import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PostPreview from "../components/PostPreview";

function PreviewPage() {
    const [posts, setPosts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [selectedLevels, setSelectedLevels] = useState<number[]>([]); // Track selected levels
    const navigate = useNavigate();

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
                setPosts(result);
            } catch (error) {
                console.error("Error retrieving unclaimed posts:", error);
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchUnclaimedPosts();
    }, []);

    if (loading) {
        return <p>Loading posts...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    const handleViewDetails = (postId: number) => {
        navigate(`/post/${postId}`);
    };

    // Get all unique levels from posts for checkbox options
    const allLevels = Array.from(new Set(posts.map((post) => post.level))).sort((a, b) => a - b);

    // Handle checkbox change
    const handleLevelChange = (level: number) => {
        setSelectedLevels((prev) =>
            prev.includes(level)
                ? prev.filter((l) => l !== level)
                : [...prev, level]
        );
    };

    // Filter posts by selected levels (if any selected)
    const filteredPosts =
        selectedLevels.length === 0
            ? posts
            : posts.filter((post) => selectedLevels.includes(post.level));

    return (
        <div className="preview-page">
            <h1>Available Posts to Claim</h1>
            <div style={{ marginBottom: "1rem" }}>
                <span>Filter by Level: </span>
                {allLevels.map((level) => (
                    <label key={level} style={{ marginRight: "1em" }}>
                        <input
                            type="checkbox"
                            checked={selectedLevels.includes(level)}
                            onChange={() => handleLevelChange(level)}
                        />
                        Level {level}
                    </label>
                ))}
            </div>
            <div className="preview-list">
                {filteredPosts.length > 0 ? (
                    filteredPosts.map((post) => (
                        <PostPreview
                            post={post}
                            key={post.id}
                            buttonLabel="Details"
                            buttonAction={handleViewDetails}
                        />
                    ))
                ) : (
                    <p>No unclaimed posts available.</p>
                )}
            </div>
        </div>
    );
}

export default PreviewPage;