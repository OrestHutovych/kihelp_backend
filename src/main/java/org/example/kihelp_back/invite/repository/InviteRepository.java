package org.example.kihelp_back.invite.repository;

import org.example.kihelp_back.invite.model.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    @Transactional(readOnly = true)
    List<Invite> findAllByInviterUserTelegramId(String telegramId);

    @Transactional(readOnly = true)
    @Query("""
        SELECT i FROM Invite i
        WHERE i.inviteeUser = :inviterUser
    """)
    Invite findInviterByInviteeUserId(@Param("inviteeUserId") Long inviteeUserId);
}
