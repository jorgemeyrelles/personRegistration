package br.com.personreg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.personreg.dtos.ObterDadosServiceOrderStatusResponse;
import br.com.personreg.entities.ServiceOrderStatus;
import br.com.personreg.repositories.ServiceOrderStatusRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceOrderStatusService {

	private final ServiceOrderStatusRepository serviceOrderStatusRepository;

	public ObterDadosServiceOrderStatusResponse obterDados(UUID id) {
		ServiceOrderStatus serviceOrderStatus = serviceOrderStatusRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Service Order Status não encontrado"));

		return new ObterDadosServiceOrderStatusResponse(serviceOrderStatus.getId(), serviceOrderStatus.getName());
	}

	public List<ObterDadosServiceOrderStatusResponse> buscarTodos() {
		List<ObterDadosServiceOrderStatusResponse> resposta = new ArrayList<>();
		List<ServiceOrderStatus> serviceOrderStatuses = serviceOrderStatusRepository.findAll();

		for (ServiceOrderStatus serviceOrderStatus : serviceOrderStatuses) {
			resposta.add(new ObterDadosServiceOrderStatusResponse(serviceOrderStatus.getId(), serviceOrderStatus.getName()));
		}
		return resposta;
	}

	public ObterDadosServiceOrderStatusResponse buscarPorNome(String name) {
		ServiceOrderStatus serviceOrderStatus = serviceOrderStatusRepository.findByName(name);
		if (serviceOrderStatus == null) {
			throw new NoSuchElementException("Service Order Status não encontrado");
		}
		return new ObterDadosServiceOrderStatusResponse(serviceOrderStatus.getId(), serviceOrderStatus.getName());
	}
}
