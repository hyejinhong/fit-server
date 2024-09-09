package com.fit.invoice.domain.mail.repository;

import com.fit.invoice.domain.mail.entity.AuthenticateCode;
import org.springframework.data.repository.CrudRepository;

public interface AuthCodeRedisRepository extends CrudRepository<AuthenticateCode, String> {
}
