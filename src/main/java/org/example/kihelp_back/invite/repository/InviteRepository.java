package org.example.kihelp_back.invite.repository;

import org.example.kihelp_back.invite.model.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    List<Invite> findAllByInviterUserTelegramId(String telegramId);
}
