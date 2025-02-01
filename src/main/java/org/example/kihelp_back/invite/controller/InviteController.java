package org.example.kihelp_back.invite.controller;

import org.example.kihelp_back.invite.dto.InviteCreateDto;
import org.example.kihelp_back.invite.dto.InviteDto;
import org.example.kihelp_back.invite.usecase.InviteCreateUseCase;
import org.example.kihelp_back.invite.usecase.InviteGetUseCase;
import org.example.kihelp_back.invite.usecase.InviteUpdateUseCase;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/invites")
public class InviteController {
    private final InviteCreateUseCase inviteCreateUseCase;
    private final InviteGetUseCase inviteGetUseCase;
    private final InviteUpdateUseCase inviteUpdateUseCase;

    public InviteController(InviteCreateUseCase inviteCreateUseCase,
                            InviteGetUseCase inviteGetUseCase,
                            InviteUpdateUseCase inviteUpdateUseCase) {
        this.inviteCreateUseCase = inviteCreateUseCase;
        this.inviteGetUseCase = inviteGetUseCase;
        this.inviteUpdateUseCase = inviteUpdateUseCase;
    }

    @PostMapping("/invite")
    public void createInvite(@RequestBody InviteCreateDto inviteCreateDto) {
        inviteCreateUseCase.createInvite(inviteCreateDto);
    }

    @GetMapping("/")
    public List<InviteDto> getInvitesByInviter() {
        return inviteGetUseCase.getInvitesByInviter();
    }

    @GetMapping("/invitee-amount")
    public BigDecimal getInvitePrice() {
        return inviteGetUseCase.getInvitesPrice();
    }

    @PutMapping("/deposit-amount")
    public void depositInviteeAmount(){
        inviteUpdateUseCase.depositInviteAmount();
    }
}
