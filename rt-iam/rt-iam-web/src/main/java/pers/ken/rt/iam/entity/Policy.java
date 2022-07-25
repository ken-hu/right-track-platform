package pers.ken.rt.iam.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * <code> Policy </code>
 * <desc> Policy </desc>
 * <b>Creation Time:</b> 2022/7/19 17:30.
 *
 * @author Ken.Hu
 */
@Entity
@Table(name = "iam_policy")
@Data
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}