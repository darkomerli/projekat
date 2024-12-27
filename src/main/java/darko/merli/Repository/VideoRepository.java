package darko.merli.Repository;

import darko.merli.Model.VideoDTOS.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
