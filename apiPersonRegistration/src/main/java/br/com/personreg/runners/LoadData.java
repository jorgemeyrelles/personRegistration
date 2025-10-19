package br.com.personreg.runners;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.personreg.entities.ConsumableMovementType;
import br.com.personreg.entities.EquipmentMovementType;
import br.com.personreg.entities.EquipmentStatus;
import br.com.personreg.entities.Perfil;
import br.com.personreg.entities.ServiceOrderStatus;
import br.com.personreg.repositories.ConsumableMovementTypeRepository;
import br.com.personreg.repositories.EquipmentMovementTypeRepository;
import br.com.personreg.repositories.EquipmentStatusRepository;
import br.com.personreg.repositories.PerfilRepository;
import br.com.personreg.repositories.ServiceOrderStatusRepository;

@Component
public class LoadData implements ApplicationRunner {

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private EquipmentStatusRepository equipmentStatusRepository;

	@Autowired
	private ServiceOrderStatusRepository serviceOrderStatusRepository;

	@Autowired
	private ConsumableMovementTypeRepository consumableMovementTypeRepository;

	@Autowired
	private EquipmentMovementTypeRepository equipmentMovementTypeRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// criando o perfil 'ADMIN'
		Perfil perfil_admin = new Perfil();
		perfil_admin.setId(UUID.randomUUID());
		perfil_admin.setNome("ADMIN");

		// criando o perfil 'MANAGER'
		Perfil perfil_manager = new Perfil();
		perfil_manager.setId(UUID.randomUUID());
		perfil_manager.setNome("MANAGER");

		// criando o perfil 'COORDINATOR'
		Perfil perfil_coordinator = new Perfil();
		perfil_coordinator.setId(UUID.randomUUID());
		perfil_coordinator.setNome("COORDINATOR");

		// criando o perfil 'EMPLOYEE'
		Perfil perfil_employee = new Perfil();
		perfil_employee.setId(UUID.randomUUID());
		perfil_employee.setNome("EMPLOYEE");

		// criando o perfil 'CLIENT'
		Perfil perfil_client = new Perfil();
		perfil_client.setId(UUID.randomUUID());
		perfil_client.setNome("CLIENT");

		// verificando se os perfis não existem no banco de dados e salvando
		if (perfilRepository.findByNome("ADMIN") == null)
			perfilRepository.save(perfil_admin);

		if (perfilRepository.findByNome("MANAGER") == null)
			perfilRepository.save(perfil_manager);

		if (perfilRepository.findByNome("COORDINATOR") == null)
			perfilRepository.save(perfil_coordinator);

		if (perfilRepository.findByNome("EMPLOYEE") == null)
			perfilRepository.save(perfil_employee);

		if (perfilRepository.findByNome("CLIENT") == null)
			perfilRepository.save(perfil_client);

		// criando os status de equipamento
		// 'ACTIVE'
		EquipmentStatus status_active = new EquipmentStatus();
		status_active.setId(UUID.randomUUID());
		status_active.setName("ACTIVE");

		// 'INACTIVE'
		EquipmentStatus status_inactive = new EquipmentStatus();
		status_inactive.setId(UUID.randomUUID());
		status_inactive.setName("INACTIVE");

		// 'MAINTENANCE'
		EquipmentStatus status_maintenance = new EquipmentStatus();
		status_maintenance.setId(UUID.randomUUID());
		status_maintenance.setName("MAINTENANCE");

		// verificando se os status não existem no banco de dados e salvando
		if (equipmentStatusRepository.findByName("ACTIVE") == null)
			equipmentStatusRepository.save(status_active);

		if (equipmentStatusRepository.findByName("INACTIVE") == null)
			equipmentStatusRepository.save(status_inactive);

		if (equipmentStatusRepository.findByName("MAINTENANCE") == null)
			equipmentStatusRepository.save(status_maintenance);

		// criando os status de ordem de serviço
		// 'PENDING'
		ServiceOrderStatus order_pending = new ServiceOrderStatus();
		order_pending.setId(UUID.randomUUID());
		order_pending.setName("PENDING");

		// 'ASSIGNED'
		ServiceOrderStatus order_assigned = new ServiceOrderStatus();
		order_assigned.setId(UUID.randomUUID());
		order_assigned.setName("ASSIGNED");

		// 'IN_PROGRESS'
		ServiceOrderStatus order_in_progress = new ServiceOrderStatus();
		order_in_progress.setId(UUID.randomUUID());
		order_in_progress.setName("IN_PROGRESS");

		// 'AWAITING_SIGNATURES'
		ServiceOrderStatus order_awaiting_signatures = new ServiceOrderStatus();
		order_awaiting_signatures.setId(UUID.randomUUID());
		order_awaiting_signatures.setName("AWAITING_SIGNATURES");

		// 'COMPLETED'
		ServiceOrderStatus order_completed = new ServiceOrderStatus();
		order_completed.setId(UUID.randomUUID());
		order_completed.setName("COMPLETED");

		// 'CANCELLED'
		ServiceOrderStatus order_cancelled = new ServiceOrderStatus();
		order_cancelled.setId(UUID.randomUUID());
		order_cancelled.setName("CANCELLED");

		// verificando se os status de ordem de serviço não existem no banco de dados e
		// salvando
		if (serviceOrderStatusRepository.findByName("PENDING") == null)
			serviceOrderStatusRepository.save(order_pending);

		if (serviceOrderStatusRepository.findByName("ASSIGNED") == null)
			serviceOrderStatusRepository.save(order_assigned);

