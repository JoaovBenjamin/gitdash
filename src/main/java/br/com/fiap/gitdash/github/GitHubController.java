package br.com.fiap.gitdash.github;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/")
    public String getUserInfo(Model model,
                               @RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient authorizedClient,
                               @AuthenticationPrincipal OAuth2User user) {
    
        String tokenValue = authorizedClient.getAccessToken().getTokenValue();
        List<RepositoryInfo> repos = gitHubService.getUserRepositories(tokenValue);
    
        model.addAttribute("repos", repos);
        model.addAttribute("userName", user.getAttribute("name") != null ? user.getAttribute("name") : user.getAttribute("login"));
        model.addAttribute("userAvatar", user.getAttribute("avatar_url"));
        model.addAttribute("userLogin", user.getAttribute("login"));
        model.addAttribute("userProfileUrl", user.getAttribute("html_url")); // Link para o perfil
    
        return "user"; 
    }

}
    
