import registroPessoaService from "../registroPessoas";
import type {
  RegistroPessoaRequest,
  RegistroPessoaUpdateRequest,
} from "../../types/registroPessoasTypes";

// Exemplos de uso dos serviços de registro de pessoas

export const exemplosCRUDRegistroPessoa = {
  // 1. Criar um novo registro de pessoa
  async criarNovoRegistroPessoa(token?: string) {
    const dadosPessoa: RegistroPessoaRequest = {
      nome: "Maria Silva Santos",
      cpf: "123.456.789-00",
      telefone: "(11) 98765-4321",
      numero: "456",
      complemento: "Apartamento 12B",
      cep: "01310-100",
      bairro: "Bela Vista",
      nomeMunicipio: "São Paulo",
      nomeEstado: "São Paulo",
      latitude: "-23.5505",
      longitude: "-46.6333",
      usuarioId: "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    };

    try {
      const pessoaCriada = await registroPessoaService.criarRegistroPessoa(
        dadosPessoa,
        token
      );
      console.log("Registro de pessoa criado:", pessoaCriada);
      return pessoaCriada;
    } catch (error) {
      console.error("Erro ao criar registro de pessoa:", error);
      throw error;
    }
  },

  // 2. Obter registro de pessoa por ID
  async obterRegistroPessoa(id: string, token?: string) {
    try {
      const pessoa = await registroPessoaService.obterRegistroPessoa(id, token);
      console.log("Dados da pessoa:", pessoa);
      return pessoa;
    } catch (error) {
      console.error("Erro ao obter registro de pessoa:", error);
      throw error;
    }
  },

  // 3. Atualizar registro de pessoa
  async atualizarRegistroPessoa(id: string, token?: string) {
    const dadosAtualizacao: RegistroPessoaUpdateRequest = {
      telefone: "(11) 99999-8888",
      numero: "1000",
      complemento: "Cobertura",
      cep: "01310-200",
      bairro: "Jardins",
      nomeMunicipio: "São Paulo",
      nomeEstado: "São Paulo",
      latitude: "-23.5506",
      longitude: "-46.6334",
    };

    try {
      const pessoaAtualizada =
        await registroPessoaService.atualizarRegistroPessoa(
          id,
          dadosAtualizacao,
          token
        );
      console.log("Registro de pessoa atualizado:", pessoaAtualizada);
      return pessoaAtualizada;
    } catch (error) {
      console.error("Erro ao atualizar registro de pessoa:", error);
      throw error;
    }
  },

  // 4. Buscar todos os registros de pessoas
  async buscarTodosRegistrosPessoas(token?: string) {
    try {
      const pessoas = await registroPessoaService.buscarTodosRegistrosPessoas(
        token
      );
      console.log("Todos os registros de pessoas:", pessoas);
      return pessoas;
    } catch (error) {
      console.error("Erro ao buscar registros de pessoas:", error);
      throw error;
    }
  },

  // 5. Buscar pessoa por telefone
  async buscarPessoaPorTelefone(telefone: string, token?: string) {
    try {
      const pessoas = await registroPessoaService.buscarPessoaPorTelefone(
        telefone,
        token
      );
      console.log("Pessoas encontradas por telefone:", pessoas);
      return pessoas;
    } catch (error) {
      console.error("Erro ao buscar pessoa por telefone:", error);
      throw error;
    }
  },

  // 6. Buscar pessoa por nome
  async buscarPessoaPorNome(nome: string, token?: string) {
    try {
      const pessoas = await registroPessoaService.buscarPessoaPorNome(
        nome,
        token
      );
      console.log("Pessoas encontradas por nome:", pessoas);
      return pessoas;
    } catch (error) {
      console.error("Erro ao buscar pessoa por nome:", error);
      throw error;
    }
  },

  // 7. Verificar se CPF existe
  async verificarCPFExiste(cpf: string, token?: string) {
    try {
      const resultado = await registroPessoaService.verificarCPFExiste(
        cpf,
        token
      );
      console.log("Verificação de CPF:", resultado);
      if (resultado.existe) {
        console.log("CPF já está cadastrado no sistema");
      } else {
        console.log("CPF não encontrado no sistema");
      }
      return resultado;
    } catch (error) {
      console.error("Erro ao verificar CPF:", error);
      throw error;
    }
  },

  // 8. Buscar pessoa por CPF
  async buscarPessoaPorCPF(cpf: string, token?: string) {
    try {
      const pessoa = await registroPessoaService.buscarPessoaPorCPF(cpf, token);
      console.log("Pessoa encontrada por CPF:", pessoa);
      return pessoa;
    } catch (error) {
      console.error("Erro ao buscar pessoa por CPF:", error);
      throw error;
    }
  },

  // 9. Deletar registro de pessoa
  async deletarRegistroPessoa(id: string, token?: string) {
    try {
      const sucesso = await registroPessoaService.deletarRegistroPessoa(
        id,
        token
      );
      console.log("Registro de pessoa deletado:", sucesso);
      return sucesso;
    } catch (error) {
      console.error("Erro ao deletar registro de pessoa:", error);
      throw error;
    }
  },

  // Exemplo de fluxo completo de gerenciamento de registro de pessoas
  async exemploFluxoCompletoRegistroPessoa(token?: string) {
    try {
      // 1. Verificar se CPF já existe antes de criar
      console.log("1. Verificando se CPF já existe...");
      const cpfExiste = await this.verificarCPFExiste("123.456.789-00", token);

      if (!cpfExiste.existe) {
        // 2. Criar pessoa se CPF não existir
        console.log("2. Criando novo registro de pessoa...");
        const pessoaCriada = await this.criarNovoRegistroPessoa(token);
        const pessoaId = pessoaCriada.id; // Agora é string (UUID)

        // 3. Buscar todos os registros
        console.log("3. Buscando todos os registros...");
        await this.buscarTodosRegistrosPessoas(token);

        // 4. Obter dados da pessoa criada
        console.log("4. Obtendo dados da pessoa específica...");
        await this.obterRegistroPessoa(pessoaId, token);

        // 5. Buscar por nome
        console.log("5. Buscando por nome...");
        await this.buscarPessoaPorNome("Maria", token);

        // 6. Buscar por telefone
        console.log("6. Buscando por telefone...");
        await this.buscarPessoaPorTelefone("98765-4321", token);

        // 7. Buscar por CPF
        console.log("7. Buscando por CPF...");
        await this.buscarPessoaPorCPF("123.456.789-00", token);

        // 8. Atualizar o registro
        console.log("8. Atualizando registro...");
        await this.atualizarRegistroPessoa(pessoaId, token);

        console.log(
          "Fluxo completo de registro de pessoa executado com sucesso!"
        );
      } else {
        console.log("CPF já existe no sistema");
      }
    } catch (error) {
      console.error("Erro no fluxo completo de registro de pessoa:", error);
    }
  },
};

export default exemplosCRUDRegistroPessoa;
