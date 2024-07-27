package com.dasun.library_manager_core.user;

import com.dasun.library_manager_core.api.entity.PersistedObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role extends PersistedObject {

    @Column(nullable = false, unique = true)
    private String name;
}
