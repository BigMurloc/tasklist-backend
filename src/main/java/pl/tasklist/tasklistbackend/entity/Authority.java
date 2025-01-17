package pl.tasklist.tasklistbackend.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@Table(name = "authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "authority")
    private String authority;

    @ManyToMany(mappedBy = "authority")
    private Set<User> user;

}
