package Repository;

import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByVisibility(String visibility);

    List<Post> findByUserId(Long userId);

}
