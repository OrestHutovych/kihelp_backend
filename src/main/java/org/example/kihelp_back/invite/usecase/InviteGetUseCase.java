package org.example.kihelp_back.invite.usecase;

import org.example.kihelp_back.invite.dto.InviteDto;

import java.math.BigDecimal;
import java.util.List;

public interface InviteGetUseCase {
    List<InviteDto> getInvitesByInviter();
    BigDecimal getInvitesPrice();
}
