package com.pingchuan.providermysql.service;

import com.pingchuan.domain.Token;

public interface TokenService {

    void insertOne(Token token);

    Token findOneByCallerCode(String code);

    void deleteOneByCallerCode(String code);
}
