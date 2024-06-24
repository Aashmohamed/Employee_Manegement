package com.xrontech.web.domain.department;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DepartmentDTO {
    @NotBlank
    private String name;
}
