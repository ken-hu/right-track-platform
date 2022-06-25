package pers.ken.rt.uaa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * <code> UserGroup </code>
 * <desc> UserGroup </desc>
 * <b>Creation Time:</b> 2022/2/24 22:41.
 *
 * @author _Ken.Hu
 */
@Entity
@Table(name = "uc_user_group")
@Data
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
