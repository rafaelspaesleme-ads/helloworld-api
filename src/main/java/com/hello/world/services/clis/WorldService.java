package com.hello.world.services.clis;

import java.util.Optional;

public interface WorldService {
    Optional<?> findOrSave(String planet);
}
