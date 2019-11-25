package com.pingchuan.providermysql.service;

import com.pingchuan.domain.Caller;

public interface CallerService {
    Caller findOneByUsernameAndPassword(String username, String password);
}
