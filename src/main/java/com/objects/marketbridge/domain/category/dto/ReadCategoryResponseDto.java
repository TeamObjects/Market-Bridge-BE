package com.objects.marketbridge.domain.category.dto;

import com.objects.marketbridge.domain.model.Category;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReadCategoryResponseDto {

    private Long id;
    private Long parentId;
    private Long level; //대분류 0L, 중분류 1L, 소분류 2L.
    private String name;
    private List<?> childCategories;

    @Builder
    public ReadCategoryResponseDto(Long id, Long parentId, Long level, String name, List<Category> childCategories) {
        this.id = id;
        this.parentId = parentId;
        this.level = level;
        this.name = name;
        this.childCategories = childCategories;
    }
}


