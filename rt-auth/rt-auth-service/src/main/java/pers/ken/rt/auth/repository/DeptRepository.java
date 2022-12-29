package pers.ken.rt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.ken.rt.auth.entity.Dept;

/**
 * <code> DeptRepository </code>
 * <desc> DeptRepository </desc>
 * <b>Creation Time:</b> 2022/2/25 23:51.
 *
 * @author _Ken.Hu
 */
public interface DeptRepository extends JpaRepository<Dept, Long> {

}
