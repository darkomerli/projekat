package darko.merli.Repository;

import darko.merli.Model.CommentDTOS.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
