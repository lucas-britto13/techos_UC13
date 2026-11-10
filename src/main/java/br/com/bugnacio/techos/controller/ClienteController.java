package br.com.bugnacio.techos.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bugnacio.techos.model.Cliente;
import br.com.bugnacio.techos.repository.ClienteRepository;
import jakarta.validation.Valid;

// @Controller: bean do Spring, a recepcao dos pedidos de clientes.
@Controller
// @RequestMapping na classe: o comeco do endereco para TODOS os metodos daqui.
@RequestMapping("/clientes")
public class ClienteController {

    // O controller precisa do respository para buscar os clientes no banco de dados.
    private final ClienteRepository repo;

    // Injeção de dependencia: o Spring vai criar o repository e passar para o controller.
    public ClienteController(ClienteRepository repo) {
        this.repo = repo;
    }

    @InitBinder("cliente")
    public void configurarCadastro(WebDataBinder binder) {
        // O cadastro recebe apenas estes campos. Até um id adulterado é ignorado,
        // mantendo o novo Cliente sem identidade e impedindo a atualização de outro registro.
        binder.setAllowedFields("nome", "telefone", "email");
    }

    // @GetMapping vazio = o proprio "/clientes".
    // Endereco completo: http://localhost:8080/clientes
    @GetMapping
    public String listar(Model model) {

        // A tela clientes/lista recebe os clientes consultados no banco de dados.
        model.addAttribute("clientes", repo.findAll());

        // SEM @ResponseBody: o texto devolvido e o nome da tela.
        // Abre: src/main/resources/templates/clientes/lista.html
        return "clientes/lista";
    }

    // GET /clientes/novo: mostra o formulário para criar um novo cliente.
    @GetMapping("/novo")
    public String novo(Model model) {
        // O objeto vazio fornece as propriedades usadas pelos th:field do formulário.
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    // POST /clientes/salvar: recebe o formulário e cadastra exclusivamente um novo cliente.
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult resultado, Model model, RedirectAttributes redirectAttributes) {
        // O Spring preenche Cliente com os campos permitidos e @Valid verifica suas regras.
        // BindingResult deve vir logo após o objeto validado; assim, os erros e os valores
        // digitados voltam ao Thymeleaf, em vez de interromper a requisição.
        if (resultado.hasErrors()) {
            return "clientes/form";
        }

        try {
            // Sem id recebido do navegador, o JPA persiste um novo registro.
            // A transação do repository termina antes do retorno desta chamada.
            repo.save(cliente);
        } catch (DataAccessException | TransactionException ex) {
            // Uma falha pode ocorrer depois de o JPA atribuir um id. Limpamos essa
            // identidade para continuar exibindo o formulário como um novo cadastro.
            cliente.setId(null);
            // A página recebe uma mensagem segura, sem SQL, credenciais ou detalhes internos.
            model.addAttribute("erro", "Não foi possível salvar o cliente. Tente novamente.");
            return "clientes/form";
        }

        // O flash dura até a próxima requisição e usa o aviso já existente na listagem.
        redirectAttributes.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso.");
        // Post/Redirect/Get evita repetir este POST ao atualizar a página de destino.
        return "redirect:/clientes";
    }
}