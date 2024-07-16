package com.StajProject.Company.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;

import java.util.List;

public interface BaseMapper<S, T> { //Nesne dönüşümlerini gerçekleştirmek için kullanıldı.

    T toDto(S s);

    @InheritInverseConfiguration
    S toEntity(T t);

    @InheritConfiguration//miras
    List<T> toDtoList(List<S> sourceList);

    @InheritConfiguration
    List<S> toEntityList(List<T> targetList);

}