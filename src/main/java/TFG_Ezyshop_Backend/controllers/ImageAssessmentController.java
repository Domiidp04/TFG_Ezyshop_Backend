package TFG_Ezyshop_Backend.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import TFG_Ezyshop_Backend.entities.ImageAssessment;
import TFG_Ezyshop_Backend.services.ImageAssessmentService;

@RestController
@RequestMapping("/imageAssessments")
public class ImageAssessmentController {
	
    private ImageAssessmentService imageAssessmentService;
    
    public ImageAssessmentController(ImageAssessmentService imageAssessmentService) {
    	this.imageAssessmentService = imageAssessmentService;
    }

    @PostMapping("/{assessmentId}")
    public ResponseEntity<List<ImageAssessment>> uploadImages(@PathVariable Long assessmentId, @RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        try {
            List<ImageAssessment> imageAssessments = imageAssessmentService.saveImages(imageFiles, assessmentId);
            return new ResponseEntity<>(imageAssessments, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @GetMapping("/assessment/{assessmentId}")
//    public ResponseEntity<List<ImageAssessment>> getImagesByAssessmentId(@PathVariable Long assessmentId) {
//        List<ImageAssessment> images = imageAssessmentService.getImagesByAssessmentId(assessmentId);
//        return new ResponseEntity<>(images, HttpStatus.OK);
//    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageAssessmentService.deleteImage(imageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
	
