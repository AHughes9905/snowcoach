import { useState } from "react";

function CreatePost() {
    const [formData, setFormData] = useState({
        title: "",
        level: "",
        topic: "",
        body: "",
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
            const response = await fetch("https://localhost:8080/api/posts/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formData), // Convert formData to JSON
            });

            if (!response.ok) {
                throw new Error("Failed to create post");
            }

            const result = await response.json();
            console.log("Post Created:", result);
            alert("Post created successfully!");

            // Reset the form
            setFormData({ title: "", level: "", topic: "", body: "" });
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
                        <option value="Beginner">Beginner</option>
                        <option value="Intermediate">Intermediate</option>
                        <option value="Advanced">Advanced</option>
                        <option value="Expert/Certification">Expert/Certification</option>
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