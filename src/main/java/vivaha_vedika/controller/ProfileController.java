package vivaha_vedika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vivaha_vedika.dto.ApiResponse;
import vivaha_vedika.entity.Profile;
import vivaha_vedika.service.ProfileService;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProfile(@RequestBody Profile profile) {
        profileService.createProfile(profile);
        return new ResponseEntity<>(new ApiResponse("Profile Updated Successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        return new ResponseEntity<>(new ApiResponse(profiles), HttpStatus.OK);
    }
    @GetMapping("/get-image/{fileId}")
    public ResponseEntity<Resource> getImageFile(@PathVariable("fileId") String fileId) {
        return profileService.getProfileImage(fileId);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteProfile(@PathVariable("id") Long id) {
        profileService.deleteProfile(id);
    }

}
