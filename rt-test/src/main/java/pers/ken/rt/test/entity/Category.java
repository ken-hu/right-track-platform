package pers.ken.rt.test.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Creation Time: 2022/11/15 15:19.
 *
 * @author Ken
 */
@Entity
@Table(name = "dict_category")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long icCode;

    private String name;
}
