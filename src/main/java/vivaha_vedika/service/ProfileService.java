package vivaha_vedika.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import vivaha_vedika.entity.Profile;

import java.util.List;

public interface ProfileService {
    void createProfile(Profile profile);
    List<Profile> getAllProfiles();
    ResponseEntity<Resource> getProfileImage(String fileId);
}
