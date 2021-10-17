package pers.ken.rt.uc.usercenter.entity;

import javax.persistence.*;

/**
 * <name> Resource </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 23:02.
 *
 * @author _Ken.Hu
 */
@Entity
@Table(name = "uc_resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
