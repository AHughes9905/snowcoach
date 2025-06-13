import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PostPreview from "../components/PostPreview";
import type { Post } from "../types/Post";


function PreviewPage() {
    const [posts, setPosts] = useState<Post[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [selectedLevels, setSelectedLevels] = useState<number[]>([]);
    const [selectedSport, setSelectedSport] = useState<string>("Any"); // <-- Add state for sport filter
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUnclaimedPosts = async () => {
            try {
                const response = await fetch("/api/posts/unclaimed", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    credentials: "include",
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch unclaimed posts");
                }

                const result: Post[] = await response.json();
                setPosts(result);
            } catch (error: any) {
                console.error("Error retrieving unclaimed posts:", error);
                setError(error.message || "Unknown error");
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

    // Filter posts by selected levels (if any selected) and sport
    const filteredPosts =
        posts.filter((post) => {
            const levelMatch =
                selectedLevels.length === 0 || selectedLevels.includes(post.level);
            const sportMatch =
                selectedSport === "Any" || post.sport === selectedSport;
            return levelMatch && sportMatch;
        });

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
                <span style={{ marginLeft: "2em" }}>Sport: </span>
                <select
                    value={selectedSport}
                    onChange={(e) => setSelectedSport(e.target.value)}
                    style={{ marginLeft: "0.5em" }}
                >
                    <option value="Any">Any</option>
                    <option value="Ski">Ski</option>
                    <option value="Snowboard">Snowboard</option>
                </select>
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