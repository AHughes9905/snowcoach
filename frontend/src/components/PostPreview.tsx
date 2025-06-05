import type { Post } from "../types/Post";


interface PostPreviewProps {
    post: Post;
    buttonLabel: string; // Label for the button
    buttonAction: (postId: number) => void; // Action to perform when the button is clicked
}

function PostPreview({ post, buttonLabel, buttonAction }: PostPreviewProps) {
    return (
        <div className="post-preview">
            <h2>{post.title}</h2>
            <p>Level: {post.level}</p>
            <p>Topic: {post.topic}</p>
            <button onClick={() => buttonAction(post.id)}>{buttonLabel}</button>
        </div>
    );
}

export default PostPreview;