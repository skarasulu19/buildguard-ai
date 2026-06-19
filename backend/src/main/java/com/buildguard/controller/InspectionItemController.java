package com.buildguard.controller;

import com.buildguard.model.InspectionItem;
import com.buildguard.model.Project;
import com.buildguard.repository.InspectionItemRepository;
import com.buildguard.repository.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class InspectionItemController {

    private final InspectionItemRepository inspectionItemRepository;
    private final ProjectRepository projectRepository;

    public InspectionItemController(
            InspectionItemRepository inspectionItemRepository,
            ProjectRepository projectRepository
    ) {
        this.inspectionItemRepository = inspectionItemRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/projects/{projectId}/inspection-items")
    public ResponseEntity<List<InspectionItem>> getInspectionItemsByProject(
            @PathVariable Long projectId
    ) {
        if (!projectRepository.existsById(projectId)) {
            return ResponseEntity.notFound().build();
        }

        List<InspectionItem> items =
                inspectionItemRepository.findByProjectIdOrderByCreatedAtDesc(projectId);

        return ResponseEntity.ok(items);
    }

    @PostMapping("/projects/{projectId}/inspection-items")
    public ResponseEntity<InspectionItem> createInspectionItem(
            @PathVariable Long projectId,
            @RequestBody InspectionItem inspectionItem
    ) {
        Project project = projectRepository.findById(projectId)
                .orElse(null);

        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        inspectionItem.setProject(project);

        InspectionItem saved = inspectionItemRepository.save(inspectionItem);

        return ResponseEntity.ok(saved);
    }

    @PutMapping("/inspection-items/{itemId}")
    public ResponseEntity<InspectionItem> updateInspectionItem(
            @PathVariable Long itemId,
            @RequestBody InspectionItem updatedItem
    ) {
        return inspectionItemRepository.findById(itemId)
                .map(existing -> {
                    existing.setArea(updatedItem.getArea());
                    existing.setPriority(updatedItem.getPriority());
                    existing.setTitle(updatedItem.getTitle());
                    existing.setDescription(updatedItem.getDescription());
                    existing.setAssignedTo(updatedItem.getAssignedTo());
                    existing.setStatus(updatedItem.getStatus());
                    InspectionItem saved = inspectionItemRepository.save(existing);

                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/inspection-items/{itemId}")
    public ResponseEntity<Void> deleteInspectionItem(
            @PathVariable Long itemId
    ) {
        if (!inspectionItemRepository.existsById(itemId)) {
            return ResponseEntity.notFound().build();
        }

        inspectionItemRepository.deleteById(itemId);

        return ResponseEntity.noContent().build();
    }
    
}