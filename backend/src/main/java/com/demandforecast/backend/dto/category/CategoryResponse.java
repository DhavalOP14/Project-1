package com.demandforecast.backend.dto.category;

import java.time.LocalDateTime;

public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public CategoryResponse() {}

    public CategoryResponse(Long id, String name, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static CategoryResponseBuilder builder() { return new CategoryResponseBuilder(); }

    public static class CategoryResponseBuilder {
        private Long id;
        private String name;
        private String description;
        private LocalDateTime createdAt;

        public CategoryResponseBuilder id(Long id) { this.id = id; return this; }
        public CategoryResponseBuilder name(String name) { this.name = name; return this; }
        public CategoryResponseBuilder description(String description) { this.description = description; return this; }
        public CategoryResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public CategoryResponse build() { return new CategoryResponse(id, name, description, createdAt); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
