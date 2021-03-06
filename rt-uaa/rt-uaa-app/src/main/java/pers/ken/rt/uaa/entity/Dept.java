package pers.ken.rt.uaa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * <code> Dept </code>
 * <desc> Dept </desc>
 * <b>Creation Time:</b> 2022/2/24 22:40.
 *
 * @author _Ken.Hu
 */
@Entity
@Table(name = "uc_dept")
@Data
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
