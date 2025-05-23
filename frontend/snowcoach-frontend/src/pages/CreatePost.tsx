import React, { useState } from "react";
import { useAuth } from "../context/AuthContext";

function CreatePost() {
    const { user } = useAuth();
    const [formData, setFormData] = useState({
        title: "",
        level: "",
        topic: "",
        body: "",
        username: user?.username || "",
    });
    const [media, setMedia] = useState<File | null>(null);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files.length > 0) {
            setMedia(e.target.files[0]);
        } else {
            setMedia(null);
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            let response;
            if (media) {
                const data = new FormData();

                // Properly send post JSON with content-type
                data.append(
                "post",
                new Blob(
                    [JSON.stringify({
                    title: formData.title,
                    level: formData.level,
                    topic: formData.topic,
                    body: formData.body,
                    username: formData.username,
                    })],
                    { type: "application/json" }
                )
                );
                data.append("file", media);

                response = await fetch("http://localhost:8080/api/posts/create/media", {
                    method: "POST",
                    credentials: "include",
                    body: data,
                });
            } else {
                // Use JSON for regular post
                response = await fetch("http://localhost:8080/api/posts/create", {
                    method: "POST",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        ...formData,
                    }),
                });
            }

            if (!response.ok) {
                throw new Error("Failed to create post");
            }

            const result = await response.json();
            console.log("Post Created:", result);
            alert("Post created successfully!");

            // Reset the form
            setFormData({ title: "", level: "", topic: "", body: "", username: user?.username || "" });
            setMedia(null);
        } catch (error) {
            console.error("Error creating post:", error);
            alert("Failed to create post. Please try again.");
        }
    };

    return (
        <div className="create-post-page">
            <h1>Create a New Post</h1>
            <form onSubmit={handleSubmit} className="create-post-form">
                <div className="form-group">
                    <label htmlFor="title">Title:</label>
                    <input
                        type="text"
                        id="title"
                        name="title"
                        value={formData.title}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="level">Level:</label>
                    <select
                        id="level"
                        name="level"
                        value={formData.level}
                        onChange={handleChange}
                        required
                    >
                        <option value="" disabled>Select a level</option>
                        <option value="1">Beginner</option>
                        <option value="2">Intermediate</option>
                        <option value="3">Advanced</option>
                        <option value="4">Expert/Certification</option>
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="topic">Topic:</label>
                    <input
                        type="text"
                        id="topic"
                        name="topic"
                        value={formData.topic}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="body">Body:</label>
                    <textarea
                        id="body"
                        name="body"
                        value={formData.body}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="media">Media (photo or video):</label>
                    <input
                        type="file"
                        id="media"
                        name="media"
                        accept="image/*,video/*"
                        onChange={handleFileChange}
                    />
                </div>
                <button type="submit" className="submit-btn">Create Post</button>
            </form>
        </div>
    );
}

export default CreatePost;