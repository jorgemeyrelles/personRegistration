package br.com.personreg.runners;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.personreg.entities.Perfil;
import br.com.personreg.repositories.PerfilRepository;

@Component
public class LoadData implements ApplicationRunner {

	@Autowired
	private PerfilRepository perfilRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// criando o perfil 'DEFAULT'
		Perfil perfil_default = new Perfil();
		perfil_default.setId(UUID.randomUUID());
		perfil_default.setNome("DEFAULT");

		// criando o perfil 'ADMINISTRADOR'
		Perfil perfil_admin = new Perfil();
		perfil_admin.setId(UUID.randomUUID());
		perfil_admin.setNome("ADMIN");

		// verificando se o perfil 'DEFAULT' não existe no banco de dados
		if (perfilRepository.findByNome("DEFAULT") == null)
			perfilRepository.save(perfil_default);

		// verificando se o perfil 'ADMINISTRADOR' não existe no banco de dados
		if (perfilRepository.findByNome("ADMIN") == null)
			perfilRepository.save(perfil_admin);
	}
}