		if (serviceOrderStatusRepository.findByName("IN_PROGRESS") == null)
			serviceOrderStatusRepository.save(order_in_progress);

		if (serviceOrderStatusRepository.findByName("AWAITING_SIGNATURES") == null)
			serviceOrderStatusRepository.save(order_awaiting_signatures);

		if (serviceOrderStatusRepository.findByName("COMPLETED") == null)
			serviceOrderStatusRepository.save(order_completed);

		if (serviceOrderStatusRepository.findByName("CANCELLED") == null)
			serviceOrderStatusRepository.save(order_cancelled);

		// criando os tipos de movimentação de consumíveis
		// 'ADD'
		ConsumableMovementType movement_add = new ConsumableMovementType();
		movement_add.setId(UUID.randomUUID());
		movement_add.setName("ADD");

		// 'REMOVE'
		ConsumableMovementType movement_remove = new ConsumableMovementType();
		movement_remove.setId(UUID.randomUUID());
		movement_remove.setName("REMOVE");

		// 'TRANSFER'
		ConsumableMovementType movement_transfer = new ConsumableMovementType();
		movement_transfer.setId(UUID.randomUUID());
		movement_transfer.setName("TRANSFER");

		// 'ADJUSTMENT'
		ConsumableMovementType movement_adjustment = new ConsumableMovementType();
		movement_adjustment.setId(UUID.randomUUID());
		movement_adjustment.setName("ADJUSTMENT");

		// 'USED_IN_SERVICE'
		ConsumableMovementType movement_used_in_service = new ConsumableMovementType();
		movement_used_in_service.setId(UUID.randomUUID());
		movement_used_in_service.setName("USED_IN_SERVICE");

		// verificando se os tipos de movimentação não existem no banco de dados e
		// salvando
		if (consumableMovementTypeRepository.findByName("ADD") == null)
			consumableMovementTypeRepository.save(movement_add);

		if (consumableMovementTypeRepository.findByName("REMOVE") == null)
			consumableMovementTypeRepository.save(movement_remove);

		if (consumableMovementTypeRepository.findByName("TRANSFER") == null)
			consumableMovementTypeRepository.save(movement_transfer);

		if (consumableMovementTypeRepository.findByName("ADJUSTMENT") == null)
			consumableMovementTypeRepository.save(movement_adjustment);

		if (consumableMovementTypeRepository.findByName("USED_IN_SERVICE") == null)
			consumableMovementTypeRepository.save(movement_used_in_service);

		// criando os tipos de movimentação de equipamentos
		// 'ADD'
		EquipmentMovementType equipment_add = new EquipmentMovementType();
		equipment_add.setId(UUID.randomUUID());
		equipment_add.setName("ADD");

		// 'REMOVE'
		EquipmentMovementType equipment_remove = new EquipmentMovementType();
		equipment_remove.setId(UUID.randomUUID());
		equipment_remove.setName("REMOVE");

		// 'TRANSFER'
		EquipmentMovementType equipment_transfer = new EquipmentMovementType();
		equipment_transfer.setId(UUID.randomUUID());
		equipment_transfer.setName("TRANSFER");

		// 'ASSIGNED_TO_SERVICE'
		EquipmentMovementType equipment_assigned_to_service = new EquipmentMovementType();
		equipment_assigned_to_service.setId(UUID.randomUUID());
		equipment_assigned_to_service.setName("ASSIGNED_TO_SERVICE");

		// 'RETURNED_FROM_SERVICE'
		EquipmentMovementType equipment_returned_from_service = new EquipmentMovementType();
		equipment_returned_from_service.setId(UUID.randomUUID());
		equipment_returned_from_service.setName("RETURNED_FROM_SERVICE");

		// 'IN_MAINTENANCE'
		EquipmentMovementType equipment_in_maintenance = new EquipmentMovementType();
		equipment_in_maintenance.setId(UUID.randomUUID());
		equipment_in_maintenance.setName("IN_MAINTENANCE");

		// 'OUT_OF_MAINTENANCE'
		EquipmentMovementType equipment_out_of_maintenance = new EquipmentMovementType();
		equipment_out_of_maintenance.setId(UUID.randomUUID());
		equipment_out_of_maintenance.setName("OUT_OF_MAINTENANCE");

		// verificando se os tipos de movimentação de equipamentos não existem no banco
		// de dados e salvando
		if (equipmentMovementTypeRepository.findByName("ADD") == null)
			equipmentMovementTypeRepository.save(equipment_add);

		if (equipmentMovementTypeRepository.findByName("REMOVE") == null)
			equipmentMovementTypeRepository.save(equipment_remove);

		if (equipmentMovementTypeRepository.findByName("TRANSFER") == null)
			equipmentMovementTypeRepository.save(equipment_transfer);

		if (equipmentMovementTypeRepository.findByName("ASSIGNED_TO_SERVICE") == null)
			equipmentMovementTypeRepository.save(equipment_assigned_to_service);

		if (equipmentMovementTypeRepository.findByName("RETURNED_FROM_SERVICE") == null)
			equipmentMovementTypeRepository.save(equipment_returned_from_service);

		if (equipmentMovementTypeRepository.findByName("IN_MAINTENANCE") == null)
			equipmentMovementTypeRepository.save(equipment_in_maintenance);

		if (equipmentMovementTypeRepository.findByName("OUT_OF_MAINTENANCE") == null)
			equipmentMovementTypeRepository.save(equipment_out_of_maintenance);
	}
}
