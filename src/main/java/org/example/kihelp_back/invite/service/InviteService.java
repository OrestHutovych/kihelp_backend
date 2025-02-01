package org.example.kihelp_back.invite.service;

import org.example.kihelp_back.invite.model.Invite;
import org.example.kihelp_back.invite.repository.InviteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InviteService {
    private final InviteRepository inviteRepository;

    public InviteService(InviteRepository inviteRepository) {
        this.inviteRepository = inviteRepository;
    }

    @Transactional
    public void create(Invite invite) {
        int sizeOfInviter = inviteRepository.findAllByInviterUserTelegramId(invite.getInviterUser().getTelegramId()).size();

        if(sizeOfInviter < 3){
            inviteRepository.save(invite);
        }
    }

    @Transactional
    public void depositToInviteeSpendBalance(Long userId, BigDecimal amount) {
        Invite invite = inviteRepository.findInviterByInviteeUserId(userId);

        if (invite != null) {
           invite.setInviteeAmountSpend(amount);
           inviteRepository.save(invite);
        }
    }

    @Transactional
    public boolean depositToWallet(String telegramId){
        List<Invite> invites = getInvitesByInviterUserId(telegramId);

        List<Invite> validInvites = invites.stream()
                .filter(i -> i.getInviteeAmountSpend() != null &&
                        i.getInviteeAmountSpend().compareTo(BigDecimal.valueOf(100.0)) >= 0)
                .toList();

        if (validInvites.size() >= 3) {
            boolean rewardedAlreadyGranted = validInvites.stream()
                    .anyMatch(Invite::getRewardGranted);

            if(!rewardedAlreadyGranted) {
                validInvites.forEach(i -> i.setRewardGranted(true));
                inviteRepository.saveAll(validInvites);

                return true;
            }
        }

        return false;
    }

    public List<Invite> getInvitesByInviterUserId(String telegramId) {
        return inviteRepository.findAllByInviterUserTelegramId(telegramId);
    }
}
