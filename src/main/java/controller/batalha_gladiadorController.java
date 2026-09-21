package controller;

import model.Gladiador;
import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.batalha_gladiadorService;
import java.util.List;

import jakarta.servlet.http.HttpSession;


@Controller
public class batalha_gladiadorController {

    //Imagens de personagem disponíveis para escolher a aparência
    //O índice (1..11) é o valor gravado na coluna aparencia
    private static final String[] IMAGENS_PERSONAGENS = {
            "F1-arqueira.png", "F1-espada.png", "F1-lutadora.png", "F1-standart.png",
            "F2-arqueira.png", "F2-espada.png", "F2-lutadora.png", "F2-standart.png",
            "M1-lutador.png", "M2-arqueiro.png", "M3-espada.png"
    };

    private final batalha_gladiadorService service;

    public batalha_gladiadorController(batalha_gladiadorService service){
        this.service = service;
    }

    //Converte a coluna aparencia (1..11) no caminho da imagem servida em /img/personagens/
    public String imagemDe(int aparencia){
        int indice = Math.min(Math.max(aparencia, 1), IMAGENS_PERSONAGENS.length) - 1;
        return "/img/personagens/" + IMAGENS_PERSONAGENS[indice];
    }

    // ---------- Login / Cadastro ----------

    @GetMapping("/login")
    public String login(){
        return "log_in"; // templates/log_in.html
    }

    @PostMapping("/login")
    public String entrar(@RequestParam String email, @RequestParam String senha, HttpSession session, Model model){
        try {
            Usuario usuario = service.autenticar(email, senha);
            session.setAttribute("usuarioId", usuario.getId());
            return "redirect:/principal";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "log_in";
        }
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model){
        model.addAttribute("usuario", new Usuario());
        return "sign_in";
    }

    @PostMapping("/cadastro")
    public String salvarUsuario(@ModelAttribute Usuario usuario, HttpSession session, Model model){
        try {
            Usuario salvo = service.criarUsuario(usuario);
            session.setAttribute("usuarioId", salvo.getId());
            return "redirect:/principal";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "sign_in";
        }
    }

    @PostMapping("/logout")
    public String sair(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }

    // ---------- Arena (tela principal) ----------

    @GetMapping("/principal")
    public String principal(HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        Usuario usuario = service.buscarUsuario(idDaSessao(session));
        model.addAttribute("usuario", usuario);
        model.addAttribute("destaques", service.ranking().stream().limit(3).toList());
        return "tela_principal";
    }

    // ---------- Batalha ----------

    @GetMapping("/batalha")
    public String batalha(HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("gladiadoresVivos", service.listarVivos());
        return "batalha";
    }

    @PostMapping("/batalha")
    public String lutar(@RequestParam Long gladiadorA, @RequestParam Long gladiadorB, HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("gladiadoresVivos", service.listarVivos());
        try {
            model.addAttribute("vencedor", service.batalhar(gladiadorA, gladiadorB));
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
        }
        return "batalha";
    }

    // ---------- Ranking ----------

    @GetMapping("/ranking")
    public String ranking(HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("ranking", service.ranking());
        return "ranking";
    }

    // ---------- Usuário ----------

    @GetMapping("/usuario/{id}")
    public String resumoUsuario(@PathVariable Long id, HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("usuario", service.buscarUsuario(id));
        model.addAttribute("gladiadores", service.listarGladiadores(id));
        return "tela_usuario";
    }

    // ---------- Gladiador ----------

    @GetMapping("/usuario/{usuarioId}/gladiador/novo")
    public String novoGladiador(@PathVariable Long usuarioId, HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("gladiador", new Gladiador());
        model.addAttribute("usuarioId", usuarioId);
        model.addAttribute("tiers", Gladiador.Tier.values());
        List<String> imagens = new java.util.ArrayList<>();
        for (String img : IMAGENS_PERSONAGENS) imagens.add("/img/personagens/" + img);
        model.addAttribute("imagens", imagens);
        model.addAttribute("custo", batalha_gladiadorService.CUSTO_GLADIADOR);
        return "gladiador/novo";
    }

    @PostMapping("/usuario/{usuarioId}/gladiador/salvar")
    public String salvarGladiador(@PathVariable Long usuarioId, @ModelAttribute Gladiador gladiador, HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        try {
            service.criarGladiador(usuarioId, gladiador);
            return "redirect:/usuario/" + usuarioId;
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "gladiador/novo";
        }
    }

    @GetMapping("/gladiador/{id}")
    public String detalheGladiador(@PathVariable Long id, HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("gladiador", service.pesquisarGladiador(id));
        return "gladiador/detalhe";
    }

    @PostMapping("/gladiador/{id}/descricao")
    public String atualizarDescricao(@PathVariable Long id, @RequestParam Long usuarioId,
                                     @RequestParam String descricao, HttpSession session){
        if (!logado(session)) return "redirect:/login";
        service.atualizarDescricao(id, descricao);
        return "redirect:/usuario/" + usuarioId;
    }

    @PostMapping("/gladiador/{id}/deletar")
    public String deletarGladiador(@PathVariable Long id, @RequestParam Long usuarioId, HttpSession session){
        if (!logado(session)) return "redirect:/login";
        service.deletarGladiador(id);
        return "redirect:/usuario/" + usuarioId;
    }

    // ---------- Auxiliares de sessão ----------

    private boolean logado(HttpSession session){
        return session.getAttribute("usuarioId") != null;
    }

    private Long idDaSessao(HttpSession session){
        return (Long) session.getAttribute("usuarioId");
    }
}