import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PostPreview from "../components/PostPreview";
import type { Post } from "../types/Post";

function ClaimedPostsPage() {
    const [posts, setPosts] = useState<Post[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchClaimedPosts = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/posts/claimed", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    credentials: "include",
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch claimed posts");
                }

                const result: Post[] = await response.json();
                setPosts(result);
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

        fetchClaimedPosts();
    }, []);

    if (loading) {
        return <p>Loading posts...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    const handleReply = (postId: number): void => {
        navigate(`/post/${postId}`);
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