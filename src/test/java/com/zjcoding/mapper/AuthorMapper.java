package com.zjcoding.mapper;

import com.zjcoding.entity.AuthorEntity;
import org.apache.ibatis.annotations.CacheNamespace;

import java.util.List;

/**
 * @author ZhangJun
 * @date 2023/1/4 09:37
 */
@CacheNamespace(readWrite = false)
public interface AuthorMapper {

    AuthorEntity findById(Long id);

    List<AuthorEntity> listAll();

}
