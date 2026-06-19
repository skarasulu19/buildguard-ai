package com.buildguard.controller;

import com.buildguard.model.Project;
import com.buildguard.repository.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project savedProject = projectRepository.save(
                Objects.requireNonNull(project, "Project cannot be null")
        );

        return ResponseEntity.ok(savedProject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        if (!projectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        projectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
public ResponseEntity<Project> updateProject(
        @PathVariable Long id,
        @RequestBody Project updatedProject
) {
    return projectRepository.findById(id)
            .map(existing -> {
                existing.setName(updatedProject.getName());
                existing.setAddress(updatedProject.getAddress());
                existing.setOwnerName(updatedProject.getOwnerName());
                existing.setContractorName(updatedProject.getContractorName());
                existing.setForemanName(updatedProject.getForemanName());
                existing.setWorkerNames(updatedProject.getWorkerNames());
                existing.setStatus(updatedProject.getStatus());
                existing.setProgressPercentage(updatedProject.getProgressPercentage());
                existing.setBudget(updatedProject.getBudget());
                existing.setPaidToDate(updatedProject.getPaidToDate());
                existing.setStartDate(updatedProject.getStartDate());
                existing.setDueDate(updatedProject.getDueDate());
                existing.setOpenIssues(updatedProject.getOpenIssues());
                existing.setDescription(updatedProject.getDescription());

                Project saved = projectRepository.save(existing);
                return ResponseEntity.ok(saved);
            })
            .orElse(ResponseEntity.notFound().build());
}
}