package org.example.kihelp_back.invite.usecase;

import org.example.kihelp_back.invite.dto.InviteDto;

import java.util.List;

public interface InviteGetUseCase {
    List<InviteDto> getInvitesByInviter();
}
