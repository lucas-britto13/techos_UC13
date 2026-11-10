package br.com.bugnacio.techos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// @Entity: essa classe representa uma tabela no banco de dados.
@Entity
public class Cliente {
    // @id: o campo que representa a chave primaria da tabela.
    // @GeneratedValue: o valor da chave primaria e gerado automaticamente.
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

// Cada atributo vira uma coluna na tabela. O nome da coluna e o mesmo do atributo.
// As validações verificam os dados; não acrescentamos mapeamentos ou restrições SQL.
// O limite de 255 acompanha o tamanho já existente das três colunas de texto.
@NotBlank(message = "Informe o nome.")
@Size(max = 255, message = "O nome deve ter no máximo 255 caracteres.")
private String nome;

// Telefone vazio é permitido. Quando preenchido, aceitamos números locais e
// internacionais de 7 a 15 dígitos, com pontuação usual e + apenas no início.
@Size(max = 255, message = "O telefone deve ter no máximo 255 caracteres.")
@Pattern(regexp = "^$|(?=(?:[^0-9]*[0-9]){7,15}[^0-9]*$)\\+?[0-9() .-]+$",
        message = "Informe um telefone com 7 a 15 dígitos e pontuação válida.")
private String telefone;

// @Email aceita null ou texto vazio: o e-mail continua opcional.
@Email(message = "Informe um e-mail válido.")
@Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres.")
private String email;

// Construtor padrao (sem argumentos) necessario para o JPA.
public Cliente() {

}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getNome() {
    return nome;
}

public void setNome(String nome) {
    this.nome = nome;
}

public String getTelefone() {
    return telefone;
}

public void setTelefone(String telefone) {
    this.telefone = telefone;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

}