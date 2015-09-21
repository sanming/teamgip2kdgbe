package be.kdg.repaircafe.frontend.config.orika.custom.convertors;

import be.kdg.repaircafe.backend.dom.users.roles.Role;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleConvertor extends CustomConverter<List<Role>, List<Role.RoleType>>
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/presentation-layer/orika/

    @Override
    public List<Role.RoleType> convert(List<Role> roles, Type<? extends List<Role.RoleType>> type)
    {
        return roles.stream().map(role -> role.getRoleType()).collect(Collectors.toList());
    }
}
