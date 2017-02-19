package io.caojun221.examples.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface ItemMapper {
    @Select("SELECT * FROM item WHERE id = #{id}")
    Item selectItem(int id);

    @Insert("INSERT INTO item (name) VALUES(#{name})")
    int insertItem(Item item);
}
