package com.main.server.repository;

import com.main.server.model.InvitationState;
import com.main.server.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("FROM users")
    Set<User> getAll();

    Optional<User> getUserByCredentialEmail(String email);

    @Query(
            "from users as usrs join usrs.organizationRoles orgr join usrs.organizations org where org.id = :organizationId and orgr.organization.id = :organizationId")
    List<User> findAllMembersByOrganizationId(Long organizationId);

    @Query(
            "from users as usrs join usrs.invitations invitations where invitations.organization.id = :organizationId and  invitations.state = :state"
    )
    List<User> findAllPendingMembers(Long organizationId, InvitationState state);
}
