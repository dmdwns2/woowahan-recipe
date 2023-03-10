package com.woowahan.recipe.repository;

import com.woowahan.recipe.domain.entity.AlarmEntity;
import com.woowahan.recipe.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {
    Page<AlarmEntity> findByTargetUser(UserEntity user, Pageable pageable);
}
