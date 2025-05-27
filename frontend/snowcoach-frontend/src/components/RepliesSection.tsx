import React, { useState, FormEvent, useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import Reply from "./Reply";
import { useAuth } from "../context/AuthContext";

interface ReplyProp {
    id: number;
    content: string;
    username: string;
    postId: number;
    mediaUrl?: string;
}

interface Post {
    id : number;
    title : number;
    level : number;
    topic : string;
    body : string;
    mediaUrl : string;
    username: string;
    visibility: string;
    claimerName?: string;
    timeCreated: string;
    replies: ReplyProp[];
}

interface RepliesSectionProps {
    post: Post;
}

//Make the prop the entire post object
function RepliesSection({ post }: RepliesSectionProps) {
    const { user } = useAuth();
    const [reply, setReply] = useState<{ content: string }>({ content: "" });
    const [replyMedia, setReplyMedia] = useState<File | null>(null);
    const [replies, setReplies] = useState<ReplyProp[]>(post.replies || []);
    const id = post.id;
    const fileInputRef = useRef<HTMLInputElement>(null); // Add this line


    const handleReplySubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            let response;
            let newReply;
            if (replyMedia) {
                const data = new FormData();
                data.append(
                    "reply",
                    new Blob(
                        [JSON.stringify({
                            content: reply.content,
                            postId: replies[0].postId,
                            username: user?.username || "",

                        })],
                        { type: "application/json" }
                    )
                );
                data.append("file", replyMedia);

                response = await fetch(`http://localhost:8080/api/posts/${id}/reply/media`, {
                    method: "POST",
                    credentials: "include",
                    body: data,
                });
            } else {
                response = await fetch(`http://localhost:8080/api/posts/${id}/reply`, {
                    method: "POST",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        content: reply.content,
                        postId: replies[0].postId,
                        username: user?.username || "",
                    }),
                });
            }

            if (!response.ok) {
                throw new Error("Failed to submit reply");
            }

            newReply = await response.json();
            setReplies((prev) => [...prev, newReply]);
            setReply({ content: "" });
            setReplyMedia(null);
            if (fileInputRef.current) {
                fileInputRef.current.value = ""; // Clear the file input
            }
        } catch (error) {
            alert("Failed to submit reply. Please try again.");
            console.error("Error submitting reply:", error);
        }
    };

    function handleReplyMediaChange(e: React.ChangeEvent<HTMLInputElement>) {
        if (e.target.files && e.target.files[0]) {
            setReplyMedia(e.target.files[0]);
        }
    }

    return (
        <div className="replies-section">
            <h2>Replies</h2>
            {replies.length > 0 ? (
                replies.map((reply) => (
                    <Reply key={reply.id} reply={reply} />
                ))
            ) : (
                <p>No replies yet.</p>
            )}

            {true ? (
                <form onSubmit={handleReplySubmit} className="create-post-form">
                    <div className="form-group">
                        <label htmlFor="reply-content">Reply:</label>
                        <textarea
                            id="reply-content"
                            name="content"
                            value={reply.content}
                            onChange={(e) =>
                                setReply((prevReply) => ({
                                    ...prevReply,
                                    content: e.target.value,
                                }))
                            }
                            placeholder="Write your reply here..."
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="reply-media">Media (photo or video):</label>
                        <input
                            type="file"
                            id="reply-media"
                            name="media"
                            accept="image/*,video/*"
                            onChange={handleReplyMediaChange}
                            ref={fileInputRef} // Add this line
                        />
                    </div>
                    <button type="submit">Submit Reply</button>
                </form>
            ) : postClaimed ? (
                <p>Only the claimer can reply to this post.</p>
            ) : (
                <p>This post must be claimed before you can reply.</p>
            )}
        </div>
    );
}

export default RepliesSection;
