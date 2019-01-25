package org.gk.business.data.model;

import lombok.*;
import org.gk.tools.thymeleaf.dialect.annotations.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Entry {
    @EqualsAndHashCode.Include
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Field(name = "hidden")
    private Long id;
    @NonNull
    @Field
    private String title;
    @NonNull
    private String text;
    @NonNull
    private Date time;
}
