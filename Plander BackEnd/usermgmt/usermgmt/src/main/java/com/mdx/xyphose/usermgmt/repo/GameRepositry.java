package com.mdx.xyphose.usermgmt.repo;

import com.mdx.xyphose.usermgmt.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepositry extends  JpaRepository<Game,Long> {

}
