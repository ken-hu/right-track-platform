package pers.ken.rt.uc.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * <name> User </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 16:30.
 *
 * @author _Ken.Hu
 */
@Entity
@Table(name = "uc_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

}
