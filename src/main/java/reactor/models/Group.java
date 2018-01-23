package reactor.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Account owner;
    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Account> accountList;
    @ManyToOne(cascade = {CascadeType.ALL})
    private List<Face> faceList;


}
