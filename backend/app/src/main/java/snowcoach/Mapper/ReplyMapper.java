package snowcoach.Mapper;

import org.springframework.stereotype.Component;
import snowcoach.DTO.ReplyDTO;
import snowcoach.Model.Reply;
import snowcoach.Model.Post;

@Component
public class ReplyMapper {

    /**
     * Converts a Reply entity into a ReplyDTO.
     *
     * @param reply The Reply entity to be converted.
     * @return A ReplyDTO containing mapped data.
     */
    public ReplyDTO toDTO(Reply reply) {
        if (reply == null) {
            return null;
        }

        return new ReplyDTO(
                reply.getId(),
                reply.getContent(),
                reply.getUsername(),
                reply.getPost().getId(),
                reply.getMediaUrl()
        );
    }

    /**
     * Converts a ReplyDTO into a Reply entity.
     *
     * @param replyDto The ReplyDTO to be converted.
     * @param post The Post to be associated with the Reply.
     * @return A Reply entity containing mapped data.
     */
    public static Reply toEntity(ReplyDTO replyDto, Post post) {
        if (replyDto == null) {
            return null;
        }

        Reply reply = new Reply();
        reply.setId(replyDto.getId());
        reply.setContent(replyDto.getContent());
        reply.setUsername(replyDto.getUsername());
        reply.setPost(post);
        reply.setMediaUrl(replyDto.getMediaUrl());
        return reply;
    }
    
    /**
     * Extracts the title of the Post associated with the Reply (if exists).
     *
     * @param post The Post entity associated with the Reply.
     * @return The title of the Post or null if Post is null.
     */
    private static String postTitle(Post post) {
        return (post != null) ? post.getTitle() : null;
    }
}