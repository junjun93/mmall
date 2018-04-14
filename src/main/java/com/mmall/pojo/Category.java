package com.mmall.pojo;

import lombok.*;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Category {
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(parentId, category.parentId) &&
                Objects.equals(name, category.name) &&
                Objects.equals(status, category.status) &&
                Objects.equals(sortOrder, category.sortOrder) &&
                Objects.equals(createTime, category.createTime) &&
                Objects.equals(updateTime, category.updateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, parentId, name, status, sortOrder, createTime, updateTime);
    }*/


}