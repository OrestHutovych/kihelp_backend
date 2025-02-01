package org.example.kihelp_back.invite.controller;

import org.example.kihelp_back.invite.dto.InviteCreateDto;
import org.example.kihelp_back.invite.dto.InviteDto;
import org.example.kihelp_back.invite.usecase.InviteCreateUseCase;
import org.example.kihelp_back.invite.usecase.InviteGetUseCase;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/invites")
public class InviteController {
    private final InviteCreateUseCase inviteCreateUseCase;
    private final InviteGetUseCase inviteGetUseCase;

    public InviteController(InviteCreateUseCase inviteCreateUseCase,
                            InviteGetUseCase inviteGetUseCase) {
        this.inviteCreateUseCase = inviteCreateUseCase;
        this.inviteGetUseCase = inviteGetUseCase;
    }

    @PostMapping("/invite")
    public void createInvite(@RequestBody InviteCreateDto inviteCreateDto) {
        inviteCreateUseCase.createInvite(inviteCreateDto);
    }

    @GetMapping("/")
    public List<InviteDto> getInvitesByInviter() {
        return inviteGetUseCase.getInvitesByInviter();
    }

    @GetMapping("/invitee_amount")
    public BigDecimal getInvitePrice() {
        return inviteGetUseCase.getInvitesPrice();
    }
}
