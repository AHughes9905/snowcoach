
interface ReplyProp {
    id: number;
    content: string;
    username: string;
    postId: number;
    mediaUrl?: string;
}

function Reply( { reply }: { reply: ReplyProp }) {
    //Take indivdual reply as input and list them similarly to post preview


    return (
        <div className="post-preview">
        
            {reply.mediaUrl && (
                <div className="reply-media">
                    {/\.(mp4|webm|ogg)$/i.test(reply.mediaUrl) ? (
                        <video controls width="320">
                            <source src={`http://localhost:8080${reply.mediaUrl}`} />
                            Your browser does not support the video tag.
                        </video>
                    ) : (
                        <img
                            src={`http://localhost:8080${reply.mediaUrl}`}
                            alt="Reply media"
                            style={{ maxWidth: "320px", maxHeight: "240px" }}
                        />
                    )}
                </div>
            )}
            <p>
                <strong>{reply.username}:</strong> {reply.content}
            </p>
        </div>
    );
}


export default Reply;