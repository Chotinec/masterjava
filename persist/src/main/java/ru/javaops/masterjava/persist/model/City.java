package ru.javaops.masterjava.persist.model;

import lombok.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class City extends BaseEntity {
    private @NonNull String ref;
    private @NonNull String value;

    public City(int id, String ref, String value) {
        this(ref, value);
        this.id = id;
    }
}
