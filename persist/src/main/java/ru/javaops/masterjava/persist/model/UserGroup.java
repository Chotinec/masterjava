package ru.javaops.masterjava.persist.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.*;

@Data
@RequiredArgsConstructor
//@AllArgsConstructor
@NoArgsConstructor
public class UserGroup{

    @Column("user_id")
    private @NonNull int userId;
    @Column("group_id")
    private @NonNull int groupId;
}
