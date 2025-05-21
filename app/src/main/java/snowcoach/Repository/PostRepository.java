package snowcoach.Repository;

import snowcoach.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByVisibility(String visibility);
    List<Post> findByUserId(Long userId);


    
    List<Post> findByTitle(String title);
    List<Post> findByTopic(String topic);
    List<Post> findByLevel(int level);
    List<Post> findByClaimerId(Long claimerId);

    List<Post> findByTimeCreatedBefore(LocalDateTime timeCreatedBefore);
    List<Post> findByTimeCreatedAfter(LocalDateTime timeCreatedAfter);
}