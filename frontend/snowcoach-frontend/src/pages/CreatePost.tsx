import React, { useState } from "react";
import { useAuth } from "../context/AuthContext";

function CreatePost() {
    const { user } = useAuth(); // Access user info from context
    const [formData, setFormData] = useState({
        title: "",
        level: "",
        topic: "",
        body: "",
        username: "admin", // HARDCODED FOR NOW
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            console.log("userName:", formData.username);
            const response = await fetch("http://localhost:8080/api/posts/create", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    ...formData,
                }),
            });
            console.log("FormData:", formData);

            if (!response.ok) {
                throw new Error("Failed to create post");
            }

            const result = await response.json();
            console.log("Post Created:", result);
            alert("Post created successfully!");

            // Reset the form
            setFormData({ title: "", level: "", topic: "", body: "", username: "" });
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
                        <option value="0">Beginner</option>
                        <option value="1">Intermediate</option>
                        <option value="2">Advanced</option>
                        <option value="3">Expert/Certification</option>
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
                <button type="submit" className="submit-btn">Create Post</button>
            </form>
        </div>
    );
}

export default CreatePost;