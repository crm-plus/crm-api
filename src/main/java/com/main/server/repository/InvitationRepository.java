package com.main.server.repository;

import com.main.server.model.Invitation;
import com.main.server.model.InvitationState;
import com.main.server.model.organization.Organization;
import com.main.server.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends CrudRepository<Invitation, Long> {

    Optional<Invitation> findByIdAndRecipient(Long id, User user);

    List<Invitation> findAllByRecipientAndState(User user, InvitationState state, Pageable pageable);

    List<Invitation> findAllByOrganizationAndState(Organization organization, InvitationState state, Pageable pageable);
}
