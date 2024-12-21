package darko.merli.Repository;

import darko.merli.Model.Channel.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    @Query(value = "SELECT * FROM channel where channel_name = ?1", nativeQuery=true)
    public Channel findByName(String channel_name);
}
