package darko.merli.Repository;

import darko.merli.Model.VideoDTOS.Video;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query(value = "DELETE FROM liked_videos WHERE video_id = ?1", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteFromDatabase(long video_id);

    @Query(value = "DELETE FROM comment WHERE video_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteFromComments(long video_id);
}
