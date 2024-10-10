package projects.vivaha_vedika.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projects.vivaha_vedika.constants.Constants;
import projects.vivaha_vedika.entity.Profile;
import projects.vivaha_vedika.repository.ProfileRepository;
import projects.vivaha_vedika.service.ProfileService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        if (optionalProfile.isEmpty()) {
            throw new RuntimeException("Profile Not Found");
        }
        profileRepository.deleteById(id);
        List<String> imageExtensionsList = Constants.IMAEGE_EXTENSIONS_LIST;
        Path imageDirectory = Paths.get(Constants.PROFILE_IMAGES_DIRECTORY);

        for (String extension : imageExtensionsList) {
            Path filePath = imageDirectory.resolve(optionalProfile.get().getCodeNo() + "." + extension);
            try {
//                Resource resource = new UrlResource(filePath.toUri());
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
