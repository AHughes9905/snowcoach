import PostPreview from "../components/PostPreview"


function PreviewPage() {

    //add getUnclaimedPost function to fill list
    const posts = [
        {id : 1, title:"turn help", level:"beginner", topic: "turns"},
        {id : 2, title:"ollies", level:"intermediate", topic: "freestyle"},
        {id : 3, title:"switch", level:"intermediate", topic: "carve"}
    ]

    return (
        <div className="preivew-page">
            <h1>Available Posts to Claim</h1>
            <div className="preview-list">
                {posts.map(post => <PostPreview post={post} key={post.id} />)}
            </div>
        </div>
    )
}

export default PreviewPage