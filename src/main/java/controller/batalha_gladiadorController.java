package controller;

import model.Gladiador;
import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.batalha_gladiadorService;


@Controller
public class batalha_gladiadorController {
    private final batalha_gladiadorService service;

    public batalha_gladiadorController(batalha_gladiadorService service){
        this.service = service;
    }

    //Parte inicial dos templates.
    @GetMapping("/")
    public String home(){
        return "index";
    }

    //---Para o usuário---
    @GetMapping("/usuario/novo")
    public String formNovoUsuario(Model model){
        model.addAttribute("usuario", new Usuario());
        return "usuario/novo";
    }

    //Dados do usuário
    @GetMapping("/usuario/{id}")
    public String resumoUsuario(@PathVariable Long id, Model model){
        model.addAttribute("usuario", service.buscarUsuario(id));
        model.addAttribute("gladiadores", service.listarGladiadores(id));
        return "usuario/resumo";
    }

    //--Para o Gladiador--
    //->Monta um novo gladiador
    @GetMapping("/usuario/{usuarioId}/gladiador/novo")
    public String novoGladiador(@PathVariable Long usuarioId, Model model){
        model.addAttribute("gladiador", new Gladiador());
        model.addAttribute("usuarioId", usuarioId);
        return "gladiador/novo";
    }

    //->Salva o gladiador montado (registrando para o usuário)
    @PostMapping("/usuario/{usuarioId}/gladiador/salvar")
    public String salvarGladiador(@PathVariable Long usuarioId, @ModelAttribute Gladiador gladiador){
        Gladiador salvo = service.criarGladiador(usuarioId, gladiador);
        return "redirect:/gladiador/" + salvo.getId();
    }

    //->Verifica o Gladiador
    @GetMapping("/gladiador/{id}")
    public String detalheGladiador(@PathVariable Long id, Model model){
        model.addAttribute("gladiador", service.pesquisarGladiador(id));
        return "gladiador/detalhe";
    }

    //->Atualiza apenas a descrição do Gladiador
    @PostMapping("/gladiador/{id}/descricao")
    public String atualizarGladiador(@PathVariable Long id, @ModelAttribute Gladiador gladiador) {
        service.atualizarDescricao(id, gladiador.getDescricao());
        return "redirect:/gladiador/" + id;
    }

    //->Deleta o Gladiador
    @PostMapping("/gladiador/{id}/deletar")
    public String deletarGladiador(@PathVariable Long id, @RequestParam Long usuarioId){
        service.deletarGladiador(id);
        return "redirect:/usuario/" + usuarioId;
    }
}


//Lembrando que ainda não passei o front para cá
//As vezes as referencias não são exatamente essas que estão no código.