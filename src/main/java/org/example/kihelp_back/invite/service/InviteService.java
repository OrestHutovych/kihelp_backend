package org.example.kihelp_back.invite.service;

import org.example.kihelp_back.invite.model.Invite;
import org.example.kihelp_back.invite.repository.InviteRepository;
import org.springframework.stereotype.Service;

@Service
public class InviteService {
    private final InviteRepository inviteRepository;

    public InviteService(InviteRepository inviteRepository) {
        this.inviteRepository = inviteRepository;
    }

    public void create(Invite invite) {
        int sizeOfInviter = inviteRepository.findAllByInviterUserTelegramId(invite.getInviterUser().getTelegramId()).size();

        if(sizeOfInviter < 3){
            inviteRepository.save(invite);
        }
    }
}
