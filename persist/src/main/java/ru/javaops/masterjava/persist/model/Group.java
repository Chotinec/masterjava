package ru.javaops.masterjava.persist.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Group extends BaseEntity {
    private @NonNull String name;
    private @NonNull GroupType type;
    @Column("project_id")
    private @NonNull int projectId;

    public Group(int id, String name, GroupType type, int projectId) {
        this(name, type, projectId);
        this.id = id;
    }
}