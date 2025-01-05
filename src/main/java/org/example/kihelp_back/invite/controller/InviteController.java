package org.example.kihelp_back.invite.controller;

import org.example.kihelp_back.invite.dto.InviteCreateDto;
import org.example.kihelp_back.invite.usecase.InviteCreateUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/invite")
public class InviteController {
    private final InviteCreateUseCase inviteCreateUseCase;

    public InviteController(InviteCreateUseCase inviteCreateUseCase) {
        this.inviteCreateUseCase = inviteCreateUseCase;
    }

    @PostMapping("/create")
    public void createInvite(@RequestBody InviteCreateDto inviteCreateDto) {
        inviteCreateUseCase.createInvite(inviteCreateDto);
    }
}
