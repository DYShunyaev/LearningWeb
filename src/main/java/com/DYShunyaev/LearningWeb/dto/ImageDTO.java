package com.DYShunyaev.LearningWeb.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO implements Serializable {

    private String name;

    private String originalName;

    private String contentType;

    private Long size;

    private byte[] bytes;
}
