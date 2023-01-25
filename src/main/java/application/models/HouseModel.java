package application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "houses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private String address;

    @NotNull
    private Long owner_id;

    @Builder.Default
    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "houses_members",
            joinColumns = {
                @JoinColumn(name = "house_id", referencedColumnName = "id", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
            }
    )
    private Set<UserModel> members = new HashSet<>();

    public HouseModel addMember(UserModel userModel) {
        this.members.add(userModel);
        return this;
    }

    public HouseModel removeMember(UserModel userModel) {
        this.members.remove(userModel);
        return this;
    }
}
