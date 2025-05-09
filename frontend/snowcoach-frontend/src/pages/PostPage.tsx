
function PostPage() {
   <div className = "post-page">
       <h1>{post.title}</h1> 
       <h2>{post.poster}</h2>
       <img src = {post.image-url} />
        <p> {post.text}</p>
   </div>  
}