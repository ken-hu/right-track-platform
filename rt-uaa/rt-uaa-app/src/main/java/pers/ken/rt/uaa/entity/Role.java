package pers.ken.rt.uaa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * <name> Account </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 16:46.
 *
 * @author _Ken.Hu
 */
@Entity
@Table(name = "uc_role")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
