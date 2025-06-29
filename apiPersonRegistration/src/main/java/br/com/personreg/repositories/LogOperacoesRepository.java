package br.com.personreg.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.personreg.collections.LogOperações;

public interface LogOperacoesRepository
		extends MongoRepository<LogOperações, UUID> {

}
