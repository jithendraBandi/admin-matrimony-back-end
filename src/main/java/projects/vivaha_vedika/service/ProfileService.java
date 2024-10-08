package projects.vivaha_vedika.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import projects.vivaha_vedika.entity.Profile;

import java.util.List;

public interface ProfileService {
    void createProfile(Profile profile);
    List<Profile> getAllProfiles();
    ResponseEntity<Resource> getProfileImage(String fileId);
    void deleteProfile(Long id);
}
