package apartment.manager.Utilities.mappers;

import apartment.manager.entity.BaseEntity;
import apartment.manager.entity.Payment;
import apartment.manager.entity.User;
import apartment.manager.presentation.models.PaymentDto;
import apartment.manager.presentation.models.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public abstract class UserMapper {

    public  abstract UserDto userToUserDto(User user);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = BaseEntity.CREATED_BY_PROPERTY, ignore = true)
    public abstract User userDtoToUser(UserDto userDto);

    public List<UserDto> allUsersToUserDto(List<User> users) {
        return users.stream().map(this::userToUserDto).collect(Collectors.toList());
    }

    public List<User> allPaymentDtoToPayment(List<UserDto> usersDto) {
        return usersDto.stream().map(this::userDtoToUser).collect(Collectors.toList());
    }
}
