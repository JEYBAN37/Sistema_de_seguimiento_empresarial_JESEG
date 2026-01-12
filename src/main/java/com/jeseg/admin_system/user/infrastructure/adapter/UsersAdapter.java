package com.jeseg.admin_system.user.infrastructure.adapter;

import com.jeseg.admin_system.company.infrastructure.repository.CompanyRepository;
import com.jeseg.admin_system.role.infrastructure.repository.RoleRepository;
import com.jeseg.admin_system.user.domain.dto.UserCreateRequest;
import com.jeseg.admin_system.user.domain.intreface.UserInterface;

import com.jeseg.admin_system.user.infrastructure.entity.UserJepegEntity;
import com.jeseg.admin_system.user.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UsersAdapter implements UserInterface {

    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void saveUsers(List<UserCreateRequest> users) {

        List<UserJepegEntity> entities = users.stream()
                .map(user -> UserJepegEntity.builder()
                        .cedula(user.getCedula())
                        .password(encodePassword(user.getPassword()))
                        .username(user.getUsername())
                        .nombreCompleto(user.getNombre())
                        .contrato(user.getContrato())
                        .telefono(user.getTelefono())
                        .role(roleRepository.getReferenceById(user.getRole()))
                        .company(companyRepository.getReferenceById(user.getCompany()))
                        .build())
                .toList();

        userRepository.saveAll(entities);
    }

    private String encodePassword(String password)
    {
        return password;
    }

}
