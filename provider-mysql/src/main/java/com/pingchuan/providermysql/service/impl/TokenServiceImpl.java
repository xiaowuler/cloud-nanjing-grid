package com.pingchuan.providermysql.service.impl;

import com.pingchuan.domain.Token;
import com.pingchuan.providermysql.mapper.TokenMapper;
import com.pingchuan.providermysql.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenMapper tokenMapper;

    @Override
    public void insertOne(Token token) {
        tokenMapper.insertOne(token);
    }

    @Override
    public Token findOneByCallerCode(String code) {
        return tokenMapper.findOneByCallerCode(code);
    }

    @Override
    public void deleteOneByCallerCode(String code) {
        tokenMapper.deleteOneByCallerCode(code);
    }
}
