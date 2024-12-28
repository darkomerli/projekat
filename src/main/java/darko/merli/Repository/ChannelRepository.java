package darko.merli.Repository;

import darko.merli.Model.ChannelDTOS.Channel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    @Query(value = "SELECT * FROM channel where channel_name = ?1", nativeQuery=true)
    public Channel findByName(String channel_name);

    @Query(value = "DELETE FROM video where channel_id = ?1", nativeQuery=true)
    @Modifying
    @Transactional
    public void deleteFromVideos(long channel_id);
}
