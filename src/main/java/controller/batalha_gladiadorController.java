package controller;

import org.springframework.stereotype.Controller;
import model.Gladiador;
import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.batalha_gladiadorService;


@Controller
public class batalha_gladiadorController {
    private final batalha_gladiadorController service;

    public batalha_gladiadorController(batalha_gladiadorController service){
        this.service = service;
    }

    //Parte inicial dos templates.
    public String home(){
        return "index";
    }

    //---Para o usuário---
    @GetMapping("/usuario/novo")
    public String formNovoUsuario(Model model){
        model.addAtribute("usuario", new Usuario());
        return "usuario/novo";
    }

    //Dados do usuário
    @GetMapping("/usuario/{id}")
    public String resumoUsuario(@PathVariable Long id, Model model){
        model.addAtribute("usuario", service.buscarUsuario(id));
        model.addAtribute("gladiadores", service.listarGladiadores(id));
        return "usuario/resumo";
    }

    //--Para o Gladiador--
    //->Monta um novo gladiador
    @GetMapping("/usuario/{usuarioId}/gladiador/novo")
    public String NovoGladiador(@PathVariable Long usuarioId, Model model){
        model.addAttribute("gladiador", new Gladiador());
        model.addAttribute("usuarioId", usuarioId);
        return "gladiador/novo";
    }
    //->Salva o gladiador montado (registrando para o usuário)
    @GetMapping("/usuario/{usuarioId}/gladiador/salvar")
    public String salvar Gladiador(@PathVariable Log usuarioId, gladiador){
        Gladiador salvo = service.criarGladiador(usuarioId, gladiador);
        return "usuario/salvar" + salvo.getId();
    }
    //->Verifica o Gladiador
    @GetMapping("/gladiador/{id}"){
        public String detalhaGladiador(@PathVariable Long id, Model model){
            model.addAtribute("gladiador", service.pesquisarGladiador(id));
            return "gladiador/detalhe";
        }
    }
    //->Atualiza apenas a descriçao do Gladiador
    @PostMapping("/gladiador/{id}/descricao")
    public String atualizaGladiador(@PathVariable Long id,  @ModelAtrribute Gladiador gladiador) {
        service.atualizaGladiador(id, gladiador.getDescricao());
        return "redirect:/gladiador/" + id;
    }
    //->Deleta o Gladiador
    @PostMapping("/gladiador/{id}/deletar")
    public String deletarGladiador(@PathVariable Long id, @ModelAttribute){
        service.deletarGladiador(id);
        return "redirect:/usuario/" + id;
    }
}


//Lembrando que ainda não passei o front para cá
//As vezes as referencias não são exatamente essas que estão no código.