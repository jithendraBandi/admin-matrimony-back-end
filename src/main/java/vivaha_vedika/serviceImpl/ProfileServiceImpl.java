package vivaha_vedika.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vivaha_vedika.constants.Constants;
import vivaha_vedika.entity.Profile;
import vivaha_vedika.repository.ProfileRepository;
import vivaha_vedika.service.ProfileService;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;


    @Override
    public void createProfile(Profile profile) {
        profileRepository.save(profile);
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public ResponseEntity<Resource> getProfileImage(String fileId) {
        List<String> imageExtensionsList = Constants.IMAEGE_EXTENSIONS_LIST;
        Path imageDirectory = Paths.get(Constants.PROFILE_IMAGES_DIRECTORY);

        for (String extension : imageExtensionsList) {
            Path filePath = imageDirectory.resolve(fileId + "." + extension);
            try {
                Resource resource = new UrlResource(filePath.toUri());
                if (resource.exists() && resource.isReadable()) {
                    return ResponseEntity.ok().body(resource);
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
