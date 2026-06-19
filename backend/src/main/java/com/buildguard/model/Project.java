package com.buildguard.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    private String ownerName;
    private String contractorName;
    private String foremanName;

    @Column(columnDefinition = "TEXT")
    private String workerNames;

    private String status;

    private Double latitude;
    private Double longitude;

    private Integer progressPercentage = 0;

    private BigDecimal budget = BigDecimal.ZERO;

    private BigDecimal paidToDate = BigDecimal.ZERO;

    private String startDate;
    private String dueDate;

    private Integer openIssues = 0;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getForemanName() {
        return foremanName;
    }

    public void setForemanName(String foremanName) {
        this.foremanName = foremanName;
    }

    public String getWorkerNames() {
        return workerNames;
    }

    public void setWorkerNames(String workerNames) {
        this.workerNames = workerNames;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Integer progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getPaidToDate() {
        return paidToDate;
    }

    public void setPaidToDate(BigDecimal paidToDate) {
        this.paidToDate = paidToDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(Integer openIssues) {
        this.openIssues = openIssues;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}