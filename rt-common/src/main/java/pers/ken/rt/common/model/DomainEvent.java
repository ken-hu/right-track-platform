package pers.ken.rt.common.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ken
 * @className: DomainEvent
 * @createdTime: 2023/3/7 0:59
 * @desc:
 */
@Data
public abstract class DomainEvent<T> {
    private String id;
    private LocalDateTime occurredOn;
    private T data;

    public DomainEvent(T data) {
        this.data = data;
    }

    public DomainEvent(String id, T data) {
        this.id = id;
        this.data = data;
    }
}
