package pers.ken.rt.uaa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * <code> Enterprise </code>
 * <desc> Enterprise </desc>
 * <b>Creation Time:</b> 2022/3/31 21:27.
 *
 * @author _Ken.Hu
 */
@Entity
@Table(name = "uc_enterprise")
@Data
public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
