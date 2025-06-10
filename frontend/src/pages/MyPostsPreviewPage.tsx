import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PostPreview from "../components/PostPreview";
import type { Post } from "../types/Post";

function MyPostsPreviewPage() {
    const [posts, setPosts] = useState<Post[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchMyPosts = async () => {
            try {
                const response = await fetch("/api/posts/my-posts", {
                    method: "GET",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });
                if (!response.ok) {
                    throw new Error("Failed to fetch your posts");
                }
                const result: Post[] = await response.json();
                setPosts(result);
            } catch (err) {
                if (err instanceof Error) {
                    setError(err.message);
                } else {
                    setError("Unknown error");
                }
            } finally {
                setLoading(false);
            }
        };

        fetchMyPosts();
    }, []);

    const handleViewPost = (postId: number): void => {
        navigate(`/post/${postId}`);
    };

    if (loading) return <p>Loading your posts...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div className="my-posts-page">
            <h1>My Active Posts</h1>
            {posts.filter(post => post.visibility === 'visible').length > 0 ? (
                posts.filter(post => post.visibility === 'visible').map((post) => (
                    <PostPreview
                        post={post}
                        key={post.id}
                        buttonLabel="View Post"
                        buttonAction={handleViewPost}
                    />
                ))
            ) : (
                <p>No active posts found.</p>
            )}
            <div className="my-completed-posts">
                <h2>Completed Posts</h2>
                {posts.filter(post => post.visibility === 'completed').length > 0 ? (
                    posts.filter(post => post.visibility === 'completed').map((post) => (
                        <PostPreview
                            post={post}
                            key={post.id}
                            buttonLabel="View Post"
                            buttonAction={handleViewPost}
                        />
                    ))
                ) : (
                    <p>No completed posts found.</p>
                )}
            </div>
        </div>
    );
}

export default MyPostsPreviewPage;