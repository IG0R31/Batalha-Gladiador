package controller;

import model.Gladiador;
import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.UsuarioService;
import service.GladiadorService;
import java.util.List;

import jakarta.servlet.http.HttpSession;



@Controller
public class batalha_gladiadorController {

    private static final String AVATAR_USUARIO = Gladiador.PASTA_IMAGENS + Gladiador.IMAGENS[3];

    private final UsuarioService usuarioservice;
    private final GladiadorService gladiadorservice;

    public batalha_gladiadorController(UsuarioService usuarioservice, GladiadorService gladiadorservice){
        this.usuarioservice = usuarioservice;
        this.gladiadorservice = gladiadorservice;
    }

    private List<String> imagensPersonagens(){
        List<String> imagens = new java.util.ArrayList<>();
        for (String img : Gladiador.IMAGENS) imagens.add(Gladiador.PASTA_IMAGENS + img);
        return imagens;
    }

    // ---------- Login / Cadastro ----------

    @GetMapping("/login")
    public String login(){
        return "log_in"; // templates/log_in.html
    }

    @PostMapping("/login")
    public String entrar(@RequestParam String email, @RequestParam String senha, HttpSession session, Model model){
        try {
            Usuario usuario = usuarioservice.autenticar(email, senha);
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
            Usuario salvo = usuarioservice.criarUsuario(usuario);
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
        Usuario usuario = usuarioservice.buscarUsuario(idDaSessao(session));
        model.addAttribute("usuario", usuario);
        model.addAttribute("destaques", gladiadorservice.ranking().stream().limit(3).toList());
        return "tela_principal";
    }

    // ---------- Batalha ----------

    @GetMapping("/batalha")
    public String batalha(HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("gladiadoresVivos", gladiadorservice.listarVivos());
        return "batalha";
    }

    @PostMapping("/batalha")
    public String lutar(@RequestParam Long gladiadorA, @RequestParam Long gladiadorB, HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("gladiadoresVivos", gladiadorservice.listarVivos());
        try {
            model.addAttribute("vencedor", gladiadorservice.batalhar(gladiadorA, gladiadorB));
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
        }
        return "batalha";
    }

    // ---------- Ranking ----------

    @GetMapping("/ranking")
    public String ranking(HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("ranking", gladiadorservice.ranking());
        return "ranking";
    }

    // ---------- Usuário ----------

    @GetMapping("/usuario/{id}")
    public String resumoUsuario(@PathVariable Long id, HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("usuario", usuarioservice.buscarUsuario(id));
        model.addAttribute("gladiadores", gladiadorservice.listarGladiadores(id));
        model.addAttribute("avatar", AVATAR_USUARIO);
        return "tela_usuario";
    }

    // ---------- Gladiador ----------

    @GetMapping("/usuario/{usuarioId}/gladiador/novo")
    public String novoGladiador(@PathVariable Long usuarioId, HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("gladiador", new Gladiador());
        model.addAttribute("usuario", usuarioservice.buscarUsuario(usuarioId));
        model.addAttribute("usuarioId", usuarioId);
        model.addAttribute("tiers", Gladiador.Tier.values());
        model.addAttribute("valores", gladiadorservice.valoresPorTier());
        model.addAttribute("imagens", imagensPersonagens());
        model.addAttribute("avatar", AVATAR_USUARIO);
        return "gladiador/novo";
    }

    @PostMapping("/usuario/{usuarioId}/gladiador/salvar")
    public String salvarGladiador(@PathVariable Long usuarioId, @ModelAttribute Gladiador gladiador, HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        try {
            gladiadorservice.criarGladiador(usuarioId, gladiador);
            return "redirect:/usuario/" + usuarioId;
        } catch (IllegalArgumentException e) {
            //reabre o formulário com os mesmos dados do GET + a mensagem de erro
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("gladiador", gladiador);
            model.addAttribute("usuario", usuarioservice.buscarUsuario(usuarioId));
            model.addAttribute("usuarioId", usuarioId);
            model.addAttribute("tiers", Gladiador.Tier.values());
            model.addAttribute("valores", gladiadorservice.valoresPorTier());
            model.addAttribute("imagens", imagensPersonagens());
            model.addAttribute("avatar", AVATAR_USUARIO);
            return "gladiador/novo";
        }
    }

    @GetMapping("/gladiador/{id}")
    public String detalheGladiador(@PathVariable Long id, HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("gladiador", gladiadorservice.pesquisarGladiador(id));
        return "gladiador/detalhe";
    }

    @PostMapping("/gladiador/{id}/descricao")
    public String atualizarDescricao(@PathVariable Long id, @RequestParam Long usuarioId,
                                     @RequestParam String descricao, HttpSession session){
        if (!logado(session)) return "redirect:/login";
        gladiadorservice.atualizarDescricao(id, descricao);
        return "redirect:/usuario/" + usuarioId;
    }

    @PostMapping("/gladiador/{id}/deletar")
    public String deletarGladiador(@PathVariable Long id, @RequestParam Long usuarioId, HttpSession session){
        if (!logado(session)) return "redirect:/login";
        gladiadorservice.deletarGladiador(id);
        return "redirect:/usuario/" + usuarioId;
    }

    // ---------- Pesquisa ----------

    //Rota chamada pelas barras de pesquisa: /pesquisar?q=termo
    @GetMapping("/pesquisar")
    public String pesquisar(@RequestParam String q, HttpSession session, Model model){
        if (!logado(session)) return "redirect:/login";
        model.addAttribute("termo", q);
        try {
            model.addAttribute("resultados", gladiadorservice.pesquisarPorNome(q));
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
        }
        return "pesquisa";
    }

    // ---------- Auxiliares de sessão ----------

    private boolean logado(HttpSession session){
        return session.getAttribute("usuarioId") != null;
    }

    private Long idDaSessao(HttpSession session){
        return (Long) session.getAttribute("usuarioId");
    }
}