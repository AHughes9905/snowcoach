import PostPreview from "../components/PostPreview"


function PreviewPage() {

    //add getUnclaimedPost function to fill list
    const posts = [
        {id : 1, title:"turn help", level:"beginner", topic: "turns"}
    ]

    return (
        <div className="preivew-page">

            <div className="preview-list">
                {posts.map(post => <PostPreview post={post} key={post.id} />)}
            </div>
        </div>
    )
}

export default PreviewPage