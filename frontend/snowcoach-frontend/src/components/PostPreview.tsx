
function PostPreview({post}) {

    function onViewClick() {

    }

    return <div className = "post-preivew">
        <div className = "post-title">
            <h1 >{post.title}</h1>
            <button className = "view-post-btn" onClick={onViewClick}>
                <p>Details</p>
            </button>
        </div>
        <div className = "post-summary">
            <h3>{post.level}</h3>
            <p>{post.topic}</p>
        </div>
    </div>
}

export default PostPreview