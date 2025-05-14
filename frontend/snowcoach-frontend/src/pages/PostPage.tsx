import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

function PostPage() {
    const { id } = useParams(); // Extract the post ID from the URL
    const [post, setPost] = useState(null); // State to store the post data
    const [loading, setLoading] = useState(true); // State to handle loading
    const [error, setError] = useState(null); // State to handle errors

    useEffect(() => {
        const fetchPost = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/posts/${id}`, {credentials: "include"});
                if (!response.ok) {
                    throw new Error("Failed to fetch post data");
                }
                const data = await response.json();
                setPost(data); // Set the fetched post data
            } catch (err) {
                setError(err.message); // Set the error message
            } finally {
                setLoading(false); // Stop loading
            }
        };

        fetchPost();
    }, [id]);

    if (loading) {
        return <p>Loading post...</p>; // Show a loading message while fetching
    }

    if (error) {
        return <p>Error: {error}</p>; // Show an error message if fetching fails
    }

    if (!post) {
        return <p>No post data available.</p>; // Handle the case where no post is found
    }

    return (
        <div className="post-page">
            <h1>{post.title}</h1>
            <h2>Level: {post.level}</h2>
            <p>Topic: {post.topic}</p>
            <p>{post.body}</p>
        </div>
    );
}

export default PostPage;