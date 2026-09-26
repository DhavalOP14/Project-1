package com.demandforecast.backend.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    private String description;

    public CategoryRequest() {}

    public CategoryRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static CategoryRequestBuilder builder() { return new CategoryRequestBuilder(); }

    public static class CategoryRequestBuilder {
        private String name;
        private String description;
        public CategoryRequestBuilder name(String name) { this.name = name; return this; }
        public CategoryRequestBuilder description(String description) { this.description = description; return this; }
        public CategoryRequest build() { return new CategoryRequest(name, description); }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
