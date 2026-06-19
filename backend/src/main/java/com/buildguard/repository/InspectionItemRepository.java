package com.buildguard.repository;

import com.buildguard.model.InspectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectionItemRepository extends JpaRepository<InspectionItem, Long> {

    List<InspectionItem> findByProjectIdOrderByCreatedAtDesc(Long projectId);
}