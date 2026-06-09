package com.qtanime.animebackend.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;

    private String name;

    private String slug;

    private String description;

    private Double price;

    private Integer quantity;

    private String thumbnail;

    private String categoryName;

    private String brandName;

    private String status;
}