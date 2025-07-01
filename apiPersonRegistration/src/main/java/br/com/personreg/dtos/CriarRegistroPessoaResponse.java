package br.com.personreg.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class CriarRegistroPessoaResponse {

    private UUID id;
    private String nome;
    private String telefone;
    private String cpf;
    private String numero;
    private String complemento;
    private String cep;
    private String bairro;
    private String nomeMunicipio;
    private String nomeEstado;
    private UUID usuarioId;
    private String latitude;
    private String longitude;
    private ObterDadosUsuarioResponse usuario;
}
