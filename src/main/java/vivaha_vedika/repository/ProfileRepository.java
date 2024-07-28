package vivaha_vedika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vivaha_vedika.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
